package io.github.xbeeant.mybatis.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobiao
 * @version 2020/10/5
 */
@Getter
@Setter
public class XstudioProperty implements Serializable {
    private static final long serialVersionUID = -6965607450900553487L;
    private String serviceRootInterface;

    private String serviceTargetPackage;

    private String serviceImplementRootInterface;

    private String serviceImplementTargetPackage;

    private String rootClient;

    private String idGenerator;

    private String responseObject;

    private String nonFuzzySearchColumn = "create_by, update_by";

    private String ignoreColumns = "";

    private String beginEnd = "";

    private String dateTime = "";

    private List<ColumnProperty> columns = new ArrayList<>();

    private String lombok = "";
}
