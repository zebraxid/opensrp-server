#!/usr/bin/env bash
set -ev
COUCHDB_LUCENE_VERSION=1.1.0
cd /usr/src \
curl -L https://github.com/rnewson/couchdb-lucene/archive/v$COUCHDB_LUCENE_VERSION.tar.gz | tar -xz
cd couchdb-lucene-$COUCHDB_LUCENE_VERSION
mvn
cd /usr/src/couchdb-lucene-$COUCHDB_LUCENE_VERSION/target
unzip couchdb-lucene-$COUCHDB_LUCENE_VERSION-dist.zip
mv couchdb-lucene-$COUCHDB_LUCENE_VERSION /opt/couchdb-lucene
rm -rf /usr/src/couchdb-lucene-*
apt-get remove --auto-remove -y maven
rm -rf /var/lib/apt/lists/*
sed -e 's/^host=localhost$/host=0.0.0.0/' -i /opt/couchdb-lucene/conf/couchdb-lucene.ini
sed -e 's/localhost:5984/127.0.0.1:5984/' -i /opt/couchdb-lucene/conf/couchdb-lucene.ini


# Link with lucene with couchdb
sed -i -e '$a [couchdb]' /usr/local/etc/couchdb/local.ini
sed -i -e '$a os_process_timeout=60000 ; increase the timeout from 5 seconds. ' /usr/local/etc/couchdb/local.ini
sed -i -e '$a [external]' /usr/local/etc/couchdb/local.ini
sed -i -e '$a fti=/usr/bin/python /opt/couchdb-lucene/tools/couchdb-external-hook.py' /usr/local/etc/couchdb/local.ini
sed -i -e '$a [httpd_db_handlers]' /usr/local/etc/couchdb/local.ini
sed -i -e '$a _fti = {couch_httpd_external, handle_external_req, <<"fti">>}' /usr/local/etc/couchdb/local.ini
sed -i -e '$a [httpd_global_handlers]' /usr/local/etc/couchdb/local.ini
sed -i -e '$a _fti = {couch_httpd_proxy, handle_proxy_req, <<"http:\/\/127.0.0.1:5985">>}' /usr/local/etc/couchdb/local.ini
