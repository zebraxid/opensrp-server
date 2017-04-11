#!/usr/bin/env bash
set -exv
curl -L https://github.com/rnewson/couchdb-lucene/archive/v1.1.0.tar.gz | tar -xz
cd couchdb-lucene-1.1.0
mvn
cd target/
unzip couchdb-lucene-1.1.0-dist.zip
sed -e 's/^host=localhost$/host=0.0.0.0/' -i couchdb-lucene-1.1.0/conf/couchdb-lucene.ini
sed -e 's/localhost:5984/127.0.0.1:5984/' -i couchdb-lucene-1.1.0/conf/couchdb-lucene.ini

sudo updatedb

couchdb_local_ini_location="$(locate couchdb/local.ini)"
python_location="$(which python)"

sudo sed -i -e '$a [couchdb]' $couchdb_local_ini_location
sudo sed -i -e '$a os_process_timeout=60000 ; increase the timeout from 5 seconds. ' $couchdb_local_ini_location
sudo sed -i -e '$a [external]' $couchdb_local_ini_location
sudo sed -i -e '$a fti=$python_location /home/travis/build/OpenSRP/opensrp-server/couchdb-lucene-1.1.0/target/couchdb-lucene-1.1.0/tools/couchdb-external-hook.py' $couchdb_local_ini_location
sudo sed -i -e '$a [httpd_db_handlers]' $couchdb_local_ini_location
sudo sed -i -e '$a _fti = {couch_httpd_external, handle_external_req, <<"fti">>}' $couchdb_local_ini_location
sudo sed -i -e '$a [httpd_global_handlers]' $couchdb_local_ini_location
sudo sed -i -e '$a _fti = {couch_httpd_proxy, handle_proxy_req, <<"http:\/\/127.0.0.1:5985">>}' $couchdb_local_ini_location


