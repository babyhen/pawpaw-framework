package com.pawpaw.framework.core.common.domain;

import com.pawpaw.framework.core.common.util.AssertUtil;

/**
 * 分页请求
 *
 * @author liujixin
 */
public class PageRequest {
    protected int pageNo;
    protected int pageSize;

    public PageRequest(int pageNo, int pageSize) {
        AssertUtil.assertTrue(pageNo >= 1, "页码从1开始");
        AssertUtil.assertTrue(pageSize >= 1, "pageSize必须大于0");
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
