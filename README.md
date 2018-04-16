# Hotel Manager

## Contexte du projet

Projet de 2 étudiants réalisé dans le cadre du cours « Programmation objet avancée » - 8INF957 - UQAC Hiver 2018

Membres du groupe :

  * Loïc Bothorel : BOTL27029508
  * Rénald Morice : MORR14029503

## Qu'est-ce « HotelManager » ?

Hotel Manager est une application Java permettant une gestion centralisée de l'activité d'un hôtel.

## Installation

Il est préconisé de créer un projet depuis des sources existantes (lien git) avec un IDE car celui-ci va détecter tout seul les librairies nécessaires au fonctionnement de l'application du dossier **`lib/`**. 
Vérifier que ces librairies sont bien liées au projet dans les options du projet.

S'assurer que le serveur MySQL est bien lancé. Sinon le lancer.

Ouvrir le fichier **`src/hibernate.cfg.xml`** et y renseigner un utilisateur (qui aura les droits sur la base de données HotelManager) et son mot de passe.

```
<property name="hibernate.connection.username"><!-- user --></property>
<property name="hibernate.connection.password"><!-- your_password --></property>
```

Compiler puis exécuter.

