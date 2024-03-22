import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public final class TicTacToeGUI {
    public static void main(String[] args) {
        TicTacToeGUI t = new TicTacToeGUI();
    }




    private State state;
    private GUI gui;

    private void startNewGame() {
        if (gui != null) {
            gui.dispose();
        }
        state = new State();
        gui = new GUI();
    }

    public TicTacToeGUI() {//default constructor
        startNewGame();
    }


    private class State {
        private char XO = 'X';

        private void prepareForNextMove() {
            XO = (XO == 'X') ? 'O' : 'X';
        }


        private final char[][] board = new char[][] {{' ',' ',' '},
                                                     {' ',' ',' '},
                                                     {' ',' ',' '}};

        private void applyMove(int row, int col) {
            board[row][col] = XO;
        }


        private boolean someoneWon() {
            if (' ' != board[0][0] && board[0][0] == board[0][1] && board[0][1] == board[0][2]) { return true; }
            if (' ' != board[1][0] && board[1][0] == board[1][1] && board[1][1] == board[1][2]) { return true; }
            if (' ' != board[2][0] && board[2][0] == board[2][1] && board[2][1] == board[2][2]) { return true; }

            if (' ' != board[0][0] && board[0][0] == board[1][0] && board[1][0] == board[2][0]) { return true; }
            if (' ' != board[0][1] && board[0][1] == board[1][1] && board[1][1] == board[2][1]) { return true; }
            if (' ' != board[0][2] && board[0][2] == board[1][2] && board[1][2] == board[2][2]) { return true; }

            if (' ' != board[0][0] && board[0][0] == board[1][1] && board[1][1] == board[2][2]) { return true; }
            if (' ' != board[0][2] && board[0][2] == board[1][1] && board[1][1] == board[2][0]) { return true; }

            return false;
        }

        private boolean boardFull() {
            return board[0][0] != ' ' && board[0][1] != ' ' && board[0][2] != ' ' &&
                   board[1][0] != ' ' && board[1][1] != ' ' && board[1][2] != ' ' &&
                   board[2][0] != ' ' && board[2][1] != ' ' && board[2][2] != ' ';
        }
    }




    private class GUI extends JFrame {
        private Tile[][] tiles; //allows for tile to know row and col
        public class Tile extends JButton implements ActionListener{
            private final int row;
            private final int col;
            public Tile (int row, int col) { //default constructor
                //sets up the rows and columns and empty string spaces
                super(" ");
                this.row = row;
                this.col = col;
                addActionListener(this);
            }
            @Override
            //processes when buttons are clicked
            public void actionPerformed(ActionEvent e) {
                processValidMove(row, col);
            }

        }
        public GUI() { //default constructor
            super("TicTacToe");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(3, 3));
            setSize(600, 600);
            tiles = new Tile[3][3];
            //adds the tile buttons to create the 3 by 3 grid necessary
            for (int row = 0; row <3; row++) {
                for(int col = 0; col < 3; col++) {
                    tiles[row][col] = new Tile(row, col);
                    add(tiles[row][col]);

                }
            }
            setVisible(true);
        }
        //helper method used for displaying valid moves
        public Tile getTile(int row, int col) {
            return tiles[row][col];
        }

    }

    private void processValidMove(int row, int col) {
        //INVALID MOVE
        if (state.board[row][col] != ' ') {
            JOptionPane.showMessageDialog(gui, "Someone has already made a move in that position.", "INVALID MOVE" , JOptionPane.WARNING_MESSAGE);
            return;
        }
        //VALID MOVE
        state.applyMove(row, col);
        gui.getTile(row, col).setText(Character.toString(state.XO));
        //WINNING MOVE
        if (state.someoneWon()) {
            char winner = state.XO;
            state.prepareForNextMove();
            JOptionPane.showMessageDialog(gui, "That was a winning move! " + winner + " wins!","WINNING MOVE", JOptionPane.INFORMATION_MESSAGE);
            startNewGame();
        //DRAW GAME
        } else if (state.boardFull()) {
            JOptionPane.showMessageDialog(gui, "It's a draw.", "DRAW", JOptionPane.INFORMATION_MESSAGE);
            startNewGame();
        //NEXT MOVE
        } else {
            state.prepareForNextMove();
        }
    }
}





