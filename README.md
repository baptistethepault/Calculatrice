# Calculatrice
Développement d'une calculatrice en Java

Pattern utilisé : Le modèle MVC avec un contrôleur qui pilote la vue et le modèle

Le contrôleur : 
CalculatriceController : contient le modèle et la vue. Détermine le comportement des deux boutons de la vue en y associant les fonctionnalités désirées côté vue et modèle. Appelle ces fonctions sur un AWTEventListener qui réagit à l’appui des touches « ENTER » et « SUPPR » n’importe où sur la GUI. L’input étant un JTextField, il aurait fallu lui ajouter un actionListener et on n’aurait pas pu choisir l’event associé à une touche du clavier

La vue : 
Un JTextField : l’input
Deux boutons : un pour reset l’affichage et un pour calculer et afficher le résultat 
Un JTextArea non modifiable : l’output

Le modèle : 
CommonFunctions : classe qui contient toutes les méthodes de calcul nécessaire au traitement de la chaine de caractères sans l’analyser 
CalculatriceModel : classe correspondant au modèle qui contient juste la fonction compute (appelée quand on clique sur « = » ou « Enter ») 
AnalysisFunctions: classe qui analyse la chaine de caractère numérique simplifiée pour réaliser le calcul et retourner la valeur de résultat sous la forme d’un double (Analyseur descendant récursif)

Implémentation d'une classe d'exception pour gérer les différentes erreurs possibles

Implémentation d'une classe contenant les tests unitaires à exécuter avec JUnit
