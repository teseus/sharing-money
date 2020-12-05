package com.base.service;

import java.util.ArrayList;
import java.util.List;

public class SharingApplicationServiceHelper {
    public static List<Long> separateMoney(long totalMoney, long separateNumber) {
        long unit = totalMoney / separateNumber;
        List<Long> separatedValues = new ArrayList<>();
        long sum = 0;
        for (int i = 0; i < separateNumber-1; i++) {
            separatedValues.add(unit);
            sum += unit;
        }
        separatedValues.add(totalMoney - sum);
        return separatedValues;
    }
}
