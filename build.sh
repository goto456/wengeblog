#########################################################################
# File Name: build.sh
# Author: Wang Biwen
# mail: wangbiwen88@126.com
# Created Time: 2017年10月11日 星期三 11时00分51秒
#########################################################################
#!/bin/bash

mvn -U clean package
cp target/*.war /usr/local/apache-tomcat-8.5.23/webapps
