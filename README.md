# Hotel Manager

## Contexte du projet

Projet de 2 étudiants réalisé dans le cadre du cours « Programmation objet avancée » - 8INF957 - UQAC Hiver 2018

Membres du groupe :

  * Loïc Bothorel : BOTL27029508
  * Rénald Morice : MORR14029503
  
Le mini-srs est présent à la racine de ce projet.

## Qu'est-ce « HotelManager » ?

Hotel Manager est une application Java permettant la gestion des réservations d'un hôtel. Elle permet de gérer :

  * les chambres
  * les réservations
  * les clients
  * les employés
  * le compte de l'employé connecté
  
## Screenshot

Voici deux screenshots de l'application :

![Reservations](/img-readme/reservation.png)
![New reservation](/img-readme/newReservation.png)

## Installation

Il est préconisé de créer un projet depuis des sources existantes (directement avec le lien du repository GitHub ou avec le projet téléchargé en zip puis décompressé) avec un IDE car celui-ci va détecter tout seul les librairies nécessaires au fonctionnement de l'application du dossier **`lib/`**. 
Vérifier que ces librairies sont bien liées au projet dans les options du projet.

S'assurer que le serveur MySQL est bien lancé. Sinon le lancer.

Ouvrir le fichier **`src/hibernate.cfg.xml`** et y renseigner un utilisateur (qui aura les droits sur la base de données HotelManager) et son mot de passe.

```
<property name="hibernate.connection.username"><!-- user --></property>
<property name="hibernate.connection.password"><!-- your_password --></property>
```

Compiler puis exécuter.

