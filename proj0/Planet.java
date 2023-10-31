public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double x = this.xxPos - p.xxPos;
        double y = this.yyPos - p.yyPos;
        return Math.sqrt(x * x + y * y);
    }

    public double calcForceExertedBy(Planet p) {
        double G = 6.67e-11;
        double distance = this.calcDistance(p);
        return G * this.mass * p.mass / distance / distance;
    }

    public double calcForceExertedByX(Planet p) {
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] p) {
        double sum = 0;
        for (int i = 0; i < p.length; i++) {
            if (this.equals(p[i]))
                continue;
            sum += this.calcForceExertedByX(p[i]);
        }
        return sum;
    }

    public double calcNetForceExertedByY(Planet[] p) {
        double sum = 0;
        for (int i = 0; i < p.length; i++) {
            if (this.equals(p[i]))
                continue;
            sum += this.calcForceExertedByY(p[i]);
        }
        return sum;
    }

    public void update(double dt, double fx, double fy) {
        double xa = fx / this.mass;
        double ya = fy / this.mass;
        this.xxVel += dt * xa;
        this.yyVel += dt * ya;
        this.yyPos += dt * this.yyVel;
        this.xxPos += dt * this.xxVel;
    }

    public void draw() {
        String img = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, img);
        return;
    }
}
