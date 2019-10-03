package com.cs.scu.service;

import com.cs.scu.entity.DataGroup;


public interface DataUploadService {
    String PrintJson() throws Exception;
    void saveObject(DataGroup dataGroup) throws Exception;
}
