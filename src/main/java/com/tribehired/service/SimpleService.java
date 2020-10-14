package com.tribehired.service;

import com.tribehired.model.vo.SimplePostVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleService {

    private void insertionSortDescending(SimplePostVO[] array){
        for (int j = 1; j < array.length; j++) {
            SimplePostVO current = array[j];
            int i = j-1;
            while ((i > -1) && (array[i].getTotalCount() < current.getTotalCount())) {
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = current;
        }
    }
}
