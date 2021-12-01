package com.example;

import java.util.*;

import javafx.scene.control.Label;

public class Checkerboard {
    // -1 as unused block, 0 as empty block, 1 as block with white pawn, 2 as block
    // with white pawn, 3 as block with
    int[][] board;

    // Mover refer to Black
    Boolean mover;
    Boolean capture = false;
    Vector<int[]> nextmove = new Vector<int[]>();
    Vector<moveAndScores> successorEvaluations;
    int countWhite;
    int countBlack;
    int countWhiteK;
    int countBlackK;
    int de, se, pr;

    public Checkerboard(Object input, Boolean move) {
        // System.out.println(input);
        this.mover = move;
        this.board = new int[8][8];
        // userinput = new Scanner(System.in);
        try {
            this.board = ((int[][]) input);
        } catch (Exception e) {
            init();
        }
        this.nextmove = check();
    }

    private void init() {
        Boolean payload = false;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (payload) {
                    board[x][y] = 1;
                } else {
                    board[x][y] = -1;
                }
                payload = !payload;
            }
            payload = !payload;
        }

        for (int y = 3; y < 5; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (payload) {
                    board[x][y] = 0;
                } else {
                    board[x][y] = -1;
                }
                payload = !payload;
            }
            payload = !payload;
        }

        for (int y = 5; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (payload) {
                    board[x][y] = 2;
                } else {
                    board[x][y] = -1;
                }
                payload = !payload;
            }
            payload = !payload;
        }
    }

    public Vector<int[]> check() {
        capture = false;
        countWhite = 0;
        countBlack = 0;
        countWhiteK = 0;
        countBlackK = 0;

        Vector<int[]> normalMoveWhite = new Vector<int[]>();
        Vector<int[]> captureMoveWhite = new Vector<int[]>();
        Vector<int[]> normalMoveBlack = new Vector<int[]>();
        Vector<int[]> captureMoveBlack = new Vector<int[]>();

        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                switch (board[x][y]) {
                    case 1:
                        countWhite++;
                        try {

                            if ((board[x - 1][y + 1] == 2 || board[x - 1][y + 1] == 4) && board[x - 2][y + 2] == 0
                                    && !mover) {
                                capture = true;

                                captureMoveWhite.add(new int[] { x, y, x - 2, y + 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {

                            if ((board[x + 1][y + 1] == 2 || board[x + 1][y + 1] == 4) && board[x + 2][y + 2] == 0
                                    && !mover) {
                                capture = true;

                                captureMoveWhite.add(new int[] { x, y, x + 2, y + 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x - 1][y + 1] == 0 && !mover) {

                                normalMoveWhite.add(new int[] { x, y, x - 1, y + 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x + 1][y + 1] == 0 && !mover) {

                                normalMoveWhite.add(new int[] { x, y, x + 1, y + 1 });
                            }
                        } catch (Exception e) {
                        }
                        break;

                    case 2:
                        countBlack++;
                        try {
                            if ((board[x - 1][y - 1] == 1 || board[x - 1][y - 1] == 3) && board[x - 2][y - 2] == 0
                                    && mover) {
                                capture = true;
                                captureMoveBlack.add(new int[] { x, y, x - 2, y - 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ((board[x + 1][y - 1] == 1 || (board[x + 1][y - 1] == 3)) && board[x + 2][y - 2] == 0
                                    && mover) {
                                capture = true;
                                captureMoveBlack.add(new int[] { x, y, x + 2, y - 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x - 1][y - 1] == 0 && mover) {
                                normalMoveBlack.add(new int[] { x, y, x - 1, y - 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x + 1][y - 1] == 0 && mover) {
                                normalMoveBlack.add(new int[] { x, y, x + 1, y - 1 });
                            }
                        } catch (Exception e) {
                        }
                        break;

                    case 3:
                        countWhiteK++;
                        try {
                            if ((board[x - 1][y - 1] == 2 || board[x - 1][y - 1] == 4) && board[x - 2][y - 2] == 0
                                    && !mover) {
                                capture = true;
                                captureMoveWhite.add(new int[] { x, y, x - 2, y - 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ((board[x + 1][y - 1] == 2 || board[x + 1][y - 1] == 4) && board[x + 2][y - 2] == 0
                                    && !mover) {
                                capture = true;
                                captureMoveWhite.add(new int[] { x, y, x + 2, y - 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ((board[x - 1][y + 1] == 2 || board[x - 1][y + 1] == 4) && board[x - 2][y + 2] == 0
                                    && !mover) {
                                capture = true;
                                captureMoveWhite.add(new int[] { x, y, x - 2, y + 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ((board[x + 1][y + 1] == 2 || board[x + 1][y + 1] == 4) && board[x + 2][y + 2] == 0
                                    && !mover) {
                                capture = true;
                                captureMoveWhite.add(new int[] { x, y, x + 2, y + 2 });
                            }
                        } catch (Exception e) {
                        }

                        try {
                            if (board[x - 1][y - 1] == 0 && !mover) {
                                normalMoveWhite.add(new int[] { x, y, x - 1, y - 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x + 1][y - 1] == 0 && !mover) {
                                normalMoveWhite.add(new int[] { x, y, x + 1, y - 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x - 1][y + 1] == 0 && !mover) {
                                normalMoveWhite.add(new int[] { x, y, x - 1, y + 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x + 1][y + 1] == 0 && !mover) {
                                normalMoveWhite.add(new int[] { x, y, x + 1, y + 1 });
                            }
                        } catch (Exception e) {
                        }
                        break;

                    case 4:
                        countBlackK++;
                        try {
                            if ((board[x - 1][y - 1] == 1 || board[x - 1][y - 1] == 3) && board[x - 2][y - 2] == 0
                                    && mover) {
                                capture = true;
                                captureMoveBlack.add(new int[] { x, y, x - 2, y - 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ((board[x + 1][y - 1] == 1 || board[x + 1][y - 1] == 3) && board[x + 2][y - 2] == 0
                                    && mover) {
                                capture = true;
                                captureMoveBlack.add(new int[] { x, y, x + 2, y - 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ((board[x - 1][y + 1] == 1 || board[x - 1][y + 1] == 3) && board[x - 2][y + 2] == 0
                                    && mover) {
                                capture = true;
                                captureMoveBlack.add(new int[] { x, y, x - 2, y + 2 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ((board[x + 1][y + 1] == 1 || board[x + 1][y + 1] == 3) && board[x + 2][y + 2] == 0
                                    && mover) {
                                capture = true;
                                captureMoveBlack.add(new int[] { x, y, x + 2, y + 2 });
                            }
                        } catch (Exception e) {
                        }

                        try {
                            if (board[x - 1][y - 1] == 0 && mover) {
                                normalMoveBlack.add(new int[] { x, y, x - 1, y - 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x + 1][y - 1] == 0 && mover) {
                                normalMoveBlack.add(new int[] { x, y, x + 1, y - 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x - 1][y + 1] == 0 && mover) {
                                normalMoveBlack.add(new int[] { x, y, x - 1, y + 1 });
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (board[x + 1][y + 1] == 0 && mover) {
                                normalMoveBlack.add(new int[] { x, y, x + 1, y + 1 });
                            }
                        } catch (Exception e) {
                        }
                        break;
                }
            }

        }

        Vector<int[]> returner = new Vector<int[]>();
        if (capture && mover) {
            returner = captureMoveBlack;
        } else if (mover) {
            returner = normalMoveBlack;
        } else if (capture && !mover) {
            returner = captureMoveWhite;
        } else if (!mover) {
            returner = normalMoveWhite;
        }
        return returner;

    }

    public void movement(int[] newmove, Boolean reverse) {
        int x_choosed;
        int y_choosed;
        int x_moveto;
        int y_moveto;
        if (!reverse) {
            x_choosed = newmove[0];
            y_choosed = newmove[1];
            x_moveto = newmove[2];
            y_moveto = newmove[3];
        } else {
            x_choosed = newmove[2];
            y_choosed = newmove[3];
            x_moveto = newmove[0];
            y_moveto = newmove[1];
        }

        board[x_moveto][y_moveto] = board[x_choosed][y_choosed];
        board[x_choosed][y_choosed] = 0;
        if (Math.abs(x_choosed - x_moveto) == 2 && Math.abs(y_choosed - y_moveto) == 2) {
            board[(x_choosed + x_moveto) / 2][(y_choosed + y_moveto) / 2] = 0;
        }
    }

    public String toString() {
        String output = "";
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                output += board[x][y] + " ";
            }
            output += "\n";
        }
        output += "Mover:" + mover + "\n";
        output += "capMode:" + capture + "\n";
        output += "countBlack:" + countBlack + "\n";
        output += "countWhite:" + countWhite + "\n";
        output += "countBlackK:" + countBlackK + "\n";
        output += "countWhiteK:" + countWhiteK + "\n";
        output += "Score:" + score() + "\n";
        return output;
    }

    public Boolean getMover() {
        return mover;
    }

    public Boolean captureMode() {
        return capture;
    }

    public Vector<int[]> next() {
        return nextmove;
    }

    public int[][] toList() {
        return board;
    }

    public int win() {
        int win = 0;
        if (nextmove.size() == 0 || countBlack + countBlackK == 0 || countWhite + countWhiteK == 0) {
            if ((countBlack + countBlackK) > (countWhite + countWhiteK)) {
                win = 1;
            } else if ((countBlack + countBlackK) < (countWhite + countWhiteK)) {
                win = -1;
            } else if ((countBlack + countBlackK) == (countWhite + countWhiteK)) {
                win = 2;
            }
        }
        return win;
    }

    public int score() {
        if (mover) {
            return 12 - (countWhite + countWhiteK);
        } else {
            return 12 - (countBlack + countBlackK);
        }
    }

    // The function focus on helping AI to get the best result
    public int[] MMABEval(int limit) {
        de = 0;
        se = 0;
        pr = 0;
        successorEvaluations = new Vector();
        MMAB(0, mover, Integer.MIN_VALUE, Integer.MAX_VALUE, limit);

        int max = -10000;
        int best = -1;
        for (int i = 0; i < successorEvaluations.size(); ++i) {
            if (max <= successorEvaluations.get(i).score) {
                max = successorEvaluations.get(i).score;
                best = i;
            }
        }
        HelloFx.dsp.getChildren().clear();
        HelloFx.dsp.getChildren().add(new Label("Se:" + se + "\n" + "De:" + de + "\n" + "Pr:" + pr + "\n"));
        System.out.println("Finished");
        return successorEvaluations.get(best).move;
    }

    public int MMAB(int depth, Boolean player, int alpha, int beta, int limit) {
        int bestScore;
        if (!mover) {
            bestScore = -1;
        } else {
            bestScore = 1;
        }

        if (se <= limit) {
            if (win() == -1) {
                se++;
                return score();
            }
            if (win() == 1) {
                se++;
                return score();
            }
            if (win() == 2) {
                se++;
                return score();
            }
            if (nextmove.isEmpty()) {
                se++;
                return score();
            }
            if (depth >= 1500) {
                se++;
                return score();
            }
        } else {
            return 0;
        }

        for (int i = 0; i < nextmove.size(); i++) {
            int[] move = nextmove.get(i);
            de++;
            Vector cache = new Vector<>();
            if (mover == false) {
                cache.add(board);
                cache.add(mover);
                cache.add(capture);

                int currentScore;
                if (capture) {
                    currentScore = MMAB(depth + 1, false, alpha, beta, limit);
                } else {
                    mover = true;
                    currentScore = MMAB(depth + 1, mover, alpha, beta, limit);
                }
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                }
                alpha = Math.max(currentScore, alpha);

                board = ((int[][]) cache.get(0));
                mover = ((Boolean) cache.get(1));
                capture = ((Boolean) cache.get(2));

                cache.clear();
                // Pruning
                if (alpha >= beta) {
                    pr++;
                    break;
                }
                if (depth == 0) {
                    successorEvaluations.add(new moveAndScores(currentScore, move));
                }
            } else {
                // System.out.println("Min");
                cache.add(board);
                cache.add(mover);
                cache.add(capture);

                // movement(move, false);
                // check();

                int currentScore;
                if (capture) {
                    currentScore = MMAB(depth + 1, true, alpha, beta, limit);
                } else {
                    mover = false;
                    currentScore = MMAB(depth + 1, mover, alpha, beta, limit);
                }
                if (currentScore < bestScore) {
                    bestScore = currentScore;
                }
                beta = Math.min(currentScore, beta);

                // movement(move, true);
                // check();
                // Reset
                board = ((int[][]) cache.get(0));
                mover = ((Boolean) cache.get(1));
                capture = ((Boolean) cache.get(2));

                // cache.clear();

                if (alpha >= beta) {
                    pr++;
                    break;
                }
            }

        }

        return bestScore;
    }
}
