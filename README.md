CSP-Résolution Exacte
=====================

Lancement:
	java -jar main.jar

Arguments:
	-file [nom de fichier]	: nom du fichier de CSP à lire
			exemples: data/sample_csp.txt; data/big_csp.txt

	-ac1	: lance un algo d'arc consistence avant le backtracking
	-tg		: utilise un backtracking de type tester et générer
				(Note: par défaut, on lance générer et tester)
