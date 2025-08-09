package org.ajonx.generation.kruskal;

public class DisjointSet {
	private final int[] parent;
	private final int[] rank;

		public DisjointSet(int cap) {
		this.parent = new int[cap];
		this.rank = new int[cap];
		for (int i = 0; i < cap; i++) {
			parent[i] = i;
		}
	}

	public int find(int n) {
		if (parent[n] != n) {
			parent[n] = find(parent[n]);
		}
		return parent[n];
	}

	public boolean union(int n1, int n2) {
		int rootN1 = find(n1);
		int rootN2 = find(n2);

		if (rootN1 == rootN2) return false;

		if (rank[rootN1] < rank[rootN2]) parent[rootN1] = rootN2;
		else if (rank[rootN2] < rank[rootN1]) parent[rootN2] = rootN1;
		else {
			parent[rootN2] = rootN1;
			rank[rootN1]++;
		}
		return true;
	}
}