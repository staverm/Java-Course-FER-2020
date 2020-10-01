package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Holds the static methods for calculating RGB values of a single pixel. The
 * calculations use ray tracing and Phong's coloring model.
 * 
 * @author staver
 *
 */
public class TracerUtil {

	/**
	 * Fills the given RGB array. Finds the closest intersection of the given ray to
	 * some object(in front of observer), then for each light source: checks if the
	 * light source can reach that intersection - if yes then adds the coloring
	 * contribution of that light source using Phong's model, else the pixel is
	 * colored black.
	 * 
	 * @param scene scene containing the objects and light sources
	 * @param ray   ray from eye-position to Point that represents some pixel
	 * @param rgb   RGB array to be filled
	 * @param eye   eye position point
	 */
	public static void tracer(Scene scene, Ray ray, short[] rgb, Point3D eye) {
		RayIntersection closestIntersection = null;
		double minDistance = Double.POSITIVE_INFINITY;
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection S = object.findClosestRayIntersection(ray);
			if (S != null && (S.getDistance() + 0.0001) < minDistance) {
				closestIntersection = S;
				minDistance = S.getDistance();
			}
		}

		if (closestIntersection == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			determineColorFor(closestIntersection, scene, rgb, eye);
		}
	}

	/**
	 * Checks for all light sources: if the light source can reach the given
	 * intersection(i.e. if intersection point is obscured by some other object)- if
	 * yes then adds the coloring contribution of that light source using Phong's
	 * model, else the pixel is colored black.
	 * 
	 * @param S     intersection: if it is reachable from some light source, then
	 *              the coloring contribution of that light source is added
	 * @param scene scene containing the objects and light sources
	 * @param rgb   RGB array to be filled
	 * @param eye   eye position point
	 */
	public static void determineColorFor(RayIntersection S, Scene scene, short[] rgb, Point3D eye) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		for (LightSource ls : scene.getLights()) {
			Ray rayFromLight = Ray.fromPoints(ls.getPoint(), S.getPoint());
			double distToLight = S.getPoint().sub(ls.getPoint()).norm();
			boolean obscured = false;

			for (GraphicalObject object : scene.getObjects()) {
				RayIntersection intersection = object.findClosestRayIntersection(rayFromLight);
				if (intersection != null && intersection.getDistance() + 0.0001 < distToLight) {
					obscured = true;
					break;
				}
			}

			if (!obscured) { // add contribution from this light source using Phong's model
				Point3D l = ls.getPoint().sub(S.getPoint()).normalize(); // from intersection to light source
				Point3D n = S.getNormal();
				Point3D v = eye.sub(S.getPoint()).normalize(); // from intersection to eye
				Point3D r = n.scalarMultiply(2 * l.scalarProduct(n)).sub(l).normalize(); // reflection vector

				rgb[0] += ls.getR() * S.getKdr() * Math.max(l.scalarProduct(n), 0);
				rgb[0] += ls.getR() * S.getKdr() * Math.pow(Math.max(v.scalarProduct(r), 0), S.getKrn());

				rgb[1] += ls.getG() * S.getKdg() * Math.max(l.scalarProduct(n), 0);
				rgb[1] += ls.getG() * S.getKdg() * Math.pow(Math.max(v.scalarProduct(r), 0), S.getKrn());

				rgb[2] += ls.getB() * S.getKdb() * Math.max(l.scalarProduct(n), 0);
				rgb[2] += ls.getB() * S.getKdb() * Math.pow(Math.max(v.scalarProduct(r), 0), S.getKrn());
			}

		}
	}
}
