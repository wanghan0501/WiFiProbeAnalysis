package com.cs.scu.service;

/**
 * author: maicius
 * date: 2019/2/2
 * description:
 */

public interface HDFSService {
    public void write(String msg);
    public void InitHDFSWriter(String ip);
}
