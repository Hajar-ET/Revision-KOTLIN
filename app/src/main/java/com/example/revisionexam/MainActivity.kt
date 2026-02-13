package com.example.revisionexam


enum class Niveau{ DEBUTANT, INTERMEDIAIRE, AVANCE }
data class Formation(val id : Int, var titre : String, var dureeHeures : Int,val niveau : Niveau )
abstract class Utilisateur(var nom: String, var email: String){
    abstract fun role(): String
    open fun afficherProfil(){}

}
class Etudiant (nom: String,email: String , val  niveauEtude : String):Utilisateur(nom, email ){
    override fun role(): String {
        return "Étudiant - Niveau : $niveauEtude"
    }
    override fun afficherProfil(){
        println("Nom : $nom")
        println("Email : $email")
        println(" specialite: $niveauEtude")

    }

}
class Formateur(nom: String,email: String ,val specialite : String):Utilisateur(nom, email ){
    override fun role(): String {
        return "Formateur - Spécialité : $specialite"
    }
    override fun afficherProfil(){
        println("Nom : $nom")
        println("Email : $email")
        println(" specialite: $specialite")

    }
}
class PaiementInvalideException(message: String): Exception(message)
sealed class Paiement(val montant : Double){
    init{
        if (montant<= 0){
            throw PaiementInvalideException("Montant du paiement invalide")
        }
    }

    class PaiementCarte(val numeroCarte : String , montant: Double): Paiement(montant )
    class PaiementEspece(montant : Double): Paiement(montant)
    class PaiementEnLigne(val emailCompte : String ,montant : Double ): Paiement(montant)


}
fun afficherTypePaiement(p: Paiement){
    when(p){
        is Paiement.PaiementCarte -> println("carte:${p.numeroCarte},${p.montant}")
        is Paiement.PaiementEspece -> println("especes: ${p.montant}")
        is Paiement.PaiementEnLigne -> println("En ligne: ${p.emailCompte}, ${p.montant}")
    }
}
class Plateforme(val nomPlateforme: String, val formations : ArrayList<Formation> ,val utilisateurs : ArrayList<Utilisateur> , val paiements : ArrayList<Paiement>  ){
    init{
        nombrePlateformes++
    }
    companion object {
        var nombrePlateformes:Int=0

        fun afficherNombrePlateformes(){
            println("Nombre de plateformes créées: $nombrePlateformes")
        }

    }
    constructor(nomPlateforme: String):this (nomPlateforme, ArrayList(), ArrayList(), ArrayList())

    fun ajouterFormation(f : Formation){
        if(!formations.contains(f)){
            formations.add(f)
        }
    }

    fun ajouterUtilisateur(u : Utilisateur){
        if(!utilisateurs.contains(u)){
            utilisateurs.add(u)
        }
    }

    fun enregistrerPaiement(p : Paiement){
        if(!paiements.contains(p)){
            paiements.add(p)
        }
    }

    fun nombreFormations(): Int{
        return formations.size
    }

    fun nombreUtilisateurs(): Int{
        return utilisateurs.size
    }

    class Adresse (val ville : String, val pays : String){
        fun afficherAdresse() {
            println("Adresse : $pays , $ville ")
        }
    }
    inner class Support(val nomSupport: String){

        fun afficherPlatforme(){
            println("le support $nomSupport appartient a la platforme $nomPlateforme")
        }}


    fun titresFormationsParNiveau(niveau: Niveau): List<String>{
        return formations.filter { it.niveau == niveau }.map { it.titre.uppercase() }.distinct()
    }
}
//fun String.estEmailValide(): Boolean{
//    return this.contains("@") && (this.substring(this.length-4).contains(".com") ||this.substring(this.length-3).contains(".fr"))
//}

fun String.estEmailValide(): Boolean{
    return this.contains("@") && (this.endsWith(".com") || this.endsWith(".fr"))
}

//fun String.estEmailValide(): Boolean{
//    val last4=this.takeLast(4)
//    val last3=this.takeLast(3)
//    return this.contains("@") && (last4.contains(".com") || last3.contains(".fr"))
//}

fun main() {



    val plateforme = Plateforme("OpenLearning")

    val f1 = Formation(1, "Kotlin Débutant", 20, Niveau.DEBUTANT)
    val f2 = Formation(2, "Kotlin Avancé", 30, Niveau.AVANCE)
    val f3 = Formation(3, "Java Intermediare", 25, Niveau.INTERMEDIAIRE)

    plateforme.ajouterFormation(f1)
    plateforme.ajouterFormation(f2)
    plateforme.ajouterFormation(f3)


    val e1 = Etudiant("Ali", "ali@gmail.com", "Licence")
    val e2 = Etudiant("Sara", "sara@yahoo.fr", "Master")
    val formateur1 = Formateur("Youssef", "youssef@gmail.com", "Mobile")
    val formateur2 = Formateur("yassin", "yassinf@gmail.com", "full stack")

    plateforme.ajouterUtilisateur(e1)
    plateforme.ajouterUtilisateur(e2)
    plateforme.ajouterUtilisateur(formateur1)
    plateforme.ajouterUtilisateur(formateur2)

    println("Email Ali valide ? ${e1.email.estEmailValide()}")
    println("Email Sara valide ? ${e2.email.estEmailValide()}")

    try{
        val p1 = Paiement.PaiementCarte("1234-5678", 500.0)
        val p2 = Paiement.PaiementEspece(300.0)
        val p3 = Paiement.PaiementEnLigne("pay@gmail.com", 400.0)

        plateforme.enregistrerPaiement(p1)
        plateforme.enregistrerPaiement(p2)
        plateforme.enregistrerPaiement(p3)

        afficherTypePaiement(p1)
        afficherTypePaiement(p2)
        afficherTypePaiement(p3)
    }catch(e: PaiementInvalideException){
        println(e.message)
    }

    println("Nombre de formations : ${plateforme.nombreFormations()}")
    println("Nombre d'utilisateurs : ${plateforme.nombreUtilisateurs()}")

    val titresDebutant = plateforme.titresFormationsParNiveau(Niveau.DEBUTANT)
    println("Formations DEBUTANT : $titresDebutant")

    val support = plateforme.Support("Support Technique")
    support.afficherPlatforme()

    Plateforme.afficherNombrePlateformes()



    val adresse = Plateforme.Adresse(ville = "Safi", pays = "Maroc")
    adresse.afficherAdresse()
    e1.afficherProfil()
    formateur1.afficherProfil()




}