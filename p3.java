class P3 {

    public static void main(String[] args) {

        int n = 5, INF = 999;

        int[][] d = {
            {0, INF, 6, INF, INF},
            {3, 0, INF, INF, INF},
            {INF, INF, 0, 2, INF},
            {3, 1, -1, 0, INF},
            {INF, 4, INF, 2, 0}
        };

        // Distance Vector Updates
        for (int k = 0; k < n - 1; k++) {

            int[][] temp = new int[n][n];

            for (int i = 0; i < n; i++)
                temp[i] = d[i].clone();

            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    for (int v = 0; v < n; v++)
                        if (d[i][v] != INF && d[v][j] != INF)
                            temp[i][j] = Math.min(
                                temp[i][j],
                                d[i][v] + d[v][j]
                            );

            d = temp;
        }

        // Negative Cycle Detection
        boolean cycle = false;

        for (int i = 0; i < n; i++)
            if (d[i][i] < 0)
                cycle = true;

        if (cycle) {

            System.out.println("Negative cycle detected!");

        } else {

            System.out.println("Shortest Distance Table:");

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    System.out.print(d[i][j] + " ");

                System.out.println();
            }

            System.out.println("No negative cycle detected.");
        }
    }
}
