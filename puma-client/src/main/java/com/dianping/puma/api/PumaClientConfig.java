package com.dianping.puma.api;

import com.dianping.puma.api.impl.*;
import com.dianping.puma.core.lock.DistributedLock;

import java.util.List;

public class PumaClientConfig {

	private String clientName;

	private String database;

	private List<String> tables;

	private boolean dml = true;

	private boolean ddl = false;

	private boolean transaction = false;

	private PumaServerRouter router;

	private String serverHost;

	private List<String> serverHosts;

	private DistributedLock lock;

	public PumaClientConfig setClientName(String clientName) {
		this.clientName = clientName;
		return this;
	}

	public PumaClientConfig setDatabase(String database) {
		this.database = database;
		return this;
	}

	public PumaClientConfig setTables(List<String> tables) {
		this.tables = tables;
		return this;
	}

	public PumaClientConfig setDml(boolean dml) {
		this.dml = dml;
		return this;
	}

	public PumaClientConfig setDdl(boolean ddl) {
		this.ddl = ddl;
		return this;
	}

	public PumaClientConfig setTransaction(boolean transaction) {
		this.transaction = transaction;
		return this;
	}

	public PumaClientConfig setRouter(PumaServerRouter router) {
		this.router = router;
		return this;
	}

	public PumaClientConfig setServerHost(String serverHost) {
		this.serverHost = serverHost;
		return this;
	}

	public PumaClientConfig setServerHosts(List<String> serverHosts) {
		this.serverHosts = serverHosts;
		return this;
	}

	public PumaClientConfig setLock(DistributedLock lock) {
		this.lock = lock;
		return this;
	}

	public String getClientName() {
		return clientName;
	}

	public String getDatabase() {
		return database;
	}

	public List<String> getTables() {
		return tables;
	}

	public boolean isDml() {
		return dml;
	}

	public boolean isDdl() {
		return ddl;
	}

	public boolean isTransaction() {
		return transaction;
	}

	public PumaServerRouter getRouter() {
		return router;
	}

	public String getServerHost() {
		return serverHost;
	}

	public List<String> getServerHosts() {
		return serverHosts;
	}

	public DistributedLock getLock() {
		return lock;
	}

	public SimplePumaClient buildSimplePumaClient() {
		return new SimplePumaClient(this);
	}

	public ClusterPumaClient buildClusterPumaClient() {
		return buildLionClusterPumaClient();
	}

	public ClusterPumaClient buildLionClusterPumaClient() {
		ConfigPumaServerMonitor monitor = new ConfigPumaServerMonitor(database, tables);
		router = new RoundRobinPumaServerRouter(monitor);
		return new ClusterPumaClient(this);
	}

	public ClusterPumaClient buildFixedClusterPumaClient() {
		FixedPumaServerMonitor monitor = new FixedPumaServerMonitor(serverHosts);
		router = new RoundRobinPumaServerRouter(monitor);
		return new ClusterPumaClient(this);
	}
}