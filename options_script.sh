#!/bin/bash

# 옵션 정의
OPTIONS=":h:p:P:v:?-:"
LONGOPTS="host:,port:,password:,version,help"

# 옵션 처리 함수
function handle_opts {
    case "$1" in
        h) HOST="$2";;
        p) PORT="$2";;
        P) PASSWORD="$2";;
        v) show_version;;
        ?) show_help;;
        -) case "$2" in
            host) HOST="$3" ; shift ;;
            port) PORT="$3" ; shift ;;
            password) PASSWORD="$3" ; shift ;;
            version) show_version;;
            help) show_help;;
            *) echo "알 수 없는 옵션: $2" ; exit 1 ;;
           esac;;
        *) echo "알 수 없는 옵션: $1" ; exit 1 ;;
    esac
}

# 옵션 파싱
PARSED=$(getopt --options=$OPTIONS --longoptions=$LONGOPTS --name "$0" -- "$@")
eval set -- "$PARSED"

# 옵션 처리
while true; do
    handle_opts "$1" "$2"
    shift
    [ -z "$1" ] && break
done

# Redis 연결
if [ -n "$HOST" ] && [ -n "$PORT" ]; then
    java -jar redis-statics-1.0-SNAPSHOT.jar -h "$HOST" -p "$PORT" ${PASSWORD:+-P "$PASSWORD"}
else
    echo "알 수 없는 명령어입니다. 사용법을 확인해주세요."
fi

# 버전 출력
function show_version {
    echo "RKS Version is 1.0.0"
    exit
}

# 도움말 출력
function show_help {
    echo "사용법: rks [옵션]"
    echo "옵션:"
    echo "  -h, --host <호스트>    Redis 서버 호스트"
    echo "  -p, --port <포트>    Redis 서버 포트"
    echo "  -P, --password <비밀번호>    Redis 서버 비밀번호"
    echo "  -v, --version    RKS 버전 확인"
    echo "  -?, --help    도움말 보기"
    exit
}
