package hr.fer.zemris.java.webserver.workers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that generates a.png file that displays a circle and sends it as an
 * http response.
 * 
 * @author staver
 *
 */
public class CircleWorker implements IWebWorker {

	/**
	 * Creates a png file and calls the <code>write()</code> method of the given
	 * context with the byte contents of that png file.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();

		g2d.fillOval(0, 0, bim.getWidth(), bim.getHeight());

		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ImageIO.write(bim, "png", bos);
		context.setMimeType("image/png");
		context.write(bos.toByteArray());

	}

}
