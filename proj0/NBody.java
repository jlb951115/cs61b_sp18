public class NBody {
    public static double readRadius(String file) {
        In in = new In(file);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String file) {
        In in = new In(file);
        int num = in.readInt();
        in.readDouble();
        Planet[] p = new Planet[num];
        for (int i = 0; i < num; i++) {
            double xp = in.readDouble();
            double yp = in.readDouble();
            double xv = in.readDouble();
            double yv = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            p[i] = new Planet(xp, yp, xv, yv, m, img);
        }
        return p;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        Planet[] p = readPlanets(args[2]);
        double r = readRadius(args[2]);
        String backgroud = "images/starfield.jpg";
        StdDraw.clear();
        StdDraw.setScale(-r, r);
        StdDraw.picture(0, 0, backgroud);
        for (int i = 0; i < p.length; i++)
            p[i].draw();
        StdDraw.enableDoubleBuffering();
        double t = 0;
        while (t < T)
        {
            double[] xforce = new double[p.length];
            double[] yforce = new double[p.length];
            for (int i = 0; i < p.length; i++) {
                xforce[i] = p[i].calcNetForceExertedByX(p);
                yforce[i] = p[i].calcNetForceExertedByY(p);
            }
            for (int i = 0; i < p.length; i++) {
                p[i].update(dt, xforce[i], yforce[i]);
            }
            StdDraw.picture(0, 0, backgroud);
            for (Planet P : p)
                P.draw();
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }
        StdOut.printf("%d\n", p.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < p.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    p[i].xxPos, p[i].yyPos, p[i].xxVel,
                    p[i].yyVel, p[i].mass, p[i].imgFileName);
        }
    }
}
