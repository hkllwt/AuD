public class SignalPlotter {
    public static final double FIRST_LIMIT = -10;
    public static final double SECOND_LIMIT = 10;
    public static final int NUMBER_OF_POINTS = 1000;
    public static final int SAMPLING_RATE = 250;

    public static void printArray(double[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length - 1; i++) {
            if (i == array.length - 2) {
                System.out.print(array[i]);
            } else {
                System.out.print(array[i] + ", ");
            }
        }
        System.out.println("]");
    }


    public static double[] createSamplingPoints(double firstLimit, double secondLimit, int numberOfPoints) {
        if (firstLimit == secondLimit) {
            numberOfPoints = 1;
        }

        double[] samplingPoints = new double[numberOfPoints];
        samplingPoints[numberOfPoints - 1] = secondLimit;

        if (numberOfPoints == 1) {
            return samplingPoints;
        }

        for (int i = 0; i < numberOfPoints - 1; i++) {
            samplingPoints[i] = firstLimit + i * (secondLimit - firstLimit) / (numberOfPoints - 1);
        }

        return samplingPoints;
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double[] applySigmoidToArray(double[] xs) {
        double[] ys = new double[xs.length];

        for (int i = 0; i < xs.length; i++) {
            ys[i] = sigmoid(xs[i]);
        }

        return ys;
    }

    public static void plotSigmoid() {
        double[] xs = createSamplingPoints(FIRST_LIMIT, SECOND_LIMIT, NUMBER_OF_POINTS);
        double[] ys = applySigmoidToArray(xs);

        PlotHelper.plot2D(xs, ys);
    }

    public static void computeHeartRate(double[] timeRPeaks) {
        System.out.println("Heart Rate:");
        double heartRate;

        for (int i = 1; i < timeRPeaks.length; i++) {
            heartRate = 1 / (timeRPeaks[i] - timeRPeaks[i - 1]) * 60;
            System.out.println(String.format("%.2f", heartRate) + " bpm");
        }
    }

    public static void main(String[] args) {
        // Task 3.2.4
        plotSigmoid();

        // Task 3.2.5
        double[] ecgSignal = PlotHelper.readEcg("ecg.txt");
        double[] ecgTime = createSamplingPoints(0, (double) ecgSignal.length / SAMPLING_RATE, ecgSignal.length);

        // PlotHelper.plotEcg(ecgTime, ecgSignal);

        int[] idxRPeaks = PlotHelper.readPeaks("rpeaks.txt");
        double[] rPeaks = new double[idxRPeaks.length];
        double[] timeRPeaks = new double[idxRPeaks.length];

        for (int i = 0; i < idxRPeaks.length; i++) {
            rPeaks[i] = ecgSignal[idxRPeaks[i]];
            timeRPeaks[i] = ecgTime[idxRPeaks[i]];
        }

        PlotHelper.plotEcg(ecgTime, ecgSignal, timeRPeaks, rPeaks);

        computeHeartRate(timeRPeaks);

    }
}