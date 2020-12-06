package com.base.service;

import java.util.ArrayList;
import java.util.List;

public class SharingApplicationServiceHelper {
    public static List<Long> separateMoney(long totalMoney, long separatedSize) {
        long unit = totalMoney / separatedSize;
        List<Long> separatedValues = new ArrayList<>();
        long sum = 0;
        for (int i = 0; i < separatedSize-1; i++) {
            separatedValues.add(unit);
            sum += unit;
        }
        separatedValues.add(totalMoney - sum);
        return separatedValues;
    }
}
