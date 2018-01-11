### gateway

![](https://img.shields.io/badge/language-java-orange.svg) ![](https://img.shields.io/badge/qq-923260818-brightgreen.svg) ![](https://img.shields.io/badge/build-%20passing-brightgreen.svg) ![](https://img.shields.io/badge/created-December-brightgreen.svg)

#### 概述

网关服务

对外接口设置及服务路由信息

对外接口种类:

1.直接放行

2.需要正确的身份认证信息才可访问

网关侧引入spring security resource server配置，除直接放行接口外，所有接口进行身份认证信息校验，通过校验的接口进行网关侧路由转发至后端服务

#### 备注

如果你有好的意见或建议，欢迎给我们提issue或pull request。