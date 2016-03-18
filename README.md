## 准备环境

初始化数据库

建库
    
    mysql -u root
    create database zoe;
    exit

建表

    mvn clean compile flyway:migrate

导入数据(在pom.xml所在文件夹执行下列命令)

    
    mysql -uroot -Dzoe < src/main/resources/db/seed/init-zoe.sql