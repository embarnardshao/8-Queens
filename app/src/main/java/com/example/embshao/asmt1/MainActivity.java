package com.example.embshao.asmt1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawGrid();
    }

    static ArrayList<Point> chessboard = new ArrayList<Point>();
    static ArrayList<Point> queensList = new ArrayList<Point>();

    public void drawGrid()
    {
        GridLayout layout = (GridLayout) findViewById(R.id.chessGrid);
        Context context = this;
        layout.setColumnCount(8);
        layout.setRowCount(8);

        chessboard = new ArrayList<Point>();

        for(int w = 1; w < 9; w++)
        {
            for(int j = 1; j < 9 ; j++)
            {
                chessboard.add(new Point(w, j));

                Button chessSquare= new Button(this);
                chessSquare.setText(""+w+""+j);
                chessSquare.setTextColor(Color.TRANSPARENT);
                //chessSquare.setTextColor(Color.BLACK);
                chessSquare.setLayoutParams(new LinearLayout.LayoutParams(90, 90));
                if( (w+j)%2 == 0 )
                {
                    chessSquare.setBackgroundResource(R.drawable.peach);
                    chessSquare.setOnClickListener(
                            new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v) {

                                    v.setSelected(!v.isSelected());
                                    Button button = (Button) v;
                                    Point queens = getPoint(button.getText());

                                    if( checkIfInChessBoard( button.getText() ) )
                                    {
                                        v.setBackgroundResource(R.drawable.peachqueen);
                                        chessboard = removeThreats(chessboard, queens);
                                        queensList.add(queens);

                                    }
                                    else if( checkIfInQueens( button.getText() ) )
                                    {
                                        v.setBackgroundResource(R.drawable.peach);
                                        chessboard = reverseRemoveThreats(chessboard, queens);
                                        queensList.remove(queens);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),
                                                "Wrong move", Toast.LENGTH_SHORT).show();
                                        Log.v("DEBUG_TAG", "Right Button toast shown!");
                                    }


                                }});
                }else{
                    chessSquare.setBackgroundResource(R.drawable.brown);
                    chessSquare.setOnClickListener(
                            new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v) {

                                    v.setSelected(!v.isSelected());
                                    Button button = (Button) v;
                                    Point queens = getPoint(button.getText());

                                    if( checkIfInChessBoard( button.getText() ) )
                                    {
                                        v.setBackgroundResource(R.drawable.brownqueen);
                                        chessboard = removeThreats(chessboard, queens);
                                        queensList.add(queens);

                                    }
                                    else if( checkIfInQueens( button.getText() ) )
                                    {
                                        v.setBackgroundResource(R.drawable.brown);
                                        chessboard = reverseRemoveThreats(chessboard, queens);
                                        queensList.remove(queens);

                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),
                                                "Wrong move", Toast.LENGTH_SHORT).show();
                                        Log.v("DEBUG_TAG", "Right Button toast shown!");
                                    }


                                }});
                }
                layout.addView(chessSquare);
            }
        }
    }

    public ArrayList<Point> getStandardBoard()
    {
        ArrayList<Point> standardBoard = new ArrayList<Point>();
        for (int w = 1; w < 9; w++) {
            for (int j = 1; j < 9; j++) {
                standardBoard.add(new Point(w, j));
            }
        }
        return standardBoard;
    }

    public boolean checkIfInChessBoard(CharSequence xy)
    {
        String cc = xy.toString();
        int x = Integer.parseInt(cc.substring(0,1));
        int y = Integer.parseInt(cc.substring(1,2));
        Point test = new Point(x, y);
        return chessboard.contains(test);
    }

    public boolean checkIfInQueens(CharSequence xy)
    {
        String cc = xy.toString();
        int x = Integer.parseInt(cc.substring(0,1));
        int y = Integer.parseInt(cc.substring(1,2));
        Point test = new Point(x, y);
        return queensList.contains(test);
    }

    public Point getPoint(CharSequence xy)
    {
        String cc = xy.toString();
        int x = Integer.parseInt(cc.substring(0,1));
        int y = Integer.parseInt(cc.substring(1,2));
        return new Point(x, y);
    }

    public void restart(View v)
    {
        finish();
        startActivity(getIntent());
    }


    public static ArrayList<Point> removeThreats(ArrayList<Point> chessboard, Point pInput)
    {
        ArrayList<Point> newBoard = new ArrayList<Point>();
        for (Point pTest: chessboard) {
            if (pTest.x != pInput.x //columns
                    && pTest.y != pInput.y // rows
                    && (pTest.x - pInput.y) != (pInput.x - pTest.y) // diagonals
                    && (pTest.x - pTest.y) != (pInput.x - pInput.y)) {
                newBoard.add(pTest);
            }
        }
        return newBoard;
    }

    public ArrayList<Point> reverseRemoveThreats(ArrayList<Point> chessboard, Point pInput)
    {
        ArrayList<Point> newBoard = getStandardBoard();
        for (Point pTest: newBoard) {
            if (pTest.x == pInput.x //rows
                    && pTest.y == pInput.y //columns
                    && (pTest.x - pInput.y) == (pInput.x - pTest.y) //diagonals
                    && (pTest.x - pTest.y) == (pInput.x - pInput.y)) {
                chessboard.add(pTest);
            }
        }
        return chessboard;
    }


}
