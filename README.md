# spring-boot-blog

MySQL命令
```
mysql -h 192.168.99.100 -uroot -p123456a
```

docker命令
```
docker --version
docker info
docker run hello-world
docker pull mysql
docker run --name blog-mysql -e MYSQL_ROOT_PASSWORD=123456a -e MYSQL_DATABASE=blog -p 3306:3306 -d mysql
docker rm -f ebdd5
docker image ls
docker ps
docker ps -a
docker start 220a0c // 启动停止的容器
```

前端代码
```
git clone https://github.com.cnpmjs.org/jirengu-inc/vue-blog-preview.git
```