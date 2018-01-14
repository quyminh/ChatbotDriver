
import java.util.*;
import java.io.*;

public class Chatbot {
	private static String filename = "./WARC201709_wid.txt";

	private static ArrayList<Integer> readCorpus() {
		ArrayList<Integer> corpus = new ArrayList<Integer>();
		try {
			File f = new File(filename);
			Scanner scn = new Scanner(f);
			while (scn.hasNext()) {
				if (scn.hasNextInt()) {
					int i = scn.nextInt();
					corpus.add(i);
				} else {
					scn.next();
				}
			}
			scn.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found.");
		}

		return corpus;
	}

	static public void main(String[] args) {
		ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
		int length = corpus.size();
		int range = -1;
		for (int i : corpus) {
			if (range < i)
				range = i;
		}
		range++;
		if (flag == 100) {
			int w = Integer.valueOf(args[1]);
			int count = 0;
			for (int i : corpus) {
				if (i == w)
					count++;
			}

			System.out.println(count);
			System.out.println(String.format("%.7f", count / (double) corpus.size()));
		} else if (flag == 200) {
			int n1 = Integer.valueOf(args[1]);
			int n2 = Integer.valueOf(args[2]);
			double random = (double) n1 / n2;
			double[] count = new double[range];
			double[] left = new double[range];
			double[] right = new double[range];
			left[0] = 0;
			for (int j : corpus) {
				count[j]++;
			}
			double pointer = 0;
			for (int i = 0; i < range; i++) {
				if (count[i] > 0) {
					left[i] = pointer;
					right[i] = left[i] + count[i] / length;
					pointer = right[i];
				}
			}

			for (int i = 0; i < range; i++) {
				if (random >= left[i] && random <= right[i]) {
					System.out.printf("%d\n", i);
					System.out.printf("%.7f\n", left[i]);
					System.out.printf("%.7f\n", right[i]);
					break;
				}
			}

		} else if (flag == 300) {
			int h = Integer.valueOf(args[1]);
			int w = Integer.valueOf(args[2]);
			int count = 0;
			int countAll = 0;

			for (int i = 0; i < length; i++) {
				if (corpus.get(i) == h) {
					if (corpus.get(i + 1) == w)
						count++;
					countAll++;
				}
			}
			System.out.println(count);
			System.out.println(countAll);
			System.out.println(String.format("%.7f", count / (double) countAll));
		} else if (flag == 400) {
			int n1 = Integer.valueOf(args[1]);
			int n2 = Integer.valueOf(args[2]);
			int h = Integer.valueOf(args[3]);
			double random = (double) n1 / n2;
			double[] left = new double[range];
			double[] right = new double[range];
			left[0] = 0;
			double pointer = 0;

			double[] count = new double[range];
			double countAll = 0;

			for (int i = 0; i < length - 1; i++) {
				if (corpus.get(i) == h) {
					count[corpus.get(i + 1)] += 1;

					countAll += 1;
				}
			}
			for (int i = 0; i < range; i++) {
				if (count[i] > 0) {
					left[i] = pointer;
					right[i] = left[i] + (count[i] / countAll);
					pointer = right[i];
					// System.out.println(right[i]);

					if (random >= left[i] && random <= right[i]) {
						System.out.println(i);
						System.out.printf("%.7f\n", left[i]);
						System.out.printf("%.7f\n", left[i] + (count[i] / (double) countAll));
						break;
					}
				}
			}

		} else if (flag == 500) {
			int h1 = Integer.valueOf(args[1]);
			int h2 = Integer.valueOf(args[2]);
			int w = Integer.valueOf(args[3]);
			int count = 0;
			int countAll = 0;
			for (int i = 0; i < length - 2; i++) {
				if (corpus.get(i + 1) == h2 && corpus.get(i) == h1) {
					if (corpus.get(i + 2) == w)
						count++;
					countAll++;
				}
			}
			// output
			System.out.println(count);
			System.out.println(countAll);
			if (countAll == 0)
				System.out.println("undefined");
			else
				System.out.println(String.format("%.7f", count / (double) countAll));
		} else if (flag == 600) {
			int n1 = Integer.valueOf(args[1]);
			int n2 = Integer.valueOf(args[2]);
			int h1 = Integer.valueOf(args[3]);
			int h2 = Integer.valueOf(args[4]);
			double random = (double) n1 / n2;
			double[] left = new double[range];
			double[] right = new double[range];
			left[0] = 0;
			int w = -1;
			double pointer = 0;

			double[] count = new double[range];
			double countAll = 0;

			for (int i = 0; i < length - 1; i++) {
				if (corpus.get(i + 1) == h2 && corpus.get(i) == h1) {
					count[corpus.get(i + 2)] += 1;

					countAll += 1;
				}
			}

			for (int i = 0; i < range; i++) {
				if (count[i] > 0) {
					left[i] = pointer;
					right[i] = left[i] + (count[i] / countAll);
					pointer = right[i];

					if (random >= left[i] && random <= right[i]) {
						w = i;
						break;
					}
				}
			}
			if (w != -1) {
				System.out.println(w);
				System.out.printf("%.7f\n", left[w]);
				System.out.printf("%.7f\n", left[w] + (count[w] / (double) countAll));
			} else
				System.out.println("undefined");

		} else if (flag == 700) {
			int seed = Integer.valueOf(args[1]);
			int t = Integer.valueOf(args[2]);
			int h1 = 0, h2 = 0;
			double[] count = new double[range];
			double countAll = 0;

			double[] left = new double[range];
			double[] right = new double[range];
			double pointer = 0;
			for (int j : corpus) {
				count[j]++;
			}

			Random rng = new Random();
			if (seed != -1)
				rng.setSeed(seed);

			if (t == 0) {
				// TODO Generate first word using r

				double[] count2 = new double[range];
				double r = rng.nextDouble();
				for (int i = 0; i < range; i++) {
					if (count[i] > 0) {
						left[i] = pointer;
						right[i] = left[i] + count[i] / length;
						pointer = right[i];
					}
				}

				for (int i = 0; i < range; i++) {
					if (r >= left[i] && r <= right[i]) {
						h1 = i;
						break;
					}
				}
				System.out.println(h1);
				if (h1 == 9 || h1 == 10 || h1 == 12) {
					return;
				}

				// Generate second word using r
				count2 = new double[range];
				left = new double[range];
				right = new double[range];
				pointer = 0;
				r = rng.nextDouble();
				countAll = 0;

				for (int i = 0; i < length - 1; i++) {
					if (corpus.get(i) == h1) {
						count2[corpus.get(i + 1)] += 1;

						countAll += 1;
					}
				}

				if (countAll > 0)
					for (int i = 0; i < range; i++) {
						if (count2[i] > 0) {
							left[i] = pointer;
							right[i] = left[i] + (count2[i] / countAll);
							pointer = right[i];
							if (r >= left[i] && r <= right[i]) {
								h2 = i;
								break;
							}
						}
					}
				else
					for (int i = 0; i < range; i++) {
						if (r >= left[i] && r <= right[i]) {
							h2 = i;
							break;
						}
					}
				System.out.println(h2);
			} else if (t == 1) {

				double[] count2 = new double[range];
				h1 = Integer.valueOf(args[3]);
				// Generate second word using r
				count2 = new double[range];
				left = new double[range];
				right = new double[range];
				pointer = 0;
				double r = rng.nextDouble();
				countAll = 0;

				for (int i = 0; i < length - 1; i++) {
					if (corpus.get(i) == h1) {
						count2[corpus.get(i + 1)] += 1;

						countAll += 1;
					}
				}
				if (countAll > 0)
					for (int i = 0; i < range; i++) {
						if (count2[i] > 0) {
							left[i] = pointer;
							right[i] = left[i] + (count2[i] / countAll);
							pointer = right[i];
							if (r >= left[i] && r <= right[i]) {
								h2 = i;
								break;
							}
						}
					}
				else {
					for (int i = 0; i < range; i++) {
						if (count[i] > 0) {
							left[i] = pointer;
							right[i] = left[i] + count[i] / length;
							pointer = right[i];
						}
					}
					for (int i = 0; i < range; i++) {
						if (r >= left[i] && r <= right[i]) {
							h2 = i;
							break;
						}
					}
				}
				System.out.println(h2);
			} else if (t == 2) {
				h1 = Integer.valueOf(args[3]);
				h2 = Integer.valueOf(args[4]);
			}

			while (h2 != 9 && h2 != 10 && h2 != 12) {
				double r = rng.nextDouble();
				int w = -1;
				// Generate new word using h1,h2

				left = new double[range];
				right = new double[range];
				left[0] = 0;
				pointer = 0;

				countAll = 0;
				double countAll2 = 0;
				double countAll3 = 0;
				double[] count2 = new double[range];
				double[] count3 = new double[range];

				for (int i = 0; i < length - 1; i++) {
					if (corpus.get(i + 1) == h2 && corpus.get(i) == h1 && i < length - 2) {
						count3[corpus.get(i + 2)] += 1;

						countAll3 += 1;
					}
					if (corpus.get(i) == h2) {
						count2[corpus.get(i + 1)] += 1;

						countAll2 += 1;
					}
				}

				if (countAll3 != 0) {
					for (int i = 0; i < range; i++) {
						if (count3[i] > 0) {
							left[i] = pointer;
							right[i] = left[i] + (count3[i] / countAll3);
							pointer = right[i];

							if (r >= left[i] && r <= right[i]) {
								w = i;
								break;
							}
						}
					}
				} else if (countAll2 != 0) {
					for (int i = 0; i < range; i++) {
						if (count2[i] > 0) {
							left[i] = pointer;
							right[i] = left[i] + (count2[i] / countAll2);
							pointer = right[i];

							if (r >= left[i] && r <= right[i]) {
								w = i;
								break;
							}
						}
					}
				} else {
					for (int i = 0; i < range; i++) {
						if (count[i] > 0) {
							left[i] = pointer;
							right[i] = left[i] + count[i] / length;
							pointer = right[i];
						}
					}
					for (int i = 0; i < range; i++) {
						if (r >= left[i] && r <= right[i]) {
							w = i;
							break;
						}
					}
				}

				System.out.println(w);
				h1 = h2;
				h2 = w;
			}
		}

		return;

	}
}