package hr.fer.zemris.java.raytracer.model;

/**
 * Class that represents an intersection of ray and a sphere. It extends
 * RayIntersection.
 * 
 * @see hr.fer.zemris.java.raytracer.model.RayIntersection
 * 
 * @author staver
 *
 */
public class SphereIntersection extends RayIntersection {

	private Point3D center; // center point of the sphere
	private double kdr; // coefficient for diffuse component for red color
	private double kdg; // coefficient for diffuse component for green color
	private double kdb; // coefficient for diffuse component for blue color
	private double krr; // coefficient for reflective component for red color
	private double krg; // coefficient for reflective component for green color
	private double krb; // coefficient for reflective component for blue color
	private double krn; // coefficient n for reflective component - shininess factor

	/**
	 * SphereIntersection constructor.
	 * 
	 * @param point    point of intersection between ray and sphere
	 * @param distance distance between start of ray and intersection
	 * @param outer    specifies if intersection is outer
	 * @param center   center point of the sphere
	 * @param kdr      coefficient for diffuse component for red color
	 * @param kdg      coefficient for diffuse component for green color
	 * @param kdb      coefficient for diffuse component for blue color
	 * @param krr      coefficient for reflective component for red color
	 * @param krg      coefficient for reflective component for green color
	 * @param krb      coefficient for reflective component for blue color
	 * @param krn      coefficient n for reflective component - shininess factor
	 */
	public SphereIntersection(Point3D point, double distance, boolean outer, Point3D center, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		super(point, distance, outer);
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
		this.center = center;
	}

	@Override
	public double getKdb() {
		return kdb;
	}

	@Override
	public double getKdg() {
		return kdg;
	}

	@Override
	public double getKdr() {
		return kdr;
	}

	@Override
	public double getKrb() {
		return krb;
	}

	@Override
	public double getKrg() {
		return krg;
	}

	@Override
	public double getKrn() {
		return krn;
	}

	@Override
	public double getKrr() {
		return krr;
	}

	@Override
	public Point3D getNormal() {
		return this.getPoint().sub(center).normalize();
	}

}
