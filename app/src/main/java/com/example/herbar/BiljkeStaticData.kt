package com.example.herbar

fun getBiljke(): List<Biljka>{
    return listOf(
        Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Lamiaceae (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA),
            slika="bosiljak"
        ),
        Biljka(
            naziv = "Nana (Mentha spicata)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,
                MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.MENTA,
            jela = listOf("Jogurt sa voćem", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA),
            slika = "nana"
        ),
        Biljka(
            naziv = "Kamilica (Matricaria chamomilla)",
            porodica = "Asteraceae (glavočike)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Čaj od kamilice"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO),
            slika="kamilica"
        ),
        Biljka(
            naziv = "Ružmarin (Rosmarinus officinalis)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,
                MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Pečeno pile", "Grah","Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.SLJUNOVITO, Zemljiste.KRECNJACKO),
            slika="ruzmarin"
        ),
        Biljka(
            naziv = "Lavanda (Lavandula angustifolia)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Jogurt sa voćem"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO),
            slika="lavanda"
        ),
        Biljka(
            naziv="Kadulja (Salvia officinalis)",
            porodica="Lamiaceae (usnate)",
            medicinskoUpozorenje="Nije preporučljivo u trudnoći i dojenju. Može iritati osjetljivu kožu.",
            medicinskeKoristi=listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
            profilOkusa=ProfilOkusaBiljke.AROMATICNO,
            jela=listOf("Juhe", "Salate", "Umaci"),
            klimatskiTipovi=listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA, KlimatskiTip.UMJERENA),
            zemljisniTipovi=listOf(Zemljiste.PJESKOVITO, Zemljiste.GLINENO, Zemljiste.ILOVACA),
            slika = "kadulja"
        ),
        Biljka(
            naziv="Majčina dušica (Thymus vulgaris)",
            porodica="Lamiaceae (usnate)",
            medicinskoUpozorenje="Nije preporučljivo u trudnoći i dojenju.",
            medicinskeKoristi=listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa=ProfilOkusaBiljke.SLATKI,
            jela=listOf("Pečenje", "Juhe", "Čajevi"),
            klimatskiTipovi=listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA, KlimatskiTip.UMJERENA),
            zemljisniTipovi=listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO, Zemljiste.ILOVACA),
            slika="majcina_dusica"
        ),
        Biljka(
            naziv="Origano (Origanum vulgare)",
            porodica="Lamiaceae (usnate)",
            medicinskoUpozorenje="Nije preporučljivo u trudnoći i dojenju.",
            medicinskeKoristi=listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa=ProfilOkusaBiljke.GORKO,
            jela=listOf("Pica", "Tjestenina", "Salate"),
            klimatskiTipovi=listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA, KlimatskiTip.UMJERENA),
            zemljisniTipovi=listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO, Zemljiste.ILOVACA),
            slika="origano"
        ),
        Biljka(
            naziv="Rukola (Diplotaxis tenuifolia)",
            porodica="Brassicaceae (krstašice)",
            medicinskoUpozorenje="Nije preporučljivo u trudnoći i dojenju.",
            medicinskeKoristi=listOf(MedicinskaKorist.REGULACIJAPRITISKA, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa=ProfilOkusaBiljke.LJUTO,
            jela=listOf("Salate", "Sendviči", "Tjestenina"),
            klimatskiTipovi=listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA, KlimatskiTip.UMJERENA),
            zemljisniTipovi=listOf(Zemljiste.PJESKOVITO, Zemljiste.GLINENO, Zemljiste.ILOVACA),
            slika="rukola"
        ),
        Biljka(
            naziv="Vlasac (Allium schoenoprasum L.)",
            porodica="Amaryllidaceae (amarilise)",
            medicinskoUpozorenje="Nema poznatih nuspojava uz umjerenu upotrebu. Visoka doza može izazvati probleme s probavom.",
            medicinskeKoristi=listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.PROTUUPALNO),
            profilOkusa=ProfilOkusaBiljke.KORIJENASTO,
            jela=listOf("Salate", "Umaci", "Sirevi"),
            klimatskiTipovi=listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA, KlimatskiTip.UMJERENA),
            zemljisniTipovi=listOf(Zemljiste.PJESKOVITO, Zemljiste.GLINENO, Zemljiste.ILOVACA),
            slika="vlasac"
        )
    )
}