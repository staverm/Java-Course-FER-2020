package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * An implementation of IFractalProducer. The <code>produce()</code> method
 * creates daemonic threads and assigns work to them. It waits for them to fill
 * up an array of data which it then used to draw fractals on the GUI.
 * 
 * @author staver
 *
 */
public class FractalProducer implements IFractalProducer {

	/**
	 * Thread factory that generates Daemonic threads.
	 * 
	 * @author staver
	 *
	 */
	private class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}

	}

	private ExecutorService pool;
	private ComplexRootedPolynomial polynomial; // polynomial used for Newton-Raphson iteration

	/**
	 * Constructs and initializes this FractalProducer to the specified polynomial.
	 * 
	 * @param polynomial Polynomial used for Newton-Raphson iteration
	 */
	public FractalProducer(ComplexRootedPolynomial polynomial) {
		pool = Executors.newFixedThreadPool(8 * Runtime.getRuntime().availableProcessors(),
				new DaemonicThreadFactory());
		this.polynomial = polynomial;
	}

	
	/**
	 * Calculates indexes of colors for each pixel, then the
	 * GUI(IFractalResultObserver) is called with the calculated values.
	 */
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
			IFractalResultObserver observer, AtomicBoolean cancel) {

		short[] data = new short[width * height];
		final int numberOfTracks = 8 * Runtime.getRuntime().availableProcessors();
		int yCountPerTrack = height / numberOfTracks;

		List<Future<?>> results = new ArrayList<>();

		for (int i = 0; i < numberOfTracks; i++) {
			int yMin = i * yCountPerTrack;
			int yMax = (i + 1) * yCountPerTrack - 1;
			if (i == numberOfTracks - 1) {
				yMax = height - 1;
			}

			CalculationWork work = new CalculationWork(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data,
					polynomial);
			results.add(pool.submit(work));
		}

		for (Future<?> work : results) {
			try {
				work.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}

		pool.shutdown();

		// GUI call
		observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
	}

}
