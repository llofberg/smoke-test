FROM openjdk:8

LABEL maintainer "vowpalwabbit@lofberg.com"

RUN apt-get update
RUN apt-get install -y gcc g++ git emacs-nox maven libboost-all-dev lsb-release autoconf automake libtool make libz-dev clang cmake libboost-program-options-dev zlib1g-dev

WORKDIR /tmp
RUN git clone https://github.com/JohnLangford/vowpal_wabbit.git

WORKDIR /tmp/vowpal_wabbit
# RUN git reset --hard 6777a307d1285f9c62eeab8f660d3ccec5a2eaed
RUN git checkout tags/8.5.0
RUN ./autogen.sh --with-boost-libdir=/usr/lib/x86_64-linux-gnu
RUN make CXX='clang++ -fPIC -std=gnu++11'
# RUN make test
RUN make install

# WORKDIR /tmp/vowpal_wabbit/rapidjson
# RUN cmake .
# RUN make install

WORKDIR /tmp/vowpal_wabbit/java
ENV UNAME=Linux
RUN ln -s ../vowpalwabbit/.libs/libvw.a ../vowpalwabbit/libvw.a
RUN ln -s ../vowpalwabbit/.libs/liballreduce.a ../vowpalwabbit/liballreduce.a
ENV BOOST_LIBRARY=/usr/lib/x86_64-linux-gnu/libboost_program_options.so
RUN make CXX='g++ -std=c++11 -fPIC' things
RUN mvn dependency:resolve -Dsilent=true

RUN mvn clean install
ENV LD_LIBRARY_PATH=/usr/local/lib

RUN mkdir -p /usr/lib/jni
RUN cp /tmp/vowpal_wabbit/java/target/libvw_jni.so /usr/lib/jni/

RUN /usr/local/bin/vw --help

RUN mkdir /tmp/work
WORKDIR /tmp/work
RUN git clone https://github.com/llofberg/smoke-test.git
WORKDIR /tmp/work/smoke-test
# RUN mvn clean verify exec:java
