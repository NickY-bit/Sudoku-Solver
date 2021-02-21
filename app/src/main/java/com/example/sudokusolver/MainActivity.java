package com.example.sudokusolver;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    int[][][] pos = new int[3][3][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button butSolve = findViewById(R.id.buttonSolve);
        butSolve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //x = row, y = column, z = square
                //[0][0][0] is the top left most number
                int x = 0;
                int y = 0;
                int z = 0;
                solvePuzzle();
            }
        });

        }

        public void solvePuzzle() {
            ArrayList<Integer>[][][] ans = new ArrayList[3][3][9];
            int x = 0;
            int y = 0;
            int z = 0;

            while (z < 9) {
                String textObjId =  "editTextNumber" + x + y + z;
                EditText textObj = findViewById(getResources().getIdentifier(textObjId, "id", getPackageName()));
                String num = String.valueOf(textObj.getText());
                if (num.isEmpty()) {
                    pos[x][y][z] = 0;
                } else {
                    pos[x][y][z] = Integer.parseInt(num);
                }
                //System.out.println("x: " + x + ", y: " + y + ", z: " + z + ", val: " + pos[x][y][z]);
                x++;
                if (x > 2) {
                    x = 0;
                    y++;
                    if (y > 2) {
                        y = 0;
                        z++;
                    }
                }
            }

            z = 0;

            //initialize the array of arraylists
            while (z < 9) {
                ans[x][y][z] = new ArrayList<>();
                x++;
                if (x > 2) {
                    x = 0;
                    y++;
                    if (y > 2) {
                        y = 0;
                        z++;
                    }
                }
            }

            z = 0;

            //triple nested loop that probably won't set your phone on fire
            //notes possible numbers in each cell
            while (z < 9) {
                System.out.println("Noting x: " + x + ", y: " + y + ", z: " + z);
                if (pos[x][y][z] != 0) {
                    ans[x][y][z].add(pos[x][y][z]);
                } else {
                    for (int i = 1; i <= 9; i++) {
                        ans[x][y][z].add(i);
                    }
                        int a = 0;
                        int b = 0;
                        int c = 0;
                        int offset;
                        //1st loop check row, 2nd loop check column, 3rd loop check square
                        //makes offset start in the left most column
                        offset = z - (z % 3);
                        while (c < 3) {
                            int num = pos[x][b][offset + c];
                            if (num != 0) {
                                ans[x][y][z].remove((Integer)num);
                            }
                            b++;
                            if (b > 2) {
                                b = 0;
                            c++;
                            }
                        }

                        c = 0;
                        //sets offset to the top most row
                        offset = z % 3;
                        while (c < 7) {
                            int num = pos[a][y][offset + c];
                            if (num != 0) {
                                ans[x][y][z].remove((Integer)num);
                            }
                            a++;
                            if (a > 2) {
                               a = 0;
                                c += 3;
                            }
                        }

                        while (b < 3) {
                            int num = pos[a][b][z];
                            if (num != 0) {
                            ans[x][y][z].remove((Integer)num);
                            }
                            a++;
                            if (a > 2) {
                                a = 0;
                                b++;
                            }
                        }

                        for (int num : ans[x][y][z]) {
                            System.out.println(num);
                        }

                        if (ans[x][y][z].size() == 1) {
                            pos[x][y][z] = ans[x][y][z].get(0);
                        }

                }
                x++;
                if (x > 2) {
                    x = 0;
                    y++;
                    if (y > 2) {
                        y = 0;
                        z++;
                    }
                }
            }
            // x & y should already be 0
            z = 0;
            //loop through ans and find numbers that only have one possible position in each square
            /*while (z < 9) {



                x++;
                if (x > 2) {
                    x = 0;
                    y++;
                    if (y > 2) {
                        y = 0;
                        z++;
                    }
                }
            }

            z = 0;
            */

            //Display answers
            while (z < 9) {

                if (pos[x][y][z] == 0) {
                    x++;
                    if (x > 2) {
                        x = 0;
                        y++;
                        if (y > 2) {
                            y = 0;
                            z++;
                        }
                    }
                    continue;
                }

                String textObjId =  "editTextNumber" + x + y + z;
                EditText textObj = findViewById(getResources().getIdentifier(textObjId, "id", getPackageName()));

                textObj.setText(String.valueOf(pos[x][y][z]));
                x++;
                if (x > 2) {
                    x = 0;
                    y++;
                    if (y > 2) {
                        y = 0;
                        z++;
                    }
                }
            }
    }
}
