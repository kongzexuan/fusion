package com.qn.auto.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TerminalType {
    //硬终端
    HDC("硬终端","HDC"),
    //WIN
    W("windows客户端", "W"),
    A("android客户端", "A"),
    M("mac客户端","M"),
    I("ios客户端","I"),
    EGR("会议大屏","EGR"),
    WEB("web客户端","WEB"),
    WEBC("web会控","WEB"),
    ECM("mac智能硬件","ECM"),
    ECH("硬终端智能硬件","ECH"),
    ECW("windows智能硬件","ECW"),
    EHCS("EHCS固件","EHCS"),
    EHTP("EHTP固件","EHTP"),
    EHOM("EHOM固件","EHOM"),
    EHA802("EHA802固件","EHA802"),
    EHT202("EHT202固件","EHT202"),
    EHC802("EHC802固件","EHC802")
    ;

    private final String name;
    private final String terminal;
}
