package com.whatyplugin.imooc.logic.service_;


import java.util.List;

import com.whatyplugin.imooc.logic.model.MCServiceResult;

public interface MCAnalyzeBackBlock<T> {
    void OnAnalyzeBackBlock(MCServiceResult result, List<T> resultList);
}

