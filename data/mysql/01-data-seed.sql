-- WARNING: PASSWORD HASH FOR DEFAULT DEV ACCOUNTS ARE PUBLICLY AVAILABLE ON THE PROJECT'S SOURCE CODE, INITIAL PASSWORDS MUST BE CHANGED FOR PRODUCTION
-- ALL THREE OF THE INITIAL USERS' PASSWORDS ARE 'Klmnop123@'

INSERT INTO users (id,access_privileges,code,created_at,email,name,password,updated_at) VALUES
	 (1,NULL,'admin',NULL,'admin@gmail.com','Admin','$2a$10$LkES6mfYTDJiDHEs5.m50ugsa.PGIQfvsj0PI5FqIOLtSlxHaDDIS',NULL),
	 (2,NULL,'new.user',NULL,'uuu1@gmail.com','Normal User','$2a$10$synVFfuBnpuYngKy.B3ck.WtZ2NGSHY2pE3.HNHdWji6QsXeE9cxm',NULL),
	 (3,NULL,'test',NULL,'a@gmail.com','Tester','$2a$10$B.E0e5SzzGpsur7ZuMEdce22emeBAk9h.y8E7V6D6uS/tstWPdxva',NULL);
