1、修改数据库配置
数据库文件为sqllite》goview.db


application-dev.yml
修改为自己对于的存放地址
url: jdbc:sqlite:D:\\eclipse-workspace\v2-goview-bate\sqllite\goview.db




2、修改配置文件图片存放地址（3个值必须对，oss与fileurl 路径一样写法不一样别搞错了 有file:）

v2>oss值      
v2>fileurl值
v2>httpurl值


httpurl为图片上传的访问地址