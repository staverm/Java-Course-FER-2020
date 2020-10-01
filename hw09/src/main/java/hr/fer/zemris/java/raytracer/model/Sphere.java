package hr.fer.zemris.java.raytracer.model;

/**
 * Class that models a sphere in 3D. It is described with center point, radius,
 * and a list of coefficients that describe the sphere surface properties.
 * 
 * @author staver
 *
 */
public class Sphere extends GraphicalObject {

	private Point3D center; // center point of the sphere
	private double radius; // radius of the sphere
	private double kdr; // coefficient for diffuse component for red color
	private double kdg; // coefficient for diffuse component for green color
	private double kdb; // coefficient for diffuse component for blue color
	private double krr; // coefficient for reflective component for red color
	private double krg; // coefficient for reflective component for green color
	private double krb; // coefficient for reflective component for blue color
	private double krn; // coefficient n for reflective component - shininess factor

	/**
	 * Sphere constructor.
	 * 
	 * @param center center point of the sphere
	 * @param radius radius of the sphere
	 * @param kdr    coefficient for diffuse component for red color
	 * @param kdg    coefficient for diffuse component for green color
	 * @param kdb    coefficient for diffuse component for blue color
	 * @param krr    coefficient for reflective component for red color
	 * @param krg    coefficient for reflective component for green color
	 * @param krb    coefficient for reflective component for blue color
	 * @param krn    coefficient n for reflective component - shininess factor
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Returns the closest intersection of the given ray and this sphere or null if
	 * there are no intersections.
	 * 
	 * @param ray ray whose closest intersection with sphere is to be found
	 * @return the closest intersection of the given ray and this sphere or null if
	 *         there are no intersections.
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D direction = ray.direction;
		Point3D centerToOrigin = ray.start.sub(center);

		double a = direction.scalarProduct(direction);
		double b = (double) 2 * direction.scalarProduct(centerToOrigin);
		double c = centerToOrigin.scalarProduct(centerToOrigin) - radius * radius;

		double t1, t2; // solutions of complex equation

		double discriminant = b * b - 4 * a * c;

		if (discriminant < 0) { // no intersect
			return null;
		}

		if (discriminant == 0) {
			t1 = t2 = -b / 2 * a;
		} else {
			double q = -0.5 * (b + Math.sqrt(discriminant));
			if (b <= 0) {
				q = -0.5 * (b - Math.sqrt(discriminant));
			}

			t1 = q / a;
			t2 = c / q;
		}

		if (t1 < 0 && t2 < 0) { // no positive solutions
			return null;
		}

		// find smallest positive t

		if (t1 > t2) {
			t1 = t2;
		}
		if (t1 < 0) {
			t1 = t2;
		}

		// t1 is the smallest positive solution

		Point3D intersectPoint = ray.start.add(direction.scalarMultiply(t1));

		return new SphereIntersection(intersectPoint, intersectPoint.sub(ray.start).norm(), true, center, kdr, kdg, kdb,
				krr, krg, krb, krn);
	}

}
