package service;

import models.Golfer;

import java.util.List;

public class ToString {
    public static void toString(List<Golfer> golfers) {
        for (Golfer golfer : golfers) {
            System.out.println(golfer.toString());
        }
    }
}


