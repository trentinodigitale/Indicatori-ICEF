create table ABSOC_dati
(
    ID_Domanda                     int not null
        primary key,
    progetto_sociale               int,
    invalidita_riconosciuta        int,
    consapevolezza_incompatibilita int,
    trattamento_dati_personali     int
)
go

create table AC_IA_DECORRENZA
(
    id_domanda      int not null
        constraint PK_AC_IA_DECORRENZA
            primary key
                with (fillfactor = 95),
    data_decorrenza datetime
)
go

create table AC_WS_Comunicazioni
(
    ID_Domanda     int      not null,
    data           datetime not null,
    ID_Soggetto    int,
    operazione     smallint,
    request        nvarchar(max),
    response       nvarchar(max),
    ID_evento_APSS bigint,
    constraint PK_AC_WS_Comunicazioni_1
        primary key (ID_Domanda, data)
)
go

create table AC_WS_EventiDaProcessare
(
    ID_Evento_APSS             bigint not null
        constraint PK_AC_WS_EventiDaProcessare
            primary key
                with (fillfactor = 95),
    ID_domanda                 int,
    data_schedule              datetime,
    nrTentativo                smallint,
    data_inserimentoVariazione datetime,
    tipoEvento                 smallint,
    ID_Evento_APSS_Corretto    bigint,
    note                       nvarchar(max)
)
go

create table AC_annotazioni
(
    ID_soggetto      int            not null,
    ID_annotazione   int            not null,
    data_annotazione datetime       not null,
    annotazione      nvarchar(4000) not null,
    constraint PK_AC_annotazioni
        primary key (ID_soggetto, ID_annotazione)
)
go

create table AC_configurazione
(
    Id_servizio          int            not null,
    Id_periodo           int            not null,
    Inizio_vettore       datetime,
    Inizio_periodo       datetime,
    Fine_periodo         datetime,
    Check_riaccertamento datetime,
    Sup                  numeric(18, 5),
    Max1                 numeric(18, 5),
    Max2                 numeric(18, 5),
    Max3                 numeric(18, 5),
    Max4                 numeric(18, 5),
    Min1                 numeric(18, 5),
    Min2                 numeric(18, 5),
    Min3                 numeric(18, 5),
    Min4                 numeric(18, 5),
    Detraz_65            numeric(18, 5),
    progressivo_elab     int,
    Franchigia_ast       numeric(18, 5) not null,
    QBI                  numeric(18, 5),
    coeff_invalidita     numeric(18, 5)
)
go

create table AC_fascicoli
(
    id_soggetto       int not null,
    id_fascicolo      int not null,
    id_domanda        int not null
        constraint PK_AC_fascicoli
            primary key
                with (fillfactor = 95),
    id_domanda_uscita int
)
go

create table AC_situazioni_mesi
(
    ID_soggetto  int      not null,
    ID_fascicolo int      not null,
    ID_domanda   int      not null,
    ID_anno      smallint not null,
    ID_mese      smallint not null,
    importo      float    not null,
    livello      smallint not null,
    giorni       smallint not null,
    constraint PK_AC_situazioni_mesi
        primary key (ID_soggetto, ID_fascicolo, ID_domanda, ID_anno, ID_mese)
            with (fillfactor = 95)
)
go

create table AC_tp_assistenze_attuali
(
    ID_tp_assistenza_attuale smallint      not null
        constraint PK_AC_tp_assistenze_attuali
            primary key,
    tp_assistenza_attuale    nvarchar(250) not null,
    codice_apss              smallint      not null
)
go

create table AC_tp_cessioni
(
    ID_tp_cessione smallint      not null
        constraint PK_AC_tp_cessioni
            primary key,
    tp_cessione    nvarchar(250) not null
)
go

create table AC_tp_legami_rif
(
    ID_tp_legame_rif smallint     not null
        constraint PK_AC_tp_legami_rif
            primary key,
    tp_legame_rif    nvarchar(50) not null,
    codice_apss      smallint     not null
)
go

create table AC_tp_professioni_rif
(
    ID_tp_professione_rif smallint     not null
        constraint PK_AC_tp_professioni_rif
            primary key,
    tp_professione_rif    nvarchar(50) not null,
    codice_apss           smallint     not null
)
go

create table AC_tp_provvidenze
(
    ID_tp_provvidenza smallint      not null
        constraint PK_AC_tp_provvidenze
            primary key
                with (fillfactor = 95),
    tp_provvidenza    nvarchar(100) not null
)
go

create table AC_provvidenze_esterne
(
    codice_fiscale    nchar(16)      not null,
    ID_tp_provvidenza smallint       not null
        constraint FK_AC_provvidenze_esterne_AC_tp_provvidenze
            references AC_tp_provvidenze,
    importo           numeric(12, 2) not null,
    titolare          nvarchar(60),
    constraint PK_AC_provvidenze_esterne_1
        primary key (codice_fiscale, ID_tp_provvidenza)
            with (fillfactor = 95)
)
go

create table AC_tp_residenze
(
    ID_tp_residenza smallint      not null
        constraint PK_AC_tp_residenze
            primary key
                with (fillfactor = 95),
    tp_residenza    nvarchar(250) not null
)
go

create table AC_tp_stati_civili_ass
(
    ID_tp_stato_civile_ass smallint     not null
        constraint PK_AC_tp_stati_civili_ass
            primary key,
    tp_stato_civile_ass    nvarchar(50) not null,
    codice_apss            smallint     not null
)
go

create table AC_tp_titoli_studio_ass
(
    ID_tp_titolo_studio_ass smallint     not null
        constraint PK_AC_tp_titoli_studio_ass
            primary key,
    tp_titolo_studio_ass    nvarchar(50) not null,
    codice_apss             smallint     not null
)
go

create table AC_tp_variazioni_categorie
(
    ID_tp_variazione_categoria smallint      not null
        constraint PK_AC_tp_variazioni
            primary key,
    tp_variazione_categoria    nvarchar(100) not null
)
go

create table AC_tp_variazioni_scope
(
    id_tp_variazione_scope smallint not null
        constraint PK_AC_tp_scope_variazioni
            primary key,
    nome_colonna           nvarchar(255),
    descrizione            nvarchar(255)
)
go

create table AC_tp_variazioni
(
    ID_tp_variazione           smallint      not null
        constraint PK_AC_tp_cessazioni2
            primary key
                with (fillfactor = 95),
    tp_variazione              nvarchar(100) not null,
    data_profilo_rl            smallint,
    data_inizio_rl             smallint,
    data_fine_rl               smallint,
    codice_apss                smallint,
    codice_anaweb              nchar(10),
    ID_tp_variazione_categoria smallint
        constraint FK_AC_tp_variazioni_AC_tp_variazioni_categorie
            references AC_tp_variazioni_categorie,
    ID_tp_variazione_scope     smallint
        constraint FK_AC_tp_variazioni_AC_tp_variazioni_scope
            references AC_tp_variazioni_scope
)
go

create table AC_variazioni
(
    ID_variazione     int identity
        constraint PK_AC_variazioni_1
            primary key
                with (fillfactor = 95),
    ID_domanda        int      not null,
    ID_tp_variazione  smallint not null
        constraint FK_AC_variazioni_AC_tp_variazioni
            references AC_tp_variazioni,
    data_inizio       datetime,
    data_fine         datetime,
    data_variazione   datetime,
    valore            numeric(12, 2),
    ID_evento_APSS    bigint,
    codice_ric_apss   nvarchar(50),
    rivalutazione_eta smallint not null
)
go

create index _dta_index_AC_variazioni_8_1674593154__K2_K3_K1_K4_K5_K7
    on AC_variazioni (ID_domanda, ID_tp_variazione, ID_variazione, data_inizio, data_fine, valore)
    with (fillfactor = 95)
go

create table AC_variazioni_soggetti
(
    ID_variazione    int identity
        constraint PK_AC_variazioni_soggetto
            primary key
                with (fillfactor = 95),
    ID_soggetto      int      not null,
    ID_tp_variazione smallint not null
        constraint FK_AC_variazioni_soggetti_AC_tp_variazioni
            references AC_tp_variazioni,
    data_inizio      datetime,
    data_fine        datetime,
    data_variazione  datetime,
    valore           numeric(12, 2),
    ID_evento_APSS   bigint,
    codice_ric_apss  nvarchar(50),
    origine_ric      nvarchar(50)
)
go

create index _dta_index_AC_variazioni_soggetti_8_1605736923__K2_K3_K1_K4_K5_K7
    on AC_variazioni_soggetti (ID_soggetto, ID_tp_variazione, ID_variazione, data_inizio, data_fine, valore)
    with (fillfactor = 95)
go

create index _dta_index_AC_variazioni_soggetti_7_1605736923__K10_K9_4_5
    on AC_variazioni_soggetti (origine_ric, codice_ric_apss) include (data_inizio, data_fine)
    with (fillfactor = 95)
go

create table ALV_fondi_pensplan
(
    ID_fondo               int not null,
    ID_servizio            int not null,
    tipo_fondo             nvarchar(100),
    nome_fondo             nvarchar(500),
    societa                nvarchar(250),
    convenzionato_pensplan int,
    indirizzo              nvarchar(100),
    localita               nvarchar(100),
    cap                    nchar(10),
    provincia              nchar(10),
    ordine                 int,
    n_covip                int,
    visibile               int,
    updated                int,
    constraint PK_ALV_fondi_pensplan
        primary key (ID_fondo, ID_servizio)
            with (fillfactor = 95)
)
go

create table AMS_Dati
(
    id_domanda             int not null
        constraint PK_AMS_dich
            primary key
                with (fillfactor = 95),
    domicilio              bit,
    id_comune_domicilio    nvarchar(6),
    id_provincia_domicilio nvarchar(2),
    domicilio_cap          nvarchar(10),
    domicilio_frazione     nvarchar(50),
    domicilio_indirizzo    nvarchar(50),
    domicilio_n_civico     nvarchar(10),
    condanne_penali        bit,
    txt_condanne           nvarchar(500),
    percorso_formativo     bit,
    esperienza             bit,
    esperienza_prof        bit,
    txt_assist             nvarchar(400),
    txt_sanitario          nvarchar(400),
    txt_legale             nvarchar(400),
    txt_contabile          nvarchar(400),
    note                   nvarchar(200),
    permesso_soggiorno     bit,
    email_pec              nvarchar(50),
    cellulare              nvarchar(10)
)
go

create table AMS_Determine
(
    ID_Determina  int not null
        constraint PK_AMS_Determine
            primary key
                with (fillfactor = 95),
    Numero        int,
    DataDetermina datetime,
    Cancella      bit
)
go

create table AMS_Registro
(
    ID_soggetto                int not null,
    codice_fiscale             nvarchar(16),
    cognome                    nvarchar(35),
    nome                       nvarchar(35),
    ID_tp_sex                  nvarchar,
    ID_provincia_nascita       nvarchar(2),
    ID_luogo_nascita           nvarchar(6),
    data_nascita               datetime,
    ID_provincia_residenza     nvarchar(2),
    ID_luogo_residenza         nvarchar(6),
    frazione_residenza         nvarchar(50),
    indirizzo_residenza        nvarchar(50),
    n_civ_residenza            nvarchar(10),
    cap_residenza              nvarchar(10),
    telefono_residenza         nvarchar(20),
    cellulare                  nvarchar(50),
    e_mail                     nvarchar(50),
    email_pec                  nvarchar(50),
    ID_tp_cittadinanza         nvarchar(4),
    domicilio                  bit,
    id_luogo_domicilio         nvarchar(6),
    id_provincia_domicilio     nvarchar(2),
    domicilio_cap              nvarchar(10),
    domicilio_frazione         nvarchar(50),
    domicilio_indirizzo        nvarchar(50),
    domicilio_n_civico         nvarchar(10),
    condanne_penali            bit,
    txt_condanne               nvarchar(500),
    percorso_formativo         bit,
    esperienza                 bit,
    esperienza_prof            bit,
    txt_assist                 nvarchar(400),
    txt_sanitario              nvarchar(400),
    txt_legale                 nvarchar(400),
    txt_contabile              nvarchar(400),
    note                       nvarchar(200),
    permesso_soggiorno         bit,
    Esito                      bit,
    Eliminato                  bit,
    DataFineIscrizione         datetime,
    ID_domanda                 int not null
        constraint PK_AMS_Registro
            primary key
                with (fillfactor = 95),
    ID_Determina_Iscrizione    int,
    ID_Determina_Cancellazione int,
    ID_User_UltimaModifica     int,
    Data_UltimaModifica        datetime
)
go

create table AMS_Utenti
(
    ID_User               int           not null
        constraint PK_AMS_Utenti
            primary key
                with (fillfactor = 95),
    username              nvarchar(255) not null
        constraint CS_Username_Unique
            unique
                with (fillfactor = 95),
    password              nvarchar(255) not null,
    WritePermission       bit           not null,
    password_change_date  datetime,
    password_change_token nvarchar(4000),
    account_expiration    datetime,
    password_expiration   datetime,
    account_locked        bit,
    enabled               bit,
    email                 nvarchar(50),
    surname               nvarchar(50),
    name                  nvarchar(50)
)
go

create table AMS_ruoli
(
    ID_Role     int not null
        constraint PK_AMS_ruoli
            primary key
                with (fillfactor = 95),
    rolename    nvarchar(50),
    description nvarchar(150)
)
go

create table AMS_tp_disponibilita
(
    ID_tp_disponibilita int not null
        constraint PK_AMS_tp_disponibilita
            primary key
                with (fillfactor = 95),
    tp_disponibilita    nvarchar(100),
    ID_servizio         int,
    ordine              int
)
go

create table AMS_disponibilita
(
    id_domanda          int not null
        constraint FK_AMS_disponibilita_AMS_Dati
            references AMS_Dati,
    id_tp_disponibilita int not null
        constraint FK_AMS_disponibilita_AMS_tp_disponibilita
            references AMS_tp_disponibilita,
    constraint PK_AMS_disponibilita
        primary key (id_domanda, id_tp_disponibilita)
)
go

create table AMS_tp_stati_incarico
(
    ID_tp_stato_incarico smallint not null
        constraint PK_AMS_tp_stati_incarico
            primary key
                with (fillfactor = 95),
    tp_stato_incarico    varchar(50)
)
go

create table AMS_utenti_ruoli
(
    ID_user int
        constraint FK_AMS_utenti_ruoli_AMS_utenti
            references AMS_Utenti,
    ID_role int
        constraint FK_AMS_utenti_ruoli_AMS_ruoli
            references AMS_ruoli
)
go

create table AM_ID
(
    ID_domanda int not null
        constraint PK_AM_ID
            primary key
                with (fillfactor = 95)
)
go

create table AM_Rendicontazione
(
    ID_domanda         int      not null,
    ID_mese            smallint not null,
    anno               int      not null,
    ID_ente            int      not null,
    assegno            float,
    sospesa            smallint,
    importo_anticipato float,
    importo_mensile    float,
    arretrati_dal_mese smallint,
    arretrati_al_mese  smallint,
    arretrati_dal_anno int,
    arretrati_al_anno  int,
    constraint PK_AM_Rendicontazione
        primary key (ID_domanda, ID_mese, anno, ID_ente)
            with (fillfactor = 70)
)
go

create table AM_mediazione
(
    codice_fiscale nvarchar(16) not null,
    ID_ente        int          not null,
    cognome        nvarchar(50),
    nome           nvarchar(50),
    mediazione     smallint,
    anno           smallint,
    constraint PK_AM_mediazione
        primary key (codice_fiscale, ID_ente)
            with (fillfactor = 70)
)
go

create table AM_mesi
(
    ID_mese      smallint not null,
    anno         int      not null,
    ID_ente      int      not null,
    mese         nvarchar(50),
    definitivo   smallint,
    data_mandato datetime,
    constraint PK_AM_mesi
        primary key (ID_mese, anno, ID_ente)
            with (fillfactor = 70)
)
go

create table AM_tp_rapporti
(
    ID_tp_rapporto smallint not null
        constraint PK_AM_tp_rapporti
            primary key
                with (fillfactor = 95),
    tp_rapporto    nvarchar(50)
)
go

create table AM_tp_situazione_domanda
(
    ID_tp_variazione_situazione_dom smallint not null
        constraint PK_AM_tp_situazione_domanda
            primary key
                with (fillfactor = 95),
    tp_variazione_situazione_dom    nvarchar(50)
)
go

create table ANF_data_inizio_beneficio
(
    ID_domanda            int      not null
        constraint PK_ANF_data_inizio_beneficio
            primary key
                with (fillfactor = 95),
    data_inizio_beneficio datetime not null,
    motivazione           nvarchar(200)
)
go

create table ANF_erogazioni
(
    anno              smallint       not null,
    codice_fiscale    nvarchar(16)   not null,
    codice_intervento smallint       not null,
    importo           decimal(12, 2) not null,
    constraint PK_ANF_erogazioni
        primary key (anno, codice_fiscale, codice_intervento)
            with (fillfactor = 90)
)
go

create table ANF_tp_anno_contributi
(
    ID_servizio int       not null
        constraint PK_ANF_tp_anno_contributi
            primary key
                with (fillfactor = 95),
    anno        nchar(10) not null
)
go

create table ANF_tp_cittadinanza
(
    ID_tipo_cittadinanza smallint not null
        constraint PK_ANF_tp_cittadinanza
            primary key
                with (fillfactor = 95),
    ID_servizio          int,
    tp_cittadinanza      nvarchar(250)
)
go

create table ANF_tp_occupazione
(
    ID_tp_occupazione smallint not null
        constraint PK_ANF_tp_occupazione
            primary key
                with (fillfactor = 95),
    tp_occupazione    nvarchar(250)
)
go

create table ANF_tp_un_genitore
(
    ID_tp_un_genitore smallint not null
        constraint PK_ANF_tp_un_genitore
            primary key
                with (fillfactor = 95),
    tp_un_genitore    nvarchar(250)
)
go

create table ANF_trasmissioni
(
    id_trasmissione            int          not null,
    id_domanda                 int          not null,
    periodo                    nvarchar(50),
    data_sottoscrizione        datetime,
    data_trasmissione          datetime     not null,
    nDomandaVar                int          not null,
    sportello                  int          not null,
    ufficio                    int          not null,
    tipoDomanda                int          not null,
    data_presentazione         datetime     not null,
    annoRif                    int          not null,
    cognome                    nvarchar(35) not null,
    nome                       nvarchar(35) not null,
    codice_fiscale             nvarchar(16) not null,
    comuneStatoNascita         nvarchar(35) not null,
    provinciaNascita           nvarchar(2)  not null,
    dataNascita                datetime     not null,
    cap                        nvarchar(50) not null,
    comune                     nvarchar(50) not null,
    provincia                  nvarchar(50) not null,
    frazione                   nvarchar(50),
    indirizzo                  nvarchar(50),
    nCivico                    nvarchar(50) not null,
    telefono                   nvarchar(50),
    tipoPagamento              nvarchar(50),
    numeroConto                nvarchar(12),
    cin                        nvarchar(3),
    cinConf                    smallint     not null,
    banca_pagamento            nvarchar(50),
    ubicazione_banca_pagamento nvarchar(50),
    abi                        nvarchar(5),
    cab                        nvarchar(5),
    a01                        float,
    a02                        float,
    a03                        float,
    a04                        float,
    a05                        float,
    a06                        float,
    a07                        float,
    a08                        float,
    a09                        float,
    a10                        float,
    a11                        float,
    a12                        float,
    a13                        float,
    a14                        float,
    a15                        float,
    a16                        float,
    a17                        float,
    a18                        float,
    tabUltimaRata              nvarchar(50) not null,
    rigaTabUltimaRata          int          not null,
    colTabUltimaRata           int          not null,
    azione                     int          not null,
    crc                        int          not null,
    ibanConf                   smallint,
    iban                       nvarchar(50),
    codiceStato                nvarchar(2),
    edizione                   int          not null,
    cf_altro_percettore        nvarchar(16),
    tp_indirizzo_altro         nvarchar(50),
    cap_altro                  nvarchar(50),
    comune_altro               nvarchar(50),
    provincia_altro            nvarchar(50),
    frazione_altro             nvarchar(50),
    indirizzo_altro            nvarchar(50),
    ncivico_altro              nvarchar(50),
    telefono_altro             nvarchar(50),
    pensplan_rinuncia          smallint,
    ID_codice_covip            int,
    progressivo_anagrafica     int,
    firma_grafometrica         smallint,
    domanda_unica              smallint,
    a19                        float,
    altre_pensioni             int,
    cambio_mod_pag             int,
    a20                        float,
    a21                        float,
    a22                        float,
    a23                        float,
    a24                        float,
    a25                        float,
    a26                        float,
    a27                        float,
    a28                        float,
    a29                        float,
    a30                        float,
    stato_domanda              float,
    constraint PK_ANF_trasmissioni
        primary key (id_trasmissione, id_domanda, tipoDomanda)
            with (fillfactor = 95)
)
go

create table ANF_trasmissioni_familiari
(
    id_trasmissione       int          not null,
    id_domanda            int          not null,
    id_soggetto           int          not null,
    codice_fiscale        nvarchar(16) not null,
    pagato                smallint     not null,
    data_modifica_cf      datetime,
    id_domanda_collisione int,
    constraint PK_ANF_trasmissioni_familiari
        primary key (id_trasmissione, id_domanda, codice_fiscale)
            with (fillfactor = 95)
)
go

create table ANF_trasmissioni_tipo_domanda
(
    id_servizio      int          not null,
    tipoDomanda      smallint     not null,
    nome_tabella_doc nvarchar(50) not null,
    enabled          smallint     not null,
    constraint PK_ANF_trasmissioni_tipo_domanda
        primary key (id_servizio, tipoDomanda)
            with (fillfactor = 95)
)
go

create table ANTICRISI_budget
(
    id_servizio    int   not null,
    budget_attuale float not null,
    budget_max     float not null
)
go

create table ANTICRISI_periodi_import
(
    periodo_import         int not null
        constraint PK_ANTICRISI_periodi_import
            primary key
                with (fillfactor = 95),
    codice_fiscale         nvarchar(16),
    periodo_dal            datetime,
    periodo_al             datetime,
    ID_tp_periodo          smallint,
    ore_cassa_integrazione int,
    presso                 nvarchar(50),
    ore_cassa_totali       int
)
go

create table ANTICRISI_sospensioni_import
(
    periodo_import     int not null
        constraint FK_ANTICRISI_sospensioni_import_ANTICRISI_periodi_import
            references ANTICRISI_periodi_import,
    sospensione_import int not null,
    sospensione_dal    datetime,
    sospensione_al     datetime,
    constraint PK_ANTICRISI_sospensioni_import
        primary key (periodo_import, sospensione_import)
            with (fillfactor = 95)
)
go

create table ANTICRISI_tp_codici_covip
(
    ID_codice_covip smallint not null
        constraint PK_ANTICRISI_tp_codici_covip
            primary key
                with (fillfactor = 95),
    descrizione     nvarchar(250)
)
go

create table ANTICRISI_tp_motivazioni
(
    ID_tp_motivazione smallint not null
        constraint PK_ANTICRISI_tp_motivazioni
            primary key
                with (fillfactor = 95),
    tp_motivazione    nvarchar(2500)
)
go

create table ANTICRISI_tp_periodi
(
    ID_tp_periodo smallint     not null
        constraint PK_ANTICRISI_tp_periodi
            primary key
                with (fillfactor = 95),
    tp_periodo    nvarchar(50) not null
)
go

create table ANTIV_Autori
(
    ID_soggetto                    int not null,
    ID_autore                      int not null,
    ID_tp_autore                   int,
    eta                            int,
    ID_tp_residenza                int,
    ID_tp_cittadinanza             nvarchar(6),
    ID_tp_titolo_studio            int,
    ID_tp_condizione_occupazionale int,
    ID_tp_provvedimenti_giudiziari int,
    constraint PK_ANTIV_Autori
        primary key (ID_soggetto, ID_autore)
            with (fillfactor = 95)
)
go

create table ANTIV_Enti
(
    ID_ente       int           not null
        constraint PK_ANTIV_Enti
            primary key
                with (fillfactor = 95),
    ente          nvarchar(70),
    codice_ente   nvarchar(15),
    tp_ente       nvarchar(3),
    enabled       smallint      not null,
    luogo         nvarchar(50),
    prov          nvarchar(50),
    cap           nvarchar(6),
    pref          nvarchar(5),
    regione       nvarchar(50),
    header        nvarchar(200) not null,
    footer        nvarchar(200) not null,
    hashCriptoKey nvarchar(100)
)
go

create table ANTIV_Figli
(
    ID_soggetto             int not null,
    id_figlio               int not null,
    data_nascita            datetime,
    assistito_a_violenza    smallint,
    data_ingresso_struttura datetime,
    data_uscita_struttura   datetime,
    constraint PK_ANTIV_Figli
        primary key (ID_soggetto, id_figlio)
            with (fillfactor = 95)
)
go

create table ANTIV_Soggetti
(
    ID_soggetto                       int not null
        constraint PK_ANTIV_Soggetti
            primary key
                with (fillfactor = 95),
    ID_user                           int not null,
    data_inserimento                  datetime,
    cognome                           nvarchar(300),
    sha1cognome                       nchar(60),
    nome                              nvarchar(300),
    sha1nome                          nchar(60),
    data_nascita                      nvarchar(300),
    codice_fiscale                    nvarchar(300),
    sha1codice_fiscale                nchar(60),
    ID_tp_residenza                   int,
    ID_tp_cittadinanza                int,
    ID_nazionalita                    nvarchar(300),
    ID_tp_stato_civile                int,
    ID_tp_titolo_studio               int,
    ID_tp_occupazione                 int,
    numero_figli                      int,
    ID_tp_gravidanza                  smallint,
    ID_tp_violenza                    int,
    ID_tp_forze_ordine                int,
    ID_tp_denuncia                    int,
    ID_tp_violenza_pregressa          smallint,
    ID_tp_violenza_pregressa_contesto int,
    ID_tp_modalita_accesso            int,
    ID_tp_modalita_accesso_emergenza  smallint,
    ID_tp_provvedimenti_giudiziari    nvarchar(10),
    ID_tp_nuovo_accesso               smallint,
    data_inizio_in_carico             datetime,
    data_fine_in_carico               datetime,
    ID_tp_interventi_residenziali     int,
    ID_tp_interventi_offerti          int,
    accesso_situazione_di_emergenza   smallint,
    ID_tp_autore_violenza             nvarchar(10),
    ID_tp_sex                         nvarchar(300),
    luogo_nascita                     nvarchar(300),
    ID_provincia_residenza            nvarchar(300),
    luogo_residenza                   nvarchar(300),
    indirizzo_residenza               nvarchar(300),
    n_civ_residenza                   nvarchar(300),
    cap_residenza                     nvarchar(300),
    telefono_residenza                nvarchar(300),
    data_nascita_chiaro               datetime
)
go

create table ANTIV_Soggetti_RECUPERO
(
    ID_soggetto                       int not null,
    ID_user                           int not null,
    data_inserimento                  datetime,
    cognome                           nvarchar(300),
    sha1cognome                       nchar(60),
    nome                              nvarchar(300),
    sha1nome                          nchar(60),
    data_nascita                      nvarchar(300),
    codice_fiscale                    nvarchar(300),
    sha1codice_fiscale                nchar(60),
    ID_tp_residenza                   int,
    ID_tp_cittadinanza                int,
    ID_nazionalita                    nvarchar(300),
    ID_tp_stato_civile                int,
    ID_tp_titolo_studio               int,
    ID_tp_occupazione                 int,
    numero_figli                      int,
    ID_tp_gravidanza                  smallint,
    ID_tp_violenza                    int,
    ID_tp_forze_ordine                int,
    ID_tp_denuncia                    int,
    ID_tp_violenza_pregressa          smallint,
    ID_tp_violenza_pregressa_contesto int,
    ID_tp_modalita_accesso            int,
    ID_tp_modalita_accesso_emergenza  smallint,
    ID_tp_provvedimenti_giudiziari    nvarchar(10),
    ID_tp_nuovo_accesso               smallint,
    data_inizio_in_carico             datetime,
    data_fine_in_carico               datetime,
    ID_tp_interventi_residenziali     int,
    ID_tp_interventi_offerti          int,
    accesso_situazione_di_emergenza   smallint,
    ID_tp_autore_violenza             nvarchar(10),
    ID_tp_sex                         nvarchar(300),
    luogo_nascita                     nvarchar(300),
    ID_provincia_residenza            nvarchar(300),
    luogo_residenza                   nvarchar(300),
    indirizzo_residenza               nvarchar(300),
    n_civ_residenza                   nvarchar(300),
    cap_residenza                     nvarchar(300),
    telefono_residenza                nvarchar(300)
)
go

create table ANTIV_Soggetti_interventi_offerti
(
    ID_soggetto               int not null
        constraint PK_ANTIV_Soggetti_interventi_offerti
            primary key,
    colloqui_individuali      smallint,
    protezione_tutela         smallint,
    accomp_visite             smallint,
    reinserimento             smallint,
    pronta_emergenza          smallint,
    percorsi_rivolti_figli    smallint,
    consulenza_psico_sociale  smallint,
    consulenza_legale         smallint,
    supporto_genitoriale      smallint,
    percorsi_rieducazione     smallint,
    consulenza_familiari      smallint,
    visite_protette           smallint,
    fornitura_vestiario       smallint,
    valutazione_rischio       smallint,
    testimonianza_tribunale   smallint,
    cons_spec_altri_operatori smallint
)
go

create table ANTIV_Soggetti_interventi_residenziali
(
    ID_soggetto                   int not null,
    ID_intervento_residenziale    int not null,
    ID_tp_intervento_residenziale int not null,
    data_inizio                   datetime,
    data_fine                     datetime,
    constraint PK_ANTIV_Soggetti_interventi_residenziali
        primary key (ID_soggetto, ID_intervento_residenziale)
)
go

create table ANTIV_Soggetti_tipologie_violenze
(
    ID_soggetto          int not null
        constraint PK_ANTIV_Soggetti_tipologie_violenze
            primary key
                with (fillfactor = 95),
    tratta               smallint,
    violenza_fisica      smallint,
    violenza_psicologica smallint,
    violenza_sessuale    smallint,
    violenza_economica   smallint,
    stalking             smallint
)
go

create table ANTIV_Uffici
(
    ID_ente       int      not null
        constraint FK_ANTIV_Uffici_ANTIV_Enti
            references ANTIV_Enti,
    ID_ufficio    int      not null,
    ufficio       nvarchar(50),
    indirizzo     nvarchar(50),
    tel           nvarchar(20),
    email         nvarchar(50),
    ftp           nvarchar(80),
    enabled       smallint not null,
    luogo         nvarchar(50),
    prov          nvarchar(50),
    cap           nvarchar(6),
    pref          nvarchar(5),
    regione       nvarchar(50),
    hashCriptoKey nvarchar(100),
    constraint PK_ANTIV_Uffici
        primary key (ID_ente, ID_ufficio)
)
go

create table ANTIV_Utenti
(
    ID_user             int          not null
        constraint PK_ANTIV_Utenti
            primary key,
    username            nvarchar(15) not null,
    descrizione         nvarchar(30) not null,
    password            nvarchar(10) not null,
    ID_ente             int
        constraint FK_ANTIV_Utenti_ANTIV_Enti
            references ANTIV_Enti,
    ID_ufficio          int,
    enabled             smallint     not null,
    tel                 nvarchar(20),
    fax                 nvarchar(20),
    email               nvarchar(100),
    data_modifica_pw    datetime,
    data_ultimo_accesso datetime,
    responsabileEnte    smallint     not null,
    email_cer           nvarchar(100),
    hashCriptoKey       nvarchar(100)
)
go

create table ANTIV_autore_occupazione
(
    ID_tp_occupazione int not null
        constraint PK_ANTIV_autore_occupazione
            primary key
                with (fillfactor = 95),
    tp_occupazione    nvarchar(500)
)
go

create table ANTIV_autore_provvedimenti_giudiziari
(
    ID_tp_provvedimenti_giudiziari int           not null
        constraint PK_ANTIV_autore_provvedimenti_giudiziari
            primary key
                with (fillfactor = 95),
    tp_provvedimenti_giudiziari    nvarchar(500) not null
)
go

create table ANTIV_autore_violenza
(
    ID_tp_autore_violenza int           not null
        constraint PK_ANTIV_autore_violenza
            primary key
                with (fillfactor = 95),
    tp_autore_violenza    nvarchar(500) not null
)
go

create table ANTIV_cittadinanza
(
    ID_tp_cittadinanza int       not null
        constraint PK_ANTIV_cittadinanza
            primary key
                with (fillfactor = 95),
    tp_cittadinanza    nchar(30) not null
)
go

create table ANTIV_dati2015_annoRif2014
(
    ID_soggetto int not null
)
go

create table ANTIV_dati2016_annoRif2015
(
    ID_soggetto int not null
)
go

create table ANTIV_dati2016_annoRif2015_NO_RES
(
    ID_soggetto int not null
)
go

create table ANTIV_dati2017_annoRif2016
(
    ID_soggetto int not null
)
go

create table ANTIV_dati2017_annoRif2016_NO_RES
(
    ID_soggetto int not null
)
go

create table ANTIV_dati2018_annoRif2017
(
    ID_soggetto int not null
)
go

create table ANTIV_dati2018_annoRif2017_NO_RES
(
    ID_soggetto int not null
)
go

create table ANTIV_denuncia
(
    ID_tp_denuncia int           not null
        constraint PK_ANTIV_denuncia
            primary key
                with (fillfactor = 95),
    tp_denuncia    nvarchar(500) not null
)
go

create table ANTIV_forze_ordine
(
    id_tp_forze_ordine int not null
        constraint PK_ANTIV_forze_ordine
            primary key
                with (fillfactor = 95),
    tp_forze_ordine    nvarchar(50)
)
go

create table ANTIV_interventi_offerti
(
    ID_tp_interventi_offerti int           not null
        constraint PK_ANTIV_interventi_offerti
            primary key
                with (fillfactor = 95),
    tp_interventi_offerti    nvarchar(500) not null
)
go

create table ANTIV_interventi_residenziali
(
    ID_tp_interventi_residenziali int           not null
        constraint PK_ANTIV_interventi_residenziali
            primary key
                with (fillfactor = 95),
    tp_interventi_residenziali    nvarchar(500) not null
)
go

create table ANTIV_modalita_accesso
(
    ID_tp_modalita_accesso int           not null
        constraint PK_ANTIV_modalita_accesso
            primary key
                with (fillfactor = 95),
    tp_modalita_accesso    nvarchar(500) not null
)
go

create table ANTIV_occupazione
(
    ID_tp_occupazione int           not null
        constraint PK_ANTIV_occupazione
            primary key
                with (fillfactor = 95),
    tp_occupazione    nvarchar(500) not null
)
go

create table ANTIV_residenza
(
    ID_tp_residenza int           not null
        constraint PK_ANTIV_residenza
            primary key
                with (fillfactor = 95),
    tp_residenza    nvarchar(500) not null
)
go

create table ANTIV_stato_civile
(
    ID_tp_stato_civile int           not null
        constraint PK_ANTIV_stato_civile
            primary key,
    tp_stato_civile    nvarchar(500) not null
)
go

create table ANTIV_titolo_studio
(
    ID_tp_titolo_studio int           not null
        constraint PK_ANTIV_titolo_studio
            primary key,
    tp_titolo_studio    nvarchar(500) not null
)
go

create table ANTIV_violenza
(
    ID_tp_violenza int           not null
        constraint PK_ANTIV_violenza
            primary key,
    tp_violenza    nvarchar(500) not null
)
go

create table ANTIV_violenze_pregresse
(
    ID_tp_violenza_pregressa int          not null
        constraint PK_ANTIV_violenze_pregresse
            primary key
                with (fillfactor = 95),
    tp_violenza_pregressa    nvarchar(50) not null
)
go

create table APSSPSNA_Elaborazioni_Run
(
    ID_elaborazione_run    int identity
        constraint PK_APSSPSNA_Elaborazioni_Run
            primary key
                with (fillfactor = 95),
    ID_tp_elaborazione_run smallint not null,
    data_run               datetime not null
)
go

create table APSSPSNA_MODODONT_Aggiornamenti
(
    ID_Aggiornamento               int identity
        constraint PK_APSSPSNA_MODODONT_Aggiornamenti
            primary key
                with (fillfactor = 95),
    ID_servizio                    int            not null,
    codice_movimento               nvarchar(17)   not null,
    data_movimento                 date           not null,
    codice_fiscale_utente          nvarchar(16)   not null,
    ID_anagrafica_apss             nvarchar(10)   not null,
    importo_prestazione            numeric(10, 2) not null,
    quantita                       numeric(10, 2) not null,
    importo_calcolato_da_ICEF      numeric(10, 2) not null,
    ID_domanda                     int,
    edizione                       int,
    data_edizione                  datetime,
    icef                           numeric(10, 4) not null,
    tipo_variazione                varchar,
    delta                          numeric(10, 2),
    ID_ente_apsspsna               int            not null,
    ID_comunicazione_movimenti     int,
    ID_comunicazione_aggiornamenti int,
    ID_comunicazione_conferma      int,
    in_coda                        bit            not null
)
go

create table APSSPSNA_R_Enti
(
    ID_ente_apsspsna int           not null
        constraint PK_APSSPSNA_R_Enti
            primary key
                with (fillfactor = 95),
    ente             nvarchar(255) not null
)
go

create table APSSPSNA_MODODONT_Movimenti
(
    ID_movimento               int identity
        constraint PK_APSSPSNA_MODODONT_Movimenti
            primary key
                with (fillfactor = 95),
    ID_servizio                int            not null,
    codice_movimento           nvarchar(17)   not null,
    codice_prestazione         nvarchar(17)   not null,
    data_invio                 datetime       not null,
    data_movimento             date           not null,
    codice_fiscale_utente      nvarchar(16)   not null,
    ID_anagrafica_apss         nvarchar(10)   not null,
    importo_prestazione        numeric(10, 2) not null,
    quantita                   numeric(10, 2) not null,
    importo_calcolato_da_APSS  numeric(10, 2) not null,
    su_base_icef               bit            not null,
    icef                       numeric(10, 4) not null,
    ID_domanda                 int,
    edizione                   int,
    ID_ente_apsspsna           int            not null
        constraint FK_APSSPSNA_MODODONT_Movimenti_APSSPSNA_R_Enti
            references APSSPSNA_R_Enti,
    ID_comunicazione_movimenti int            not null,
    in_coda                    bit            not null
)
go

create table APSSPSNA_tp_operazioni
(
    ID_tp_operazione smallint      not null
        constraint PK_APSSPSNA_tp_operazioni
            primary key
                with (fillfactor = 95),
    tp_operazione    nvarchar(100) not null
)
go

create table APSSPSNA_WS_Comunicazioni
(
    ID_Comunicazione int identity
        constraint PK_APSSPSNA_WS_Comunicazioni
            primary key
                with (fillfactor = 95),
    data             datetime not null,
    ID_tp_operazione smallint not null
        constraint FK_APSSPSNA_WS_Comunicazioni_APSSPSNA_tp_operazioni
            references APSSPSNA_tp_operazioni,
    request          xml,
    response         xml,
    anno             smallint,
    mese             smallint,
    provenienza      smallint,
    ID_ente_apsspsna int
        constraint FK_APSSPSNA_WS_Comunicazioni_APSSPSNA_R_Enti
            references APSSPSNA_R_Enti,
    data_upload      datetime,
    content_type     nvarchar(255)
)
go

create table ASSFIGLI_tp_genitori
(
    ID_tp_genitore int not null
        constraint PK_ASSFIGLI_tp_genitori
            primary key,
    tp_genitore    nvarchar(50)
)
go

create table ASSNA_tp_anno
(
    anno          smallint not null,
    data_scadenza datetime,
    ID_servizio   int      not null,
    constraint PK_ASSNA_tp_anno
        primary key (anno, ID_servizio)
            with (fillfactor = 95)
)
go

create table ASSNA_tp_coperture_previdenziali
(
    ID_tp_copertura_previdenziale smallint      not null
        constraint PK_ASSNA_tp_coperture_previdenziali
            primary key
                with (fillfactor = 95),
    tp_copertura_previdenziale    nvarchar(300) not null
)
go

create table ASSNA_tp_tempoCoperture_previdenziali
(
    ID_tp_tempoCoperturaPrevidenziale smallint not null
        constraint PK_ASSNA_tp_TempoCoperture_previdenziali
            primary key
                with (fillfactor = 95),
    tp_tempoCoperturaPrevidenziale    nvarchar(50)
)
go

create table ASSNA_tp_versamenti
(
    ID_tp_versamento smallint      not null
        constraint PK_ASSNA_tp_versamenti
            primary key
                with (fillfactor = 95),
    tp_versamento    nvarchar(250) not null
)
go

create table AUP_BSV_Flussi
(
    codice_fiscale_genitore nvarchar(16)  not null,
    data_avvio              date          not null,
    data_fine               date          not null,
    ore_effettive           numeric(6, 2) not null,
    costo_orario_pat        numeric(3, 2) not null,
    file_name               nvarchar(500) not null
)
go

create table AUP_C_ElaIN
(
    ID_domanda  int          not null,
    anno        int          not null,
    mese        int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_AUP_C_ElaIN
        primary key (ID_domanda, anno, mese, net, node)
)
go

create index idx_aup_in_nodi
    on AUP_C_ElaIN (node, input_value) include (ID_domanda, anno, mese, net)
go

create table AUP_C_ElaOUT
(
    ID_domanda    int          not null,
    anno          int          not null,
    mese          int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    numeric_value float,
    constraint PK_AUP_C_ElaOUT
        primary key (ID_domanda, anno, mese, net, node)
)
go

create table AUP_RdC_Autodichiarazioni
(
    ID_Domanda         int not null
        primary key,
    RedditoPensione    nvarchar,
    mese1              decimal(10, 2),
    mese2              decimal(10, 2),
    mese3              decimal(10, 2),
    mese4              decimal(10, 2),
    mese5              decimal(10, 2),
    mese6              decimal(10, 2),
    mese7              decimal(10, 2),
    mese8              decimal(10, 2),
    mese9              decimal(10, 2),
    mese10             decimal(10, 2),
    mese11             decimal(10, 2),
    mese12             decimal(10, 2),
    prot_num           nvarchar(25),
    prot_data          datetime,
    motivazione        nvarchar(100),
    mancanza_requisiti nvarchar(200)
)
go

create table AUP_RdC_Dichiarazioni
(
    ID_Domanda             int not null,
    mese                   int not null,
    importo                decimal(10, 2),
    reddito_pensione       nvarchar,
    prot_num               nvarchar(25),
    prot_data              datetime,
    esito                  nvarchar(100),
    mancanza_requisiti     nvarchar(200),
    conferma_stima         int,
    dichiarazione_presente int not null,
    constraint AUP_RdC_Dichiarazioni_PK
        primary key (ID_Domanda, mese)
)
go

create table AUP_data_inizio_beneficio
(
    ID_domanda            int      not null
        constraint PK_AUP_data_inizio_beneficio
            primary key
                with (fillfactor = 95),
    data_inizio_beneficio datetime not null,
    motivazione           nvarchar(200)
)
go

create table AUP_dati_mese
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    edizione         int not null,
    anno             int not null,
    mese             int not null,
    quotaA           int not null,
    quotaB1          int not null,
    quotaB13         int not null,
    quotaB2          int not null,
    quotaB3          int not null,
    nel_nucleo       int not null,
    blacklist        int,
    attualizzata     int,
    ID_domandaREI    int,
    importoREI       numeric(10, 2),
    ID_domanda448    int,
    importo448       numeric(10, 2),
    naspi            int,
    importoREC       numeric(10, 2),
    quotaC           int,
    quotaCAnte       int,
    constraint PK_AUP_dati_mese
        primary key (ID_domanda, ID_dichiarazione, edizione, mese)
            with (fillfactor = 95)
)
go

create index idx_AUP_dati_mese_ID_dichiarazione_anno
    on AUP_dati_mese (ID_dichiarazione, anno)
    with (fillfactor = 95)
go

create table AUP_entita
(
    id_entita     int          not null,
    anno          int          not null,
    codice_entita varchar(50)  not null,
    descrizione   varchar(150) not null,
    meseInizio    int          not null,
    meseFine      int          not null,
    tipo_flusso   varchar(10),
    constraint PK_AUP_entita
        primary key (id_entita, anno)
            with (fillfactor = 95)
)
go

create table AUP_esclusioni_ufficio
(
    ID_domanda            int not null,
    anno                  int not null,
    mese                  int not null,
    quotaA                int not null,
    quotaB1               int not null,
    quotaB2               int not null,
    quotaB3               int not null,
    caso_sociale          int not null,
    riduzione_nuclei      int not null,
    numero_beneficiari    int not null,
    forzatura_contributo  int not null,
    forzatura_congruita   int not null,
    forzatura_blacklist   int,
    forzatura_rei         int,
    forzatura_448         int,
    forzatura_residenza10 int,
    quotaC                int,
    constraint AUP_dati_esclusioni
        primary key (ID_domanda, anno, mese)
            with (fillfactor = 95)
)
go

create index _dta_index_AUP_esclusioni_ufficio_7_1470888557__K1_K2_K4_K5_K6_K7
    on AUP_esclusioni_ufficio (ID_domanda, anno, quotaA, quotaB1, quotaB2, quotaB3)
    with (fillfactor = 95)
go

create table AUP_familiari_residenza
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    ID_residenza     int not null,
    residenza_dal    datetime,
    residenza_al     datetime,
    constraint [PK_[AUP_familiari_residenzaTN]
        primary key (ID_domanda, ID_dichiarazione, ID_residenza)
)
go

create table AUP_familiari_residenza_nuovinati
(
    ID_domanda        int         not null,
    codice_fiscale    nchar(16)   not null,
    nucleo_dal        datetime,
    nucleo_al         datetime,
    nome              varchar(35) not null,
    cognome           varchar(55) not null,
    data_nascita      datetime,
    reddito_inferiore int
)
go

create table AUP_flussi_2s
(
    id_rendicontazione    int identity,
    id_entita             int          not null,
    anno                  int          not null,
    id_blocco_flusso      int,
    codice_fiscale        nvarchar(16) not null,
    idPersona             decimal(12),
    nome                  nvarchar(35),
    cognome               nvarchar(35),
    id_tp_rendicontazione int          not null,
    tariffa               decimal(8, 2),
    quantita              decimal(4),
    dal                   datetime,
    al                    datetime,
    codice_fiscale_flusso nvarchar(16)
)
go

create table AUP_minori_quotaC2
(
    ID_domanda     int          not null,
    codice_fiscale nvarchar(16) not null,
    ID_servizio    int
)
go

create table AUP_patrimonio_finanziario
(
    ID_domanda           int      not null,
    ID                   int      not null,
    ID_tipo_investimento char(4),
    ID_intermediario     nvarchar(10),
    intermediario        nvarchar(150),
    data_valutazione     date,
    consistenza          decimal(12, 2),
    semestre             smallint not null,
    constraint AUP_patrimonio_finanziario_PK
        primary key (ID_domanda, ID, semestre)
)
go

create table AUP_quotaB
(
    id_servizio int,
    tipo        nvarchar(50),
    inf         numeric(18, 5),
    sup         numeric(18, 5),
    min         numeric(18, 5),
    max         numeric(18, 5),
    sca         numeric(18, 5)
)
go

create table AUP_sospensione_importo
(
    ID_domanda        int not null,
    ID_tp_sospensione int not null,
    id_sospensione    int not null,
    sospensione_dal   datetime,
    sospensione_al    datetime,
    constraint PK_AUP_sospensione_importo
        primary key (ID_domanda, ID_tp_sospensione, id_sospensione)
            with (fillfactor = 95)
)
go

create table AUP_sport
(
    id_sport         int not null
        constraint PK_AUP_sport
            primary key,
    id_domanda       int not null,
    id_dichiarazione int not null,
    spesa_sostenuta  numeric(18, 2),
    data_pagamento   datetime,
    distinta         nvarchar(100),
    societa_sportiva nvarchar(300)
)
go

create table AUP_tp_blacklist
(
    id_tp_blacklist    int           not null
        constraint PK_AUP_tp_blacklist
            primary key,
    descrizione        nvarchar(200) not null,
    numero_mesi_blocco smallint      not null
)
go

create table AUP_blacklist
(
    codice_fiscale            nchar(16)     not null,
    data_inizio_blocco        datetime      not null,
    data_fine_blocco          datetime      not null,
    id_tp_blacklist           int           not null
        constraint FK_AUP_blacklist_AUP_tp_blacklist
            references AUP_tp_blacklist,
    id_ente_inseritore        int           not null,
    blocca_solo_domande_apapi smallint      not null,
    annotazioni               nvarchar(200) not null,
    data_inserimento_blocco   datetime,
    constraint PK_AUP_blacklist
        primary key (codice_fiscale, data_inizio_blocco, data_fine_blocco, id_tp_blacklist)
)
go

create table AUP_tp_caso_sociale
(
    caso_sociale int not null
        constraint PK_AUP_tp_caso_sociale
            primary key
                with (fillfactor = 95),
    descrizione  nvarchar(50)
)
go

create table AUP_tp_esclusione_componente
(
    ID_tp_esclusione_componente int           not null,
    ID_servizio                 int           not null,
    descrizione                 nvarchar(100) not null,
    constraint PK_AUP_tp_esclusione_componente
        primary key (ID_tp_esclusione_componente, ID_servizio)
            with (fillfactor = 95)
)
go

create table AUP_tp_esclusione_domanda
(
    ID_tp_esclusione int           not null,
    ID_servizio      int           not null,
    ID_ente          int           not null,
    tipo_esclusione  nvarchar(200) not null,
    constraint PK_AUP_tp_esclusione_domanda
        primary key (ID_tp_esclusione, ID_servizio)
            with (fillfactor = 95)
)
go

create table AUP_tp_relazioni
(
    ID_tp_relazione     int not null,
    ID_servizio         int not null,
    relazione_parentela nvarchar(50),
    constraint PK_AUP_tp_relazioni
        primary key (ID_tp_relazione, ID_servizio)
            with (fillfactor = 95)
)
go

create table AUP_tp_rendicontazione
(
    id_tp_rendicontazione int not null
        constraint PK_AUP_tp_rendicontazione
            primary key
                with (fillfactor = 95),
    tp_rendicontazione    nvarchar(100)
)
go

create table AUP_rendicontazione
(
    codice_fiscale        nchar(16) not null,
    anno                  int       not null,
    mese                  int       not null,
    id_tp_rendicontazione int       not null
        constraint FK_AUP_rendicontazione_AUP_tp_rendicontazione
            references AUP_tp_rendicontazione,
    importo               decimal(18, 2),
    quantita              decimal(18, 2),
    id_blocco_flusso      int,
    constraint PK_AUP_rendicontazione
        primary key (codice_fiscale, anno, mese, id_tp_rendicontazione)
            with (fillfactor = 95)
)
go

create index idx_auprendi_cf_anno
    on AUP_rendicontazione (codice_fiscale, anno)
    with (fillfactor = 95)
go

create index _dta_index_AUP_rendicontazione_7_446884909__K4_K2_3
    on AUP_rendicontazione (id_tp_rendicontazione, anno) include (mese)
    with (fillfactor = 95)
go

create index _dta_index_AUP_rendicontazione_7_446884909__K4_K2_K3_K1_6
    on AUP_rendicontazione (id_tp_rendicontazione, anno, mese, codice_fiscale) include (quantita)
    with (fillfactor = 95)
go

create table AUP_tp_situazioni_lavorative
(
    ID_tp_situazione_lavorativa int          not null,
    ID_servizio                 int          not null,
    lavoro                      nvarchar(50) not null,
    constraint PK_AUP_tp_situazioni_lavorative
        primary key (ID_tp_situazione_lavorativa, ID_servizio)
            with (fillfactor = 95)
)
go

create table AUP_tp_stati_upload
(
    ID_tp_stato_upload smallint     not null
        constraint PK_AUP_tp_stati_upload
            primary key
                with (fillfactor = 95),
    tp_stato_upload    nvarchar(50) not null
)
go

create table Activator_Superservizi
(
    ID_superservizio int            not null
        constraint PK_Activator_Superservizi
            primary key
                with (fillfactor = 95),
    descrizione      nvarchar(50)   not null,
    titolo           nvarchar(50)   not null,
    intestazione     nvarchar(50)   not null,
    anno_copyright   int            not null,
    params           nvarchar(2000) not null,
    versione         datetime       not null,
    credits          nvarchar(2000) not null,
    header           nvarchar(2000) not null
)
go

create table Activator_News
(
    ID_news            int          not null
        constraint PK_Activator_news
            primary key
                with (fillfactor = 90),
    ID_superservizio   int          not null
        constraint FK_Activator_News_Activator_Superservizi
            references Activator_Superservizi,
    posizione          int          not null,
    intestazione       nvarchar(10) not null,
    testo              nvarchar(50) not null,
    link               nvarchar(80) not null,
    data_fine_validita datetime     not null
)
go

create table Activator_Pulsanti
(
    ID_superservizio int           not null
        constraint FK_Activator_Pulsanti_Activator_Superservizi
            references Activator_Superservizi,
    posizione        int           not null,
    testo            nvarchar(50)  not null,
    link             nvarchar(100) not null,
    come             nvarchar(50)  not null,
    winOpen          nvarchar(500) not null,
    constraint PK_Activator_Pulsanti
        primary key (ID_superservizio, posizione)
            with (fillfactor = 90)
)
go

create table Activator_Warning
(
    ID_superservizio int            not null
        constraint FK_Activator_Warning_Activator_Superservizi
            references Activator_Superservizi,
    ID_warning       int            not null,
    warning          nvarchar(2000) not null,
    constraint PK_Activator_Warning
        primary key (ID_superservizio, ID_warning)
            with (fillfactor = 95)
)
go

create table Assegni_tp_eventi
(
    id_tp_evento int          not null
        constraint PK_Assegni_tp_eventi
            primary key
                with (fillfactor = 95),
    tp_evento    nvarchar(50) not null
)
go

create table Assegni_tp_titolo
(
    id_tp_titolo int           not null
        constraint PK_Assegni_tp_titolo
            primary key
                with (fillfactor = 95),
    tp_titolo    nvarchar(100) not null
)
go

create table Avvisi
(
    ID_avviso       int not null
        constraint PK_Avvisi
            primary key
                with (fillfactor = 95),
    data_avviso     datetime,
    titolo          nvarchar(200),
    avviso          nvarchar(4000),
    role_class      nvarchar(255),
    role_parameters nvarchar(max),
    enabled         bit,
    data_scadenza   datetime
)
go

create table Avvisi_documenti
(
    ID_avviso    int not null
        constraint FK_Avvisi_documenti_Avvisi
            references Avvisi,
    ID_documento int not null,
    nome_file    nvarchar(200),
    data_upload  datetime,
    file_content text,
    content_type nvarchar(100),
    constraint PK_Avvisi_documenti
        primary key (ID_avviso, ID_documento)
            with (fillfactor = 95)
)
go

create table Azioni
(
    ID_azione int not null
        constraint PK_Azioni
            primary key nonclustered
                with (fillfactor = 90),
    azione    nvarchar(250)
)
go

create table BAR_tp_interventi
(
    ID_tp_intervento int          not null
        constraint PK_BAR_tp_interventi
            primary key
                with (fillfactor = 95),
    tp_intervento    nvarchar(50) not null
)
go

create table BAR_tp_invalidita
(
    ID_tp_invalidita int not null
        constraint PK_BAR_tp_invalidita
            primary key
                with (fillfactor = 95),
    tp_invalidita    nvarchar(150),
    coeff_invalidita decimal(10, 2)
)
go

create table BAR_tp_relazione
(
    ID_tp_relazione smallint not null
        constraint PK_BAR_tp_relazione
            primary key
                with (fillfactor = 95),
    tp_relazione    nvarchar(250)
)
go

create table BAR_tp_titoli_giuridici
(
    ID_tp_titolo_giuridico int,
    tp_titolo_giuridico    nvarchar(50)
)
go

create table BIM
(
    ID_BIM int           not null
        constraint PK_BIM
            primary key
                with (fillfactor = 95),
    BIM    nvarchar(200) not null
)
go

create table BONUS_Domanda
(
    ID_Domanda                      int          not null,
    ID_servizio                     int          not null,
    codice_fiscale                  nvarchar(16) not null,
    json_richiesta                  nvarchar(max),
    json_risposta                   nvarchar(max),
    data_ricezione_domanda          datetime,
    importo                         decimal(12, 2),
    iban                            nvarchar(50),
    nome_richiedente                nvarchar(35),
    cognome_richiedente             nvarchar(55),
    ID_domanda_stanza               nvarchar(50) not null,
    cf_richiedente                  nvarchar(16),
    ID_luogo_nascita_richiedente    nvarchar(6),
    data_nascita_richiedente        datetime,
    sesso_richiedente               nvarchar,
    ID_luogo_residenza_richiedente  nvarchar(6),
    indirizzo_residenza_richiedente nvarchar(50),
    n_civ_residenza                 nvarchar(10),
    telefono_richiedente            nvarchar(20),
    email_richiedente               nvarchar(50),
    ID_protocollo                   int,
    num_protocollo                  nvarchar(50),
    data_protocollo                 datetime,
    ID_fascicolo                    nvarchar(20),
    data_creazione_sistema          datetime,
    reddito_lordo_complessivo       decimal(12, 2),
    constraint BONUS_Domanda_PK
        primary key (ID_domanda_stanza, codice_fiscale)
)
go

create table BONUS_motivi_esclusione
(
    ID_Domanda       int not null,
    ID_servizio      int not null,
    ID_tp_esclusione int not null,
    constraint BONUS_motivi_esclusione_PK
        primary key (ID_Domanda, ID_tp_esclusione)
)
go

create table BS_minori
(
    ID_domanda   int      not null,
    ID_soggetto  int      not null,
    minore       smallint not null,
    non_invalido smallint not null,
    constraint PK_BUONI_minori
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table CAT_fabbricati_temp
(
    ID_immobile          int          not null,
    ID_titolarita        int          not null,
    codice_fiscale       nvarchar(16) not null,
    anno                 smallint     not null,
    ID_soggetto_catasto  int          not null,
    cod_catastale        nvarchar(4)  not null,
    categoria            nvarchar(3),
    rendita              decimal(12, 2),
    quota_num            numeric,
    quota_deno           numeric,
    cognome              nvarchar(50),
    nome                 nvarchar(50),
    ID_provincia_nascita nvarchar(2),
    ID_luogo_nascita     nvarchar(6),
    data_nascita         datetime,
    ID_tp_sex            nvarchar,
    ID_provincia_fabb    nvarchar(2),
    ID_luogo_fabb        nvarchar(6),
    ind_fabb             nvarchar(100),
    civico_fabb          nvarchar(100),
    numero_ped           nchar(10)    not null,
    subalterno           smallint,
    ID_comune_catastale  nvarchar(4)  not null,
    foglio               nvarchar(4),
    PM                   nvarchar(50),
    codice_diritto       nvarchar(3),
    numero               nchar(10),
    denominatore         nchar(10),
    consistenza          nvarchar(7),
    superficie           nvarchar(5),
    primary key (ID_immobile, ID_titolarita, codice_fiscale, anno)
)
go

create table CAT_fabbricati_tmp_fab
(
    anno                smallint    not null,
    cod_catastale       nvarchar(4) not null,
    ID_immobile         int         not null,
    categoria           nvarchar(3),
    rendita             decimal(12, 2),
    ind_fabb            nvarchar(100),
    civico_fabb         nvarchar(100),
    subalterno          smallint,
    ID_comune_catastale nvarchar(4),
    foglio              nvarchar(4),
    numero              nchar(10),
    denominatore        nchar(10),
    consistenza         nvarchar(7),
    superficie          nvarchar(5),
    constraint PK__CAT_fabb__8879B14CDAB9368C
        primary key (anno, cod_catastale, ID_immobile)
)
go

create table CAT_fabbricati_tmp_sog
(
    anno                smallint     not null,
    cod_catastale       nvarchar(4)  not null,
    ID_soggetto_catasto int          not null,
    codice_fiscale      nvarchar(16) not null,
    cognome             nvarchar(50),
    nome                nvarchar(50),
    data_nascita        datetime,
    constraint PK__CAT_fabb__2564E5876040FFE2
        primary key (anno, cod_catastale, ID_soggetto_catasto)
)
go

create table CAT_fabbricati_tmp_tit
(
    anno                smallint    not null,
    cod_catastale       nvarchar(4) not null,
    ID_titolarita       int         not null,
    ID_immobile         int         not null,
    ID_soggetto_catasto int         not null,
    quota_num           numeric,
    quota_deno          numeric,
    numero_ped          nchar(10),
    PM                  nvarchar(50),
    codice_diritto      nvarchar(3),
    constraint PK__CAT_fabb__A3804B960FA08F1F
        primary key (anno, cod_catastale, ID_titolarita, ID_immobile, ID_soggetto_catasto)
)
go

create table COMP_Collegamento_Enti
(
    ID_UVM            int not null,
    ID_comunita       int not null,
    ID_gruppo_calcolo int not null,
    constraint PK_COMP_Collegamento_Enti
        primary key (ID_UVM, ID_comunita)
            with (fillfactor = 95)
)
go

create table COMP_Elaborazioni
(
    cf_utente               nvarchar(16) not null,
    anno                    smallint     not null,
    mese                    smallint     not null,
    ID_tp_intervento        nvarchar(15) not null,
    ID_ente_comp            int          not null,
    ID_domanda_ICEF         int,
    cf_destinatario_fattura nvarchar(16),
    quantita                numeric(10, 2),
    importo_unitario        numeric(10, 2),
    doc_edizione            int,
    storno                  numeric(10, 2),
    tetto_max_familiare     numeric(10, 2),
    ICEF                    numeric(10, 6),
    tipo_variazione         nvarchar(2),
    ID_comunicazione_f2     int,
    ID_elaborazione         int,
    data_inizio             datetime     not null,
    constraint PK_COMP_elaborazioni
        primary key (cf_utente, anno, mese, ID_tp_intervento, data_inizio, ID_ente_comp)
            with (fillfactor = 95)
)
go

create table COMP_Enti
(
    ID_ente_comp      int           not null,
    ente              nvarchar(255) not null,
    ID_tp_ente_comp   smallint      not null,
    ID_gruppo_calcolo int           not null
)
go

create table COMP_Monitor
(
    ID_ente_comp        int      not null,
    anno                int      not null,
    mese                int      not null,
    id_tp_stato         smallint not null,
    data_upload         datetime,
    data_elaborazione   datetime,
    data_download       datetime,
    data_conferma       datetime,
    message             text,
    message_clesius     text,
    ID_comunicazione_f3 int,
    constraint PK_COMP_Monitor
        primary key (ID_ente_comp, anno, mese)
            with (fillfactor = 95)
)
go

create table COMP_ODONTO_Elaborazioni
(
    cf_utente           nvarchar(16) not null,
    anno                smallint     not null,
    mese                smallint     not null,
    ID_ente_comp        int          not null,
    codice_movimento    nvarchar(35) not null,
    importo_ICEF        numeric(10, 2),
    ID_domanda_ICEF     int,
    doc_edizione        int,
    data_edizione       datetime,
    ICEF                numeric(10, 6),
    tipo_variazione     nvarchar(2),
    delta               numeric(10, 2),
    ID_comunicazione_f2 int,
    ID_elaborazione     int,
    codice_istat        nvarchar(6),
    primary key (cf_utente, anno, mese, ID_ente_comp, codice_movimento)
)
go

create table COMP_ODONTO_Elaborazioni_scaricate
(
    ID_ente         int      not null,
    anno            int      not null,
    mese            int      not null,
    data_invio      datetime not null,
    id_elaborazione int      not null,
    constraint COMP_ODONTO_Elaborazioni_scaricate_PK
        primary key (ID_ente, anno, mese, id_elaborazione)
)
go

create table COMP_ODONTO_Rendicontazione
(
    codice_movimento            nvarchar(35) not null,
    anno                        smallint     not null,
    mese                        smallint     not null,
    ID_ente_comp                int          not null,
    codice_prestazione          nvarchar(15),
    data_inizio                 datetime     not null,
    cf_utente                   nvarchar(16) not null,
    ID_soggetto_APSS            nvarchar(50),
    modalita                    nvarchar(30),
    importo_prestazione         numeric(10, 2),
    quantita                    numeric(10, 2),
    importo_calcolato_APSS      numeric(10, 2),
    su_base_ICEF                nvarchar,
    ICEF                        float,
    ID_domanda_ICEF             int,
    edizione                    int,
    ID_comunicazione_f2         int,
    codice_movimento_originario nvarchar(35),
    codice_istat                nvarchar(6),
    constraint PK_COMP_ODONTO_rendicontazione
        primary key (anno, mese, ID_ente_comp, codice_movimento)
)
go

create table COMP_ODONTO_WS_Comunicazioni
(
    ID_Comunicazione int identity
        constraint PK_COMP_ODONTO_WS_Comunicazioni
            primary key,
    data             datetime,
    operazione       smallint,
    request          xml,
    response         xml,
    provenienza      smallint,
    ID_ente_comp     smallint,
    data_upload      datetime,
    contentType      nvarchar(255)
)
go

create table COMP_POLEQ_quote_temp
(
    ID_domanda       int            not null,
    ID_tp_intervento nvarchar(15)   not null,
    quota            numeric(12, 2) not null,
    primary key (ID_domanda, ID_tp_intervento)
)
go

create table COMP_POLEQ_temp
(
    ID_domanda      int            not null
        primary key,
    tetto_max       numeric(12, 2) not null,
    data_decorrenza datetime
)
go

create table COMP_Params
(
    ID_ente_comp int            not null,
    anno         smallint       not null,
    mese         smallint       not null,
    tipo         nvarchar(20)   not null,
    valore       numeric(12, 2) not null,
    constraint PK_COMP_Params
        primary key (ID_ente_comp, anno, mese, tipo)
            with (fillfactor = 95)
)
go

create table COMP_R_Anni
(
    anno_rendicontazione int not null,
    anno_max_redditi     int not null,
    constraint PK_COMP_R_Anni
        primary key (anno_rendicontazione, anno_max_redditi)
            with (fillfactor = 95)
)
go

create table COMP_Soggetti_errati
(
    codice_errato    varchar(50),
    codice_corretto  varchar(50),
    cognome          varchar(50),
    nome             varchar(50),
    sesso            varchar(50),
    data_nascita     varchar(50),
    luogo_nascita    varchar(250),
    belfiore_nascita varchar(50),
    processato       int
)
go

create table COMP_WS_Abilitazioni
(
    ID_flusso smallint      not null
        constraint PK_COMP_WS_Abilitazioni
            primary key,
    nome      nvarchar(255) not null,
    enabled   bit           not null
)
go

create table COMP_WS_Comunicazioni
(
    ID_Comunicazione int identity
        constraint PK_COMP_WS_Comunicazioni
            primary key,
    data             datetime,
    operazione       smallint,
    request          xml,
    response         xml,
    anno             smallint,
    mese             smallint,
    provenienza      smallint,
    ID_ente_comp     smallint,
    data_upload      datetime,
    contentType      nvarchar(255)
)
go

create table COMP_domande_flusso_1
(
    ID_domanda int not null
        constraint PK_COMP_domande_flusso1
            primary key
                with (fillfactor = 95)
)
go

create table COMP_domande_soggetto
(
    ID_soggetto  int not null,
    ID_domanda   int not null,
    edizione_doc int not null,
    constraint PK_COMP_domande_soggetto
        primary key (ID_soggetto, ID_domanda)
            with (fillfactor = 95)
)
go

create table COMP_file_manuali
(
    ID_ente_header int not null,
    anno           int not null,
    mese           int not null,
    constraint PK_COMP_file_manuali
        primary key (ID_ente_header, anno, mese)
            with (fillfactor = 95)
)
go

create table COMP_nuclei
(
    ID_gruppo_calcolo int          not null,
    ID_famiglia       int          not null,
    anno              smallint     not null,
    mese              smallint     not null,
    ID_soggetto       int          not null,
    codice_fiscale    nvarchar(16) not null,
    ID_domanda_comp   int,
    icef              numeric(10, 6),
    ID_domanda_poleq  int,
    constraint PK_COMP_nuclei_1
        primary key (anno, mese, ID_gruppo_calcolo, ID_soggetto, ID_famiglia, codice_fiscale)
            with (fillfactor = 95)
)
go

create table COMP_nuclei_anomalie
(
    ID_gruppo_calcolo    int          not null,
    anno                 smallint     not null,
    mese                 smallint     not null,
    ID_soggetto          int          not null,
    codice_fiscale       nvarchar(16) not null,
    ID_famiglia_anomalia int          not null,
    constraint PK_COMP_anomalie_domande
        primary key (ID_gruppo_calcolo, anno, mese, ID_soggetto, codice_fiscale, ID_famiglia_anomalia)
            with (fillfactor = 95)
)
go

create table COMP_nuclei_domande
(
    ID_gruppo_calcolo int      not null,
    anno              smallint not null,
    mese              smallint not null,
    ID_famiglia       int      not null,
    ID_domanda        int      not null,
    constraint PK_COMP_nuclei_domande_1
        primary key (ID_famiglia, ID_domanda, anno, mese, ID_gruppo_calcolo)
)
go

create table COMP_nuclei_tetti
(
    ID_gruppo_calcolo int      not null,
    ID_famiglia       int      not null,
    anno              smallint not null,
    mese              smallint not null,
    tetto_icef        numeric(16, 2),
    tetto_poleq       numeric(16, 2),
    constraint PK_COMP_nuclei_tetti
        primary key (ID_gruppo_calcolo, ID_famiglia, anno, mese)
            with (fillfactor = 95)
)
go

create table COMP_pre_elaborazioni
(
    cf_utente               nvarchar(16) not null,
    anno                    smallint     not null,
    mese                    smallint     not null,
    ID_tp_intervento        nvarchar(15) not null,
    ID_ente_comp            int          not null,
    ID_gruppo_calcolo       int          not null,
    ID_famiglia             int          not null,
    ID_domanda_ICEF         int,
    ID_soggetto_APSS        nvarchar(50),
    cf_destinatario_fattura nvarchar(16),
    quantita                numeric(10, 2),
    quantita_calcolata      numeric(10, 2),
    importo_unitario        numeric(10, 2),
    storno                  numeric(10, 2),
    tetto_max_intervento    numeric(10, 2),
    elab_mode               nvarchar(50),
    fatturazione            nvarchar(5),
    ICEF                    numeric(10, 6),
    ID_comunicazione_f2     int,
    data_inizio             datetime     not null,
    constraint PK_COMP_pre_elaborazioni
        primary key (cf_utente, anno, mese, ID_tp_intervento, data_inizio, ID_ente_comp, ID_gruppo_calcolo, ID_famiglia)
            with (fillfactor = 95)
)
go

create table COMP_tp_interventi
(
    ID_tp_intervento nvarchar(15) not null,
    tp_intervento    nvarchar(250),
    unita_misura     nvarchar(50),
    regole           nvarchar(5),
    invio_quantita   nvarchar(50),
    invio_importi    nvarchar(50),
    elab_mode        nvarchar(50),
    fatturazione     nvarchar(5),
    tp_quota_ICEF    nvarchar(10),
    tetto_max        nvarchar(50),
    quota_manuale    int,
    note             nvarchar(250),
    data_inizio      datetime     not null,
    data_fine        datetime     not null,
    constraint PK_Comp_tp_interventi
        primary key (ID_tp_intervento, data_inizio)
            with (fillfactor = 95)
)
go

create table COMP_Elaborazioni_storico
(
    ID_elaborazione          int          not null,
    cf_utente                nvarchar(16) not null,
    anno                     smallint     not null,
    mese                     smallint     not null,
    ID_tp_intervento         nvarchar(15) not null,
    ID_ente_comp             int          not null,
    ID_domanda_ICEF          int,
    doc_edizione             int,
    cf_destinatario_fattura  nvarchar(16),
    quantita                 numeric(10, 2),
    importo_unitario         numeric(10, 2),
    storno                   numeric(10, 2),
    tetto_max_familiare      numeric(10, 2),
    ICEF                     numeric(10, 6),
    tipo_variazione          nvarchar(2),
    delta                    numeric(10, 2),
    ID_comunicazione_f2      int          not null,
    ID_comunicazione_f3      int,
    ID_comunicazione_f4      int,
    quantita_prec            numeric(10, 2),
    importo_unitario_prec    numeric(10, 2),
    storno_prec              numeric(10, 2),
    ID_comunicazione_f2_prec int,
    ID_comunicazione_f3_prec int,
    data_inizio              datetime     not null,
    constraint PK_COMP_Elaborazioni_Storico
        primary key (ID_elaborazione, cf_utente, anno, mese, ID_tp_intervento, data_inizio, ID_ente_comp)
            with (fillfactor = 95),
    constraint FK_COMP_Elaborazioni_storico_COMP_tp_interventi
        foreign key (ID_tp_intervento, data_inizio) references COMP_tp_interventi
)
go

create table COMP_Rendicontazione
(
    cf_utente               nvarchar(16) not null,
    anno                    smallint     not null,
    mese                    smallint     not null,
    ID_tp_intervento        nvarchar(15) not null,
    ID_ente_comp            int          not null,
    ID_domanda_ICEF         int,
    ID_soggetto_APSS        nvarchar(50),
    cf_destinatario_fattura nvarchar(16),
    quantita                numeric(10, 2),
    importo_unitario        numeric(10, 2),
    tetto_max               numeric(10, 2),
    ID_ente_header          int          not null,
    ID_comunicazione_f2     int,
    data_inizio             datetime     not null,
    constraint PK_COMP_rendicontazione
        primary key (cf_utente, anno, mese, ID_tp_intervento, ID_ente_comp, ID_ente_header),
    constraint FK_COMP_rendicontazione_Comp_tp_interventi
        foreign key (ID_tp_intervento, data_inizio) references COMP_tp_interventi
)
go

create table CTRL_2014_backup_c_elain_estratti
(
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_CTRL_2014_backup_c_elain_estratti
        primary key (ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create table CTRL_2014_backup_c_elaout_estratti
(
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    numeric_value float,
    constraint PK_CTRL_2014_backup_c_elaout_estratti
        primary key (ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create table CTRL_2014_backup_stato_controllo_pre
(
    id_dichiarazione      int not null
        constraint PK_CTRL_2014_backup_stato_controllo_pre
            primary key
                with (fillfactor = 95),
    id_tp_stato_controllo int not null
)
go

create table CTRL_2014_benefici
(
    ID_servizio int          not null,
    tabella     nvarchar(50) not null,
    nodo        nvarchar(50) not null,
    condizione  nvarchar(50) not null,
    percentuale int          not null
)
go

create table CTRL_2014_confronto_C
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C11
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C11
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C11P
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C11P
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C12
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C12
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C13
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C13
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C31
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C31
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C32
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C32
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C4
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_C4
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_C5
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    importo          decimal(12, 2) not null,
    ID_tp_erogazione char(3)        not null,
    tp_erogazione    nvarchar(300),
    constraint PK_CTRL_2014_confronto_C5
        primary key (anno, codice_fiscale, ID_tp_erogazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_confronto_F
(
    anno           int            not null,
    codice_fiscale nvarchar(16)   not null,
    importo        decimal(12, 2) not null,
    constraint PK_CTRL_2014_confronto_F
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C11
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C11
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C11P
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C11P
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C12
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C12
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C13
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C13
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C31
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C31
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C32
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C32
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C4
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    constraint PK_CTRL_2014_dichiarato_C4
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_C5
(
    anno             int            not null,
    codice_fiscale   nvarchar(16)   not null,
    ID_dichiarazione int            not null,
    importo          decimal(12, 2) not null,
    ID_tp_erogazione char(3)        not null,
    tp_erogazione    varchar(300),
    constraint PK_CTRL_2014_dichiarato_C5
        primary key (anno, codice_fiscale, ID_dichiarazione, ID_tp_erogazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarato_F
(
    anno                       int            not null,
    codice_fiscale             nvarchar(16)   not null,
    ID_dichiarazione           int            not null,
    importo                    decimal(12, 2) not null,
    possibili_beni_strumentali smallint       not null,
    constraint PK_CTRL_2014_dichiarato_F
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarazioni_blacklist
(
    ID_dichiarazione int not null
        constraint PK_CTRL_2014_dichiarazioni_blacklist
            primary key
                with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarazioni_da_estrarre
(
    ID_dichiarazione int not null
        constraint PK_CTRL_2014_dichiarazioni_da_estrarre
            primary key
                with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarazioni_domande
(
    ID_dichiarazione int not null,
    ID_domanda       int not null,
    constraint PK_CTRL_2014_dichiarazioni_domande
        primary key (ID_dichiarazione, ID_domanda)
            with (fillfactor = 95)
)
go

create table CTRL_2014_dichiarazioni_gia_in_controllo
(
    id_dichiarazione        int not null
        constraint PK_CTRL_2014_dichiarazioni_gia_in_controllo
            primary key
                with (fillfactor = 95),
    id_tp_stato_controlloBu int not null
)
go

create table CTRL_2014_dichiarazioni_in_fase_sblocco
(
    id_dichiarazione int not null
        constraint PK_CTRL_2014_dichiarazioni_in_fase_sblocco
            primary key
                with (fillfactor = 95)
)
go

create table CTRL_2014_domande
(
    ID_domanda   int      not null
        constraint PK_CTRL_2014_domande
            primary key
                with (fillfactor = 95),
    ID_servizio  int      not null,
    ha_beneficio smallint not null
)
go

create table CTRL_2014_estratti
(
    anno             int          not null,
    codice_fiscale   nvarchar(16) not null,
    ID_dichiarazione int          not null,
    constraint PK_CTRL_2014_estratti
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_risultato_comparazione
(
    anno                       int          not null,
    codice_fiscale             nvarchar(16) not null,
    ID_dichiarazione           int          not null,
    C                          smallint,
    importo_dichiarato_C       decimal(12, 2),
    importo_confronto_C        decimal(12, 2),
    C11                        smallint,
    importo_dichiarato_C11     decimal(12, 2),
    importo_confronto_C11      decimal(12, 2),
    C11P                       smallint,
    importo_dichiarato_C11P    decimal(12, 2),
    importo_confronto_C11P     decimal(12, 2),
    C12                        smallint,
    importo_dichiarato_C12     decimal(12, 2),
    importo_confronto_C12      decimal(12, 2),
    C13                        smallint,
    importo_dichiarato_C13     decimal(12, 2),
    importo_confronto_C13      decimal(12, 2),
    C31                        smallint,
    importo_dichiarato_C31     decimal(12, 2),
    importo_confronto_C31      decimal(12, 2),
    C32                        smallint,
    importo_dichiarato_C32     decimal(12, 2),
    importo_confronto_C32      decimal(12, 2),
    C4                         smallint,
    importo_dichiarato_C4      decimal(12, 2),
    importo_confronto_C4       decimal(12, 2),
    C5                         smallint,
    importo_dichiarato_C5      decimal(12, 2),
    importo_confronto_C5       decimal(12, 2),
    F                          smallint,
    importo_dichiarato_F       decimal(12, 2),
    importo_confronto_F        decimal(12, 2),
    possibili_beni_strumentali smallint,
    constraint PK_CTRL_2014_risultato_comparazione
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_totale_dichiarazioni
(
    anno             int          not null,
    codice_fiscale   nvarchar(16) not null,
    ID_dichiarazione int          not null,
    constraint PK_CTRL_2014_totale_dichiarazioni
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_2014_totale_dichiarazioni_senza_cf_doppi
(
    anno             int          not null,
    codice_fiscale   nvarchar(16) not null,
    ID_dichiarazione int          not null
        constraint PK_CTRL_2014_totale_dichiarazioni_senza_cf_doppi
            primary key
                with (fillfactor = 95)
)
go

create table CTRL_risultato_comparazione
(
    anno                       int          not null,
    codice_fiscale             nvarchar(16) not null,
    ID_dichiarazione           int          not null,
    C                          smallint,
    importo_dichiarato_C       decimal(12, 2),
    importo_confronto_C        decimal(12, 2),
    C5                         smallint,
    importo_dichiarato_C5      decimal(12, 2),
    importo_confronto_C5       decimal(12, 2),
    F                          smallint,
    importo_dichiarato_F       decimal(12, 2),
    importo_confronto_F        decimal(12, 2),
    possibili_beni_strumentali smallint,
    C11                        smallint,
    importo_dichiarato_C11     decimal(12, 2),
    importo_confronto_C11      decimal(12, 2),
    C11P                       smallint,
    importo_dichiarato_C11P    decimal(12, 2),
    importo_confronto_C11P     decimal(12, 2),
    C12                        smallint,
    importo_dichiarato_C12     decimal(12, 2),
    importo_confronto_C12      decimal(12, 2),
    C13                        smallint,
    importo_dichiarato_C13     decimal(12, 2),
    importo_confronto_C13      decimal(12, 2),
    C31                        smallint,
    importo_dichiarato_C31     decimal(12, 2),
    importo_confronto_C31      decimal(12, 2),
    C32                        smallint,
    importo_dichiarato_C32     decimal(12, 2),
    importo_confronto_C32      decimal(12, 2),
    C4                         smallint,
    importo_dichiarato_C4      decimal(12, 2),
    importo_confronto_C4       decimal(12, 2),
    constraint PK_CTRL_2008_risultato_comparazione
        primary key (anno, codice_fiscale, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table CTRL_statistiche_finali_nucleo_controllo
(
    anno                  int not null,
    ID_ente_erogatore     int not null,
    estratte              int not null,
    anomale_automatizzato int not null,
    anomale_controllo     int not null,
    constraint PK_CTRL_statistiche_finali_nucleo_controllo
        primary key (anno, ID_ente_erogatore)
            with (fillfactor = 95)
)
go

create table CTRL_tp_altri_enti_segnalatori
(
    ID_tp_altro_ente_segnalatore int           not null
        constraint PK_CTRL_tp_altri_enti_segnalatori
            primary key,
    descrizione                  nvarchar(200) not null,
    ID_tp_ente_segnalatore       int           not null
)
go

create table CTRL_tp_descrizione_errore_quadro
(
    ID_tp_descrizione_errore_quadro int           not null
        constraint PK_CTRL_2008_tp_descrizione_errore_quadro
            primary key
                with (fillfactor = 95),
    descrizione                     nvarchar(200) not null
)
go

create table CTRL_tp_tipologia_errore_quadro
(
    ID_tp_tipologia_errore_quadro int           not null
        constraint PK_CTRL_2008_tp_tipologia_errore_quadro
            primary key
                with (fillfactor = 95),
    descrizione                   nvarchar(200) not null
)
go

create table CUD_tp_anni
(
    anno        int      not null,
    ID_servizio int      not null,
    enabled     smallint not null
)
go

create table C_ElaOUT_benefici
(
    ID_domanda    int         not null,
    node          varchar(50) not null,
    numeric_value float,
    constraint PK_C_ElaOUT_benefici
        primary key (ID_domanda, node)
            with (fillfactor = 95)
)
go

create table C_GlobalParams
(
    ID_param int not null
        constraint PK_C_GlobalParams
            primary key nonclustered
                with (fillfactor = 95),
    param    nvarchar(50),
    value    nvarchar(50)
)
go

create unique clustered index pk_id_params_globalparams
    on C_GlobalParams (ID_param)
    with (fillfactor = 95)
go

create table C_NodIN
(
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    class       nvarchar(60),
    descrizione nvarchar(200),
    tp_node     smallint     not null,
    tp_value    char(15),
    SQL_query   nvarchar(200),
    stat        smallint     not null,
    stat_alias  nvarchar(50),
    constraint PK_C_NodIN
        primary key nonclustered (net, node)
            with (fillfactor = 95)
)
go

create table C_NodOUT
(
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    class       nvarchar(50),
    descrizione nvarchar(200),
    stat        smallint     not null,
    stat_alias  nvarchar(50),
    constraint PK_C_NodOUT
        primary key nonclustered (net, node)
            with (fillfactor = 90)
)
go

create table CheckIn
(
    busy nvarchar(50) not null
        constraint PK_CheckIn
            primary key nonclustered
                with (fillfactor = 95)
)
go

create table Check_C_DefaultIn
(
    ID_servizio   int          not null,
    ID_periodo    int          not null,
    ID_ente       int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    default_value nvarchar(50) not null,
    modificabile  smallint,
    constraint PK_Check_C_DefaultIn
        primary key (ID_servizio, ID_periodo, ID_ente, net, node)
            with (fillfactor = 95)
)
go

create table Check_C_ElaIN
(
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_Check_C_ElaIN
        primary key nonclustered (ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create table Check_C_ElaOUT
(
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    constraint PK_Check_C_ElaOUT
        primary key (ID_domanda, net, node)
)
go

create table Check_C_Elab
(
    id_domanda  int          not null,
    net         nvarchar(50) not null,
    elab_date   smalldatetime,
    fingerprint nvarchar(50),
    checkfp     smallint,
    n_elab      int,
    constraint PK_Check_C_Elab
        primary key nonclustered (id_domanda, net)
            with (fillfactor = 95)
)
go

create table Check_C_NodIN
(
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    class       nvarchar(50),
    descrizione nvarchar(200),
    tp_node     smallint     not null,
    tp_value    char(15),
    SQL_query   nvarchar(200),
    stat        smallint     not null,
    constraint PK_Check_C_NodIN
        primary key nonclustered (net, node)
            with (fillfactor = 95)
)
go

create table Check_C_NodOUT
(
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    class       nvarchar(50),
    descrizione nvarchar(200),
    stat        smallint     not null,
    constraint PK_Check_C_NodOUT
        primary key nonclustered (net, node)
            with (fillfactor = 90)
)
go

create table CollegamentiCambiati
(
    ID_tipo       int      not null,
    ID_principale int      not null,
    ID_dipendente int      not null,
    data          datetime not null,
    constraint PK_CollegamentiCambiati
        primary key (ID_principale, data)
            with (fillfactor = 95)
)
go

create table DATAESCLUSIONI
(
    ID_DOMANDA           int      not null,
    F_VALUE              float,
    RICHIEDE_ANF         smallint not null,
    RICHIEDE_FNUM        smallint not null,
    ESCLUDI_UFFICIO_ANF  smallint not null,
    ESCLUDI_UFFICIO_FNUM smallint not null,
    V_VALUE              float,
    STR_V_VALUE          varchar(12),
    STR_F_VALUE          varchar(12)
)
go

create table DB_component_configuration
(
    id_component          int          not null
        constraint pk_db_component_configuration
            primary key
                with (fillfactor = 95),
    id_parent             int          not null,
    context               nvarchar(50) not null,
    plugin_id             nvarchar(50) not null,
    id_zk                 nvarchar(255),
    plugin_index_url      nvarchar(255),
    plugin_url_parameters text,
    role_priority         int          not null,
    role_class            nvarchar(255),
    role_parameters       nvarchar(max),
    label                 nvarchar(50),
    selected              int,
    icon                  nvarchar(50),
    plugin_singolo        int          not null
)
go

create table DM_AC_variazioni_BONIFICA_EVENTI_NULLI
(
    id_domanda     int,
    id_evento_apss int
)
go

create table DM_AC_variazioni_BONIFICA_EVENTI_NULLI_ESITO
(
    id_variazione  int,
    id_evento_apss int
)
go

create table DM_AC_variazioni_Backup_20130723
(
    ID_variazione    int identity
        constraint PK_AC_variazioni_Backup_1
            primary key
                with (fillfactor = 95),
    ID_domanda       int      not null,
    ID_tp_variazione smallint not null,
    data_inizio      datetime,
    data_fine        datetime,
    data_variazione  datetime,
    valore           numeric(12, 2),
    ID_evento_APSS   bigint
)
go

create table DU_esclusione_mensile
(
    ID_domanda int      not null,
    periodo    smallint not null,
    constraint PK_DU_esclusione_mensile
        primary key (ID_domanda, periodo)
            with (fillfactor = 95)
)
go

create table DU_mensa_2016_2017
(
    codice_fiscale  nvarchar(16) not null,
    saldo           decimal(18, 2),
    id_ente         int          not null,
    [file]          int,
    anno_scolastico nvarchar(50) not null,
    constraint PK_DU_mensa_2016_2017
        primary key (codice_fiscale, id_ente, anno_scolastico)
            with (fillfactor = 95)
)
go

create table DU_tp_residenze
(
    ID_servizio     int      not null,
    ID_tp_residenza smallint not null,
    tipo_residenza  nvarchar(250),
    constraint PK_DU_tp_residenza
        primary key (ID_servizio, ID_tp_residenza)
            with (fillfactor = 95)
)
go

create table DU_tp_variazioni
(
    ID_tp_reddito_attualizzato     int           not null,
    tp_reddito_attualizzato        nvarchar(200) not null,
    ID_servizio                    int           not null,
    tp_reddito_attualizzato_stampa text          not null,
    constraint PK_DU_tp_variazioni
        primary key (ID_tp_reddito_attualizzato, ID_servizio)
            with (fillfactor = 95)
)
go

create table Dichiarazioni
(
    ID_dichiarazione int,
    ID_dich          int
)
go

create table Doc_edizioni_gruppi
(
    id   int           not null
        constraint PK_Doc_edizioni_gruppi
            primary key
                with (fillfactor = 95),
    nome nvarchar(100) not null
)
go

create table Doc_edizioni_predom
(
    id_doc                    int      not null,
    edizione_doc              int      not null,
    data_edizione             datetime not null,
    archiviato_pat            smallint not null,
    id_pat                    int,
    print_backup_file_name    nvarchar(50),
    hashcode                  int,
    data_archiviazione        datetime,
    elab_backup_file_name     nvarchar(50),
    id_user                   int,
    ID_doc_edizioni_tipologia int      not null
)
go

create table Doc_edizioni_progressivo_servizi
(
    id_servizio       int not null
        constraint PK_Doc_edizioni_progressivo_servizi
            primary key
                with (fillfactor = 95),
    servizio          nvarchar(200),
    stampa_protocollo int
)
go

exec sp_addextendedproperty 'MS_Description', '0 in caso di dichiarazione ICEF', 'SCHEMA', 'dbo', 'TABLE',
     'Doc_edizioni_progressivo_servizi', 'COLUMN', 'id_servizio'
go

create table Doc_edizioni_progressivo_tp_acquisizione
(
    id_tp_acquisizione int           not null
        constraint PK_Doc_edizioni_progressivo_tp_acquisizione
            primary key
                with (fillfactor = 70),
    descrizione        nvarchar(500) not null
)
go

create table Doc_edizioni_progressivo_gruppi
(
    id_gruppo          int not null
        constraint PK_Doc_progressivo_gruppi
            primary key
                with (fillfactor = 95),
    descrizione        nvarchar(500),
    id_tp_acquisizione int not null
        constraint FK_Doc_edizioni_progressivo_gruppi_Doc_edizioni_progressivo_tp_acquisizione
            references Doc_edizioni_progressivo_tp_acquisizione
)
go

create table Doc_edizioni_progressivo
(
    id                    int not null
        constraint PK_Doc_edizioni_progressivo
            primary key
                with (fillfactor = 95),
    progressivo_attuale   int,
    progressivo_riservato int,
    id_gruppo             int not null
        constraint FK_Doc_edizioni_progressivo_Doc_edizioni_progressivo_gruppi
            references Doc_edizioni_progressivo_gruppi
)
go

create table Doc_edizioni_progressivo_gruppi_servizi
(
    id_gruppo   int not null
        constraint FK_Doc_edizioni_progressivo_gruppi_servizi_Doc_edizioni_progressivo_gruppi
            references Doc_edizioni_progressivo_gruppi,
    id_servizio int not null
        constraint FK_Doc_edizioni_progressivo_gruppi_servizi_Doc_edizioni_progressivo_servizi
            references Doc_edizioni_progressivo_servizi,
    constraint PK_Doc_edizioni_progressivo_gruppi_servizi
        primary key (id_gruppo, id_servizio)
            with (fillfactor = 95)
)
go

create table Doc_edizioni_stampa_report
(
    id        int not null
        constraint PK_Doc_edizioni_stampa_report
            primary key
                with (fillfactor = 95),
    header    text,
    footer    text,
    pdfHeader nvarchar(200),
    pdfFooter nvarchar(200)
)
go

declare @MS_DefaultView tinyint = 2
exec sp_addextendedproperty 'MS_DefaultView', @MS_DefaultView, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report'
go

declare @MS_OrderByOn bit = '0'
exec sp_addextendedproperty 'MS_OrderByOn', @MS_OrderByOn, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report'
go

declare @MS_Orientation tinyint = 0
exec sp_addextendedproperty 'MS_Orientation', @MS_Orientation, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report'
go

declare @MS_RowHeight smallint = 12480
exec sp_addextendedproperty 'MS_RowHeight', @MS_RowHeight, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report'
go

exec sp_addextendedproperty 'MS_TableMaxRecords', 10000, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'id'
go

declare @MS_ColumnOrder smallint = 1
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'id'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'id'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'header'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'header'
go

declare @MS_ColumnWidth smallint = 6840
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'header'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'footer'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'footer'
go

declare @MS_ColumnWidth smallint = 17805
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'footer'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'pdfHeader'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'pdfHeader'
go

declare @MS_ColumnWidth smallint = 5205
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'pdfHeader'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'pdfFooter'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'pdfFooter'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_stampa_report',
     'COLUMN', 'pdfFooter'
go

create table Doc_edizioni_report
(
    id               int           not null
        constraint PK_Doc_edizioni_report
            primary key
                with (fillfactor = 95),
    nome             nvarchar(100) not null,
    query            text,
    id_stampa_report int
        constraint FK_Doc_edizioni_report_Doc_edizioni_stampa_report
            references Doc_edizioni_stampa_report
)
go

create table Doc_edizioni_report_gruppi
(
    id_report int not null
        constraint FK_Doc_edizioni_report_gruppi_Doc_edizioni_report
            references Doc_edizioni_report,
    id_gruppo int not null
        constraint FK_Doc_edizioni_report_gruppi_Doc_edizioni_gruppi
            references Doc_edizioni_gruppi,
    constraint PK_Doc_edizioni_report_gruppi
        primary key (id_report, id_gruppo)
            with (fillfactor = 95)
)
go

create table Doc_edizioni_tipologia
(
    ID_doc_edizioni_tipologia int           not null
        constraint PK_Doc_edizioni_tipologia
            primary key,
    tipologia                 nvarchar(100) not null
)
go

create table Doc_edizioni_tipologia_parametri
(
    id             int          not null
        constraint PK_Doc_edizioni_tipologia_parametri
            primary key
                with (fillfactor = 95),
    tipologia      nvarchar(50) not null,
    dimensione_max int
)
go

create table Doc_edizioni_parametri
(
    id           int           not null
        constraint PK_Doc_edizioni_parametri
            primary key
                with (fillfactor = 95),
    nome         nvarchar(50)  not null,
    descrizione  nvarchar(100) not null,
    id_tipologia int           not null
        constraint FK_Doc_edizioni_parametri_Doc_edizioni_tipologia_parametri
            references Doc_edizioni_tipologia_parametri,
    valore       nvarchar(100),
    query        text,
    tipo_dato    nvarchar(50)  not null,
    step         int           not null,
    obbligatorio smallint      not null
)
go

declare @MS_DefaultView tinyint = 2
exec sp_addextendedproperty 'MS_DefaultView', @MS_DefaultView, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri'
go

declare @MS_OrderByOn bit = '0'
exec sp_addextendedproperty 'MS_OrderByOn', @MS_OrderByOn, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri'
go

declare @MS_Orientation tinyint = 0
exec sp_addextendedproperty 'MS_Orientation', @MS_Orientation, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri'
go

exec sp_addextendedproperty 'MS_TableMaxRecords', 10000, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'id'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'id'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'id'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'nome'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'nome'
go

declare @MS_ColumnWidth smallint = 1800
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'nome'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'descrizione'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'descrizione'
go

declare @MS_ColumnWidth smallint = 3420
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'descrizione'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'id_tipologia'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'id_tipologia'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'id_tipologia'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'valore'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'valore'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'valore'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'query'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'query'
go

declare @MS_ColumnWidth smallint = 8640
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'query'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'tipo_dato'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'tipo_dato'
go

declare @MS_ColumnWidth smallint = 1575
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'tipo_dato'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'step'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'step'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'step'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'obbligatorio'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'obbligatorio'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'Doc_edizioni_parametri',
     'COLUMN', 'obbligatorio'
go

create table Doc_edizioni_report_parametri
(
    id_parametro int not null
        constraint FK_Doc_edizioni_report_parametri_Doc_edizioni_parametri
            references Doc_edizioni_parametri,
    id_report    int not null
        constraint FK_Doc_edizioni_report_parametri_Doc_edizioni_report
            references Doc_edizioni_report,
    constraint PK_Doc_edizioni_report_parametri
        primary key (id_parametro, id_report)
            with (fillfactor = 95)
)
go

create table Doc_edizioni_utenti
(
    id                int           not null
        constraint PK_Doc_edizioni_utenti
            primary key
                with (fillfactor = 95),
    username          nvarchar(15)  not null,
    password          nvarchar(10)  not null,
    descrizione       nvarchar(100) not null,
    ufficio           nchar(10),
    tel               nvarchar(20),
    email             nvarchar(200),
    enabled           smallint      not null,
    fax               nvarchar(20),
    dataModificaPw    datetime,
    dataUltimoAccesso datetime,
    id_progressivo    int
        constraint FK_Doc_edizioni_utenti_Doc_edizioni_progressivo
            references Doc_edizioni_progressivo
)
go

create table Doc_edizioni_utenti_report
(
    id_utente int not null
        constraint FK_Doc_edizioni_utenti_report_Doc_edizioni_utenti
            references Doc_edizioni_utenti,
    id_report int not null
        constraint FK_Doc_edizioni_utenti_report_Doc_edizioni_report
            references Doc_edizioni_report,
    constraint PK_Doc_edizioni_utenti_report
        primary key (id_utente, id_report)
            with (fillfactor = 95)
)
go

create table Doc_esclusione_controlli
(
    ID_domanda      int          not null,
    ID_tp_errore    nvarchar(25) not null,
    data_esclusione datetime,
    constraint PK_Doc_esclusione_controlli
        primary key (ID_domanda, ID_tp_errore)
            with (fillfactor = 95)
)
go

create table Doc_esclusione_tp_errore
(
    ID_servizio       int          not null,
    ID_tp_errore      nvarchar(25) not null,
    tp_errore         nvarchar(500),
    classe            nvarchar(50),
    abilita_operatore smallint,
    constraint PK_Doc_esclusione_tp_errore
        primary key (ID_tp_errore, ID_servizio)
            with (fillfactor = 95)
)
go

create table Doc_hashcode
(
    id       int      not null,
    hashcode int      not null,
    data     datetime not null,
    constraint PK_Doc_hashcode
        primary key (id, hashcode)
            with (fillfactor = 95)
)
go

create table Doc_notificheicef
(
    id_soggetto          int not null
        constraint PK_Doc_notificheicef
            primary key
                with (fillfactor = 95),
    id_notifica          int not null,
    nuovo_sottoscrittore int not null
)
go

create table Doc_trasmissione_locked
(
    Id_domanda int,
    Data       datetime
)
go

create table Doc_warning
(
    id_domanda    numeric  not null,
    id_edizione   numeric  not null,
    id_tp_warning numeric  not null,
    timestamp     datetime not null,
    note          nvarchar(max),
    constraint PK_doc_warning
        primary key (id_domanda, id_edizione, id_tp_warning)
            with (fillfactor = 95)
)
go

create table DocumentiAnni
(
    anno             int not null,
    ID_servizio      int not null,
    ID_superservizio int not null
        constraint FK_DocumentiAnni_Activator_Superservizi
            references Activator_Superservizi,
    enabled          int not null,
    constraint PK_DocumentiAnni
        primary key (anno, ID_servizio)
            with (fillfactor = 95)
)
go

create table Documenti
(
    ID_documenti int            not null
        constraint PK_Documenti
            primary key
                with (fillfactor = 95),
    anno         int            not null,
    ID_servizio  int            not null,
    posizione    int            not null,
    intestazione nvarchar(10)   not null,
    testo        nvarchar(90)   not null,
    commento     nvarchar(2000) not null,
    link         nvarchar(500)  not null,
    enabled      int            not null,
    constraint FK_Documenti_DocumentiAnni
        foreign key (anno, ID_servizio) references DocumentiAnni
)
go

create table Domande_controllo
(
    ID_domanda int not null
)
go

create table Domande_soggetti_apapi_pagamento
(
    id_domanda int           not null,
    id_iban    int           not null,
    iban       varbinary(50) not null
)
go

create table Duplicati_autorizzati
(
    id_domanda             int      not null,
    id_domanda_autorizzata int,
    motivazione            nvarchar(250),
    data_autorizzazione    datetime not null,
    enabled                int      not null,
    id_ticket              nchar(10),
    forza_tutto            smallint not null
)
go

create table EDIL_tp_interventi
(
    ID_tp_intervento int          not null,
    tp_intervento    nvarchar(50) not null,
    ID_servizio      int          not null,
    constraint PK_EDIL_tp_interventi
        primary key (ID_tp_intervento, ID_servizio)
)
go

create table EPU_condannati
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    ID_soggetto      int not null,
    codice_fiscale   nvarchar(50),
    cognome          nvarchar(50),
    nome             nvarchar(50),
    primary key (ID_domanda, ID_dichiarazione)
)
go

create table EPU_tp_presenze_nucleo
(
    id_tp_presenza_nucleo int not null,
    tp_presenza_nucleo    nvarchar(100),
    id_servizio           int not null,
    constraint EPU_tp_presenze_nucleo_PK
        primary key (id_tp_presenza_nucleo, id_servizio)
)
go

create table ER_C_ElaOUT
(
    ID_domanda        int not null,
    ID_soggetto       int not null,
    ID_tp_prestazione int not null,
    importo           numeric(12, 2),
    constraint PK_ER_C_elaout
        primary key (ID_domanda, ID_soggetto, ID_tp_prestazione)
)
go

create table ER_tp_liquidazioni_rateo
(
    ID_tp_liquidazione_rateo int not null
        constraint PK_ER_tp_liquidazioni_rateo
            primary key
                with (fillfactor = 95),
    tp_liquidazione_rateo    nvarchar(200),
    ID_servizio              int
)
go

create table Energia_disagiato
(
    ID_domanda              int      not null,
    ID_soggetto             int      not null,
    ID_apparecchio          smallint not null,
    descr_apparecchio       nvarchar(150),
    data_inizio_apparecchio datetime,
    pess                    smallint,
    ID_certificato          smallint,
    data_certificato        datetime,
    carta_identita          smallint,
    disagiato               smallint not null,
    constraint PK_Energia_disagiato
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table Energia_disagio_tp_potenze
(
    ID_tp_potenza smallint      not null
        constraint PK_Energia_disagio_tp_potenze
            primary key,
    potenza       nvarchar(20)  not null,
    tp_potenza    numeric(3, 2) not null,
    ID_servizio   int           not null
)
go

create table Energia_tp_apparecchi
(
    ID_apparecchio   smallint     not null
        constraint PK_Energia_tp_apparecchi
            primary key,
    tp_apparecchio   nvarchar(50) not null,
    tipo_apparecchio nchar(10)    not null
)
go

create table Energia_tp_certificati
(
    ID_certificato smallint     not null
        constraint PK_Energia_tp_certificati
            primary key,
    certificato    nvarchar(50) not null
)
go

create table Energia_tp_distributori
(
    ID_tp_distributore nvarchar(10) not null
        constraint PK_Energia_distributori
            primary key
                with (fillfactor = 95),
    tp_distributore    nvarchar(70) not null,
    ID_servizio        int          not null,
    ID_servizio_fisico int
)
go

create table Energia_tp_fonti
(
    ID_tp_fonte int          not null
        constraint PK_Energia_tp_fonti
            primary key
                with (fillfactor = 95),
    tp_fonte    nvarchar(80) not null,
    fonte       smallint     not null,
    ID_servizio int          not null
)
go

create table Energia_tp_nucleo
(
    ID_tp_nucleo int          not null
        constraint PK_Energia_tp_nucleo
            primary key
                with (fillfactor = 95),
    tp_nucleo    nvarchar(50) not null,
    ID_servizio  int          not null
)
go

create table Energia_tp_potenze
(
    ID_tp_potenza nvarchar(10) not null
        constraint PK_Energia_potenza
            primary key
                with (fillfactor = 95),
    tp_potenza    nvarchar(50) not null,
    ID_servizio   int          not null
)
go

create table Energia_tp_tariffe
(
    ID_tp_tariffa int          not null
        constraint PK_Energia_tp_tariffe
            primary key
                with (fillfactor = 95),
    tariffa       nvarchar(50) not null,
    ID_servizio   int          not null
)
go

create table FAB_SOG_PF_recuperato
(
    ID_soggetto           int      not null,
    tp_soggetto           nvarchar not null,
    anno                  smallint not null,
    cognome               nvarchar(50),
    nome                  nvarchar(50),
    ID_tp_sex             nvarchar,
    data_nascita          datetime,
    ID_luogo              nvarchar(6),
    luogo_nascita_ita     nvarchar(100),
    luogo_nascita_ted     nvarchar(100),
    codice_fiscale        nvarchar(16),
    ind_supplementari_ita nvarchar(100),
    ANAGRAFICA            smallint,
    sezione               nvarchar,
    recuperato            smallint not null,
    codiceAmministrativo  nvarchar(100)
)
go

create table FAB_tp_codici_diritti
(
    codice_diritto   smallint     not null
        constraint PK_FAB_tp_codici_diritti
            primary key,
    descrizione      nvarchar(50) not null,
    descrizione_icef nvarchar(50),
    ID_tp_diritto    char(2)
)
go

create table FAQ_gruppi
(
    ID_superservizio int          not null
        constraint FK_FAQ_gruppi_Activator_Superservizi
            references Activator_Superservizi,
    ID_gruppo        int          not null
        constraint PK_FAQ_gruppi
            primary key
                with (fillfactor = 95),
    titolo           nvarchar(50) not null,
    ordine           int          not null,
    enabled          int          not null
)
go

create table FAQ_sottogruppi
(
    ID_sottogruppo int          not null
        constraint PK_FAQ_sottogruppi
            primary key
                with (fillfactor = 95),
    ID_gruppo      int          not null
        constraint FK_FAQ_sottogruppi_FAQ_gruppi
            references FAQ_gruppi,
    titolo         nvarchar(50) not null,
    ordine         int          not null,
    enabled        int          not null
)
go

create table FAQ_faq
(
    ID_faq         int            not null
        constraint PK_FAQ_faq
            primary key
                with (fillfactor = 95),
    ID_gruppo      int            not null,
    ID_sottogruppo int            not null
        constraint FK_FAQ_faq_FAQ_sottogruppi
            references FAQ_sottogruppi,
    titolo         nvarchar(50)   not null,
    ordine         int            not null,
    enabled        int            not null,
    domanda        nvarchar(2000) not null,
    risposta       nvarchar(2500) not null
)
go

create table FGIO_tp_studenti
(
    ID_tp_studente int           not null
        constraint PK_FGIO_tp_studenti
            primary key
                with (fillfactor = 95),
    tp_studente    nvarchar(100) not null,
    eta_min        int,
    eta_max        int
)
go

create table FSE_R_Corsi
(
    ID_corso     int           not null
        constraint PK_FSE_R_Corsi
            primary key
                with (fillfactor = 95),
    codice_corso nvarchar(50)  not null,
    corso        nvarchar(300),
    struttura    nvarchar(200) not null,
    durata       int,
    ID_servizio  int           not null,
    servizio     nvarchar(100),
    termine      datetime
)
go

create table FSE_R_Strutture
(
    ID_struttura int not null
        constraint PK_FSE_R_Strutture
            primary key
                with (fillfactor = 95),
    ID_Servizio  int not null,
    struttura    nvarchar(200),
    codice_ente  nvarchar(4)
)
go

create table FSE_termini
(
    codice_corso nvarchar(50) not null
        constraint PK_Table1
            primary key
                with (fillfactor = 95),
    termine      datetime     not null
)
go

create table FSE_tp_corsi
(
    ID_tp_corso smallint not null
        constraint PK_FSE_tp_corsi
            primary key
                with (fillfactor = 90),
    tp_corso    nvarchar(50),
    ID_servizio int      not null
)
go

create table FSE_tp_famiglie
(
    ID_famiglia int           not null
        constraint PK_FSE_tp_famiglie
            primary key
                with (fillfactor = 95),
    famiglia    nvarchar(150) not null,
    indennita   int           not null
)
go

create table Familiari_cancellati
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    constraint PK_Familiari_cancellati
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table Familiari_ext
(
    ID_domanda           int not null,
    ID_dichiarazione     int not null,
    ID_dichiarazione_ext int not null,
    constraint PK_Familiari_ext
        primary key (ID_domanda, ID_dichiarazione, ID_dichiarazione_ext)
            with (fillfactor = 95)
)
go

create table Firmatari_tp_documenti
(
    ID_servizio     int          not null,
    ID_tp_documento int          not null,
    tp_documento    nvarchar(50) not null,
    tipo_INPS       nchar(3),
    constraint PK_Firmatari_tp_documenti_1
        primary key (ID_servizio, ID_tp_documento)
            with (fillfactor = 95)
)
go

create table Firmatari_tp_enti
(
    ID_ente_rilasciatario nchar(2)     not null,
    ID_servizio           int          not null,
    ente                  nvarchar(50) not null,
    constraint PK_Firmatari_tp_ente
        primary key (ID_ente_rilasciatario, ID_servizio)
)
go

create table Firmatari_tp_impedimenti
(
    ID_servizio       int           not null,
    ID_tp_impedimento int           not null,
    tp_impedimento    nvarchar(150) not null,
    constraint PK_Firmatari_tp_impedimenti_1
        primary key (ID_servizio, ID_tp_impedimento)
)
go

create table Firmatari_tp_qualita
(
    ID_servizio   int           not null,
    ID_tp_qualita int           not null,
    tp_qualita    nvarchar(100) not null,
    constraint PK_Firmatari_tp_qualita_1
        primary key (ID_servizio, ID_tp_qualita)
)
go

create table FunctionPoints
(
    id_servizio bigint    not null,
    id_periodo  bigint    not null,
    servizio    nvarchar(300),
    fpkey       nchar(50) not null,
    fpval       bigint,
    constraint PK_FunctionPoints_1
        primary key (id_servizio, id_periodo, fpkey)
)
go

create table GEA_S1P_ANAGRAFICA
(
    TIPOANAG             nvarchar(255) not null,
    ID_ANAG              float         not null,
    MATRIS1P             float,
    COGNOME              nvarchar(255),
    NOME                 nvarchar(255),
    D_NASCITA            datetime,
    NASC                 nvarchar(255),
    COMUNE_NASCITA       nvarchar(255),
    CAP_NASC             float,
    F10                  nchar(10),
    COD_FISCALE          nvarchar(255),
    D_DECESSO            nvarchar(255),
    RESI                 nvarchar(255),
    COMUNE_RESIDENZA     nvarchar(255),
    CAP_RESI             float,
    INDIRIZZO_RESIDENZA  nvarchar(255),
    PROVINCIA_RESIDENZA  nvarchar(255),
    DOMI                 nvarchar(255),
    COMUNE_DOMICILIO     nvarchar(255),
    CAP_DOMI             float,
    INDIRIZZO_DOMICILIO  nvarchar(255),
    PROVINCIA_DOMICILIO  nvarchar(255),
    RECA                 nvarchar(255),
    COMUNE_RECAPITO      nvarchar(255),
    CAP_RECA             float,
    INDIRIZZO_RECAPITO   nvarchar(255),
    PROVINCIA_RECAPITO   nvarchar(255),
    F27                  nvarchar(255),
    dataNascita          datetime,
    validazione_apss     int,
    conome_apss          nvarchar(35),
    nome_apss            nvarchar(35),
    cessazione_apss      nvarchar(35),
    id_assistito_apss    nvarchar(50),
    data_cessazione_apss datetime,
    id_luogo_nascita     nvarchar(50),
    id_luogo_residenza   nvarchar(50),
    COD_FISCALE2         nvarchar(50),
    NASC2                nvarchar(50),
    errorcf              int,
    PRESSO               nvarchar(255),
    constraint PK_GEA_S1P_ANAGRAFICA
        primary key (TIPOANAG, ID_ANAG)
            with (fillfactor = 95)
)
go

create table GEA_S1P_ANAGRAFICA_PRESSO
(
    TIPOANAG                   nvarchar(255),
    ID_ANAG                    float,
    MATRIS1P                   float,
    COGNOME                    nvarchar(255),
    NOME                       nvarchar(255),
    D_NASCITA                  datetime,
    NASC                       nvarchar(255),
    COMUNE_NASCITA             nvarchar(255),
    CAP_NASC                   float,
    COD_FISCALE                nvarchar(255),
    D_DECESSO                  nvarchar(255),
    RESI                       nvarchar(255),
    COMUNE_RESIDENZA           nvarchar(255),
    CAP_RESI                   float,
    INDIRIZZO_RESIDENZA        nvarchar(255),
    PROVINCIA_RESIDENZA        nvarchar(255),
    DOMI                       nvarchar(255),
    [COMUNE_DOMICILIO        ] nvarchar(255),
    CAP_DOMI                   float,
    INDIRIZZO_DOMICILIO        nvarchar(255),
    PROVINCIA_DOMICILIO        nvarchar(255),
    RECA                       nvarchar(255),
    COMUNE_RECAPITO            nvarchar(255),
    CAP_RECA                   float,
    INDIRIZZO_RECAPITO         nvarchar(255),
    PROVINCIA_RECAPITO         nvarchar(255),
    PRESSO                     nvarchar(255),
    F28                        nvarchar(255)
)
go

create table GEA_S1P_ANAGRAFICA_VALIDATA
(
    TIPOANAG                   nvarchar(255),
    ID_ANAG                    float,
    MATRIS1P                   float,
    COGNOME                    nvarchar(255),
    NOME                       nvarchar(255),
    D_NASCITA                  nvarchar(255),
    NASC                       nvarchar(255),
    COMUNE_NASCITA             nvarchar(255),
    CAP_NASC                   float,
    F10                        nvarchar(255),
    COD_FISCALE                nvarchar(255),
    D_DECESSO                  nvarchar(255),
    RESI                       nvarchar(255),
    COMUNE_RESIDENZA           nvarchar(255),
    CAP_RESI                   float,
    INDIRIZZO_RESIDENZA        nvarchar(255),
    F17                        nvarchar(255),
    DOMI                       nvarchar(255),
    [COMUNE_DOMICILIO        ] nvarchar(255),
    CAP_DOMI                   float,
    INDIRIZZO_DOMICILIO        nvarchar(255),
    F22                        nvarchar(255),
    RECA                       nvarchar(255),
    COMUNE_RECAPITO            nvarchar(255),
    CAP_RECA                   float,
    INDIRIZZO_RECAPITO         nvarchar(255),
    F27                        nvarchar(255),
    F28                        nvarchar(255),
    dataNascita                datetime,
    validazione_apss           int,
    conome_apss                nvarchar(35),
    nome_apss                  nvarchar(35),
    cessazione_apss            nvarchar(35),
    id_assistito_apss          nvarchar(50),
    data_cessazione_apss       datetime,
    id_luogo_nascita           nvarchar(50),
    id_luogo_residenza         nvarchar(50),
    DateTimeColumn             datetime,
    COD_FISCALE2               nvarchar(50),
    NASC2                      nvarchar(50),
    errorcf                    int,
    IdColumn                   int not null
)
go

create table GEA_S1P_CASELLARIO_INPS
(
    ENTE                      float,
    COD_FISCALE               nvarchar(255),
    MATRIS1P                  float,
    TRA                       nvarchar(255),
    TIMESTAMP_DI_TRASMISSIONE nvarchar(255)
)
go

create table GEA_S1P_COMPETENZE
(
    MATRIS1P          float         not null,
    ID_RAPPO          float         not null,
    TIPO              nvarchar(255) not null,
    CS1P              float         not null,
    tipo_informazione nvarchar(255),
    DI_INFO           datetime      not null,
    DF_INFO2          nvarchar(255),
    D_DELIBERA        datetime,
    N_ATTO            nvarchar(255),
    MATRINPS          nvarchar(255),
    TIPO1             nvarchar(255),
    SIGL              nvarchar(255),
    ELIM              nvarchar(255),
    EXCO              nvarchar(255),
    TTRA              nvarchar(255),
    NORMA             nvarchar(255),
    DF_INFO           datetime,
    constraint PK_GEA_S1P_COMPETENZE
        primary key (MATRIS1P, ID_RAPPO, TIPO, CS1P, DI_INFO)
            with (fillfactor = 95)
)
go

create index IDX_GEA_S1P_COMPETENZE
    on GEA_S1P_COMPETENZE (MATRIS1P, ID_RAPPO)
    with (fillfactor = 95)
go

create table GEA_S1P_COMP_TRA_CEDO
(
    MATRIS1P       float,
    TIPO           nvarchar(255),
    ID_RAPPO       float,
    DI_COMPET      datetime,
    DF_COMPET      datetime,
    DI_PAGAME      datetime,
    DF_PAGAME      datetime,
    VOCE           float,
    DESC_VOCE      nvarchar(255),
    IMPORTO        float,
    TIPO_PAGAMENTO nvarchar(255),
    IBAN           nvarchar(255),
    ID_DELEGA      float,
    NMAN           float,
    SUBM           float,
    D_MANDATO      datetime
)
go

create clustered index _dta_index_GEA_S1P_COMP_TRA_CEDO_c_6_1847169826__K1_K3_K4
    on GEA_S1P_COMP_TRA_CEDO (MATRIS1P, ID_RAPPO, DI_COMPET)
    with (fillfactor = 95)
go

create index IDX_GEA_S1P_COMP_TRA_CEDO
    on GEA_S1P_COMP_TRA_CEDO (MATRIS1P, ID_RAPPO)
    with (fillfactor = 95)
go

create table GEA_S1P_C_ElaOUT
(
    anno          int          not null,
    ID_domanda    int          not null,
    node          nvarchar(50) not null,
    numeric_value float,
    constraint PK_GEA_S1P_C_ElaOUT
        primary key (anno, ID_domanda, node)
            with (fillfactor = 95)
)
go

create index IND_GEA_S1P_C_ElaOUT
    on GEA_S1P_C_ElaOUT (ID_domanda)
    with (fillfactor = 95)
go

create table GEA_S1P_ENTI
(
    entes1p            nvarchar(250),
    id_ente_clesius    int,
    id_ufficio_clesius int
)
go

create table GEA_S1P_PAGAMENTI
(
    MATRIS1P               float,
    TIPO                   nvarchar(255),
    DESC_TIPO_INFORMAZIONE nvarchar(255),
    TS_APPLICAZIONE        nvarchar(255),
    DI_INFO                datetime,
    DF_INFO                nvarchar(255),
    ID_ANAG                float,
    IBAN                   nvarchar(255)
)
go

create index IDX_GEA_S1P_PGAMENTI
    on GEA_S1P_PAGAMENTI (MATRIS1P)
    with (fillfactor = 95)
go

create table GEA_S1P_RAPPORTI
(
    MATRIS1P      float,
    ID_RAPPO      float,
    DI_GIURID     datetime,
    DF_GIURID2    nvarchar(255),
    DI_ECONOM     datetime,
    DF_ECONOM2    nvarchar(255),
    TIPO_RAPPORTO nvarchar(255),
    TIPO_CHIUSURA nvarchar(255),
    DF_GIURID     datetime,
    DF_ECONOM     datetime
)
go

create index IDX_GEA_S1P_RAPPORTI
    on GEA_S1P_RAPPORTI (MATRIS1P, ID_RAPPO)
    with (fillfactor = 95)
go

create table GEA_S1P_REDDITI
(
    matris1p    int not null,
    redditi2014 int,
    redditi2015 int
)
go

create table GEA_S1P_RETIFICHE
(
    MATRICOLA   float,
    TIPO        float,
    ID_RAPPORTO float,
    CODICE      float,
    DESCRIZIONE nvarchar(255),
    INVALIDITA  float,
    DATA_ECONOM datetime
)
go

create table GEA_S1P_id
(
    id int not null
)
go

create table GEA_S1P_import_domande
(
    id int
)
go

create table GEA_S1P_import_soggetti_apapi
(
    id int
)
go

create table GEA_cache
(
    id          int not null
        constraint PK_GEA_cahe
            primary key
                with (fillfactor = 95),
    id_servizio int,
    id_periodo  int,
    edizione    int
)
go

create index _dta_index_GEA_cache_7_1475692505__K3_K2_K1
    on GEA_cache (id_periodo, id_servizio, id)
    with (fillfactor = 95)
go

create index _dta_index_GEA_cache_8_1475692505__K1_4
    on GEA_cache (id) include (edizione)
    with (fillfactor = 95)
go

create table GEA_casellario_anagrafica
(
    id                 int identity,
    anno               int,
    tipologia          nchar(50),
    blocco             int,
    tipo_trattamento   nvarchar(50),
    codice_ente        nvarchar(50),
    numero_trattamento nvarchar(50),
    tipo_record        nvarchar(50),
    sigla_titolare     nvarchar(50),
    cognome            nvarchar(50),
    nome               nvarchar(50),
    data_nascita       nvarchar(50),
    sesso              nvarchar(50),
    codice_fiscale     nvarchar(50),
    comune_nascita     nvarchar(150),
    provincia_nascita  nvarchar(50),
    stato_nascita      nvarchar(50),
    stato_cittadinanza nvarchar(50),
    cf_corretto        nvarchar(50),
    tipo_correzione    nvarchar(50),
    data_decesso       datetime,
    inviata_chiusura   int,
    cf_verificato      nvarchar(50),
    id_soggetto        int,
    note               nvarchar(150)
)
go

create index IX_GEA_casellario_anagrafica_cf
    on GEA_casellario_anagrafica (codice_fiscale)
go

create table GEA_casellario_cache
(
    codice_fiscale nvarchar(50) not null
        constraint PK_gea_casellario_cache
            primary key
                with (fillfactor = 95)
)
go

create table GEA_casellario_correzione_matricole
(
    id_domanda     bigint,
    matricola_inps nvarchar(50)
)
go

create table GEA_casellario_dati_inps
(
    categoria             int,
    ente                  int,
    certificato           nvarchar(50),
    codice_cessazione     nvarchar(50),
    codice_fiscale        nvarchar(50),
    decorrenza_iniziale   nvarchar(50),
    decorrenza_cessazione nvarchar(50),
    cf_corretto           nvarchar(50),
    data_decesso          datetime,
    nome                  nvarchar(50),
    cognome               nvarchar(50),
    data_nascita          nvarchar(50),
    presente_clesius      int,
    rilevato_decesso      int,
    chiusura_forzata      int,
    motivo_chiusura       nvarchar(50),
    data_chiusura         datetime,
    note                  nvarchar(50),
    gestita_manualmente   int,
    apss_cessazione       int
)
go

create index IX_GEA_casellario_dati_inps
    on GEA_casellario_dati_inps (codice_fiscale)
    with (fillfactor = 95)
go

create index IX_gea_casellario_dati_inps_c
    on GEA_casellario_dati_inps (certificato)
    with (fillfactor = 95)
go

create table GEA_casellario_dati_inps_deceduti
(
    certificato nvarchar(57)
)
go

create index IX_gea_casellario_dati_inps_deceduti_certificato
    on GEA_casellario_dati_inps_deceduti (certificato)
    with (fillfactor = 95)
go

create index IX_gea_casellario_dati_inps_deceduti
    on GEA_casellario_dati_inps_deceduti (certificato)
    with (fillfactor = 95)
go

create table GEA_casellario_importi_annui
(
    id                                                   int identity,
    anno                                                 int,
    tipologia                                            nchar(10),
    blocco                                               int,
    tipo_trattamento                                     nvarchar(50),
    codice_ente                                          nvarchar(50),
    numero_trattamento                                   nvarchar(50),
    tipo_record                                          nvarchar(50),
    anno_riferimento                                     nvarchar(50),
    codice_imponibilita                                  nvarchar(50),
    tipologia_record                                     nvarchar(50),
    importo_annuo                                        nvarchar(50),
    detrazioni_imposta                                   nvarchar(50),
    detrazioni_carichi_famiglia                          nvarchar(50),
    importo_annuo_oneri_deducibili                       nvarchar(50),
    imposta_netta_annua_irpef                            nvarchar(50),
    arretrati_anni_precedenti_tassazione_separata        nvarchar(50),
    importo_detrazione_imposta_arretrati                 nvarchar(50),
    aliquota_media_arretrati                             nvarchar(50),
    imposta_netta_arretrati                              nvarchar(50),
    importo_fringe_benefit                               nvarchar(50),
    importo_annuo_addizionale_regionale                  nvarchar(50),
    importo_acconto_addizionale_comunale                 nvarchar(50),
    importo_saldo_addizionale_comunale                   nvarchar(50),
    importo_acconto_addizionale_comunale_anno_successivo nvarchar(50),
    importo_aggiuntivo                                   nvarchar(50),
    importo_aumento_pensioni_basse                       nvarchar(50),
    importo_contributo_solidarieta                       nvarchar(50),
    importo_calcolo_contributo_solidarieta               nvarchar(50)
)
go

create index IX_GEA_casellario_importi_annui
    on GEA_casellario_importi_annui (anno, blocco, numero_trattamento)
go

create table GEA_casellario_importi_mensili
(
    id                                      int identity,
    anno                                    int,
    tipologia                               nchar(10),
    blocco                                  int,
    tipo_trattamento                        nvarchar(50),
    codice_ente                             nvarchar(50),
    numero_trattamento                      nvarchar(50),
    tipo_record                             nvarchar(50),
    data_riferimento                        nvarchar(50),
    importo_mensile_lordo                   nvarchar(50),
    importo_mensile_trattamento_famiglia    nvarchar(50),
    importo_mensile_indennita               nvarchar(50),
    importo_mensile_assegno_accompagnamento nvarchar(50),
    importo_mensile_assegno_incollocabilita nvarchar(50),
    importo_mensile_imponibile_irpef        nvarchar(50),
    importo_mensile_perequazione            nvarchar(50),
    importo_mensile_maggiorazione           nvarchar(50),
    codice_pensione_integrata               nvarchar(50),
    importoIC                               numeric(18, 2),
    importoAC                               numeric(18, 2),
    importoL11                              numeric(18, 2),
    importoINT                              numeric(18, 2),
    importoIND                              numeric(18, 2),
    importoTOT                              numeric(18, 2)
)
go

create index IX_GEA_casellario_importi_mensili
    on GEA_casellario_importi_mensili (anno, blocco, numero_trattamento)
go

create table GEA_casellario_matricole_sostituite
(
    matricola_origine      nvarchar(50) not null,
    matricola_destinazione nvarchar(50) not null,
    codice_fiscale         nvarchar(50) not null,
    data_sostituzione      datetime
)
go

create table GEA_casellario_residenza
(
    id                  int identity,
    anno                int,
    tipologia           nchar(10),
    blocco              int,
    tipo_trattamento    nvarchar(50),
    codice_ente         nvarchar(50),
    numero_trattamento  nvarchar(50),
    tipo_record         nvarchar(50),
    tipo_indirizzo      nvarchar(50),
    indirizzo           nvarchar(50),
    numero_civico       nvarchar(50),
    frazione            nvarchar(50),
    comune_residenza    nvarchar(50),
    provincia_residenza nvarchar(50),
    stato_residenza     nvarchar(50),
    cap                 nvarchar(50),
    residente_estero    nvarchar(50)
)
go

create index IX_GEA_casellario_residenza
    on GEA_casellario_residenza (anno, blocco, numero_trattamento)
go

create table GEA_casellario_trattamento
(
    id                                  int identity,
    anno                                int,
    tipologia                           nchar(10),
    blocco                              int,
    tipo_trattamento                    nvarchar(50),
    codice_ente                         nvarchar(50),
    numero_trattamento                  nvarchar(50),
    tipo_record                         nvarchar(50),
    tipologia_trattamento               nvarchar(50),
    decorrenza_iniziale                 nvarchar(50),
    cod_ex_combattente                  nvarchar(50),
    numero_mesi_erogazione              nvarchar(50),
    codice_pensione                     nvarchar(50),
    cessazione                          nvarchar(50),
    codice_cessazione                   nvarchar(50),
    data_cessazione                     nvarchar(50),
    chiave_trattamento_provenienza      nvarchar(50),
    trattamenti_assistenziali           nvarchar(50),
    particolari_benefici                nvarchar(50),
    contributo_solidarieta              nvarchar(50),
    vittime_terrorismo                  nvarchar(50),
    anno_decorrenza                     nvarchar(50),
    totalizzazione_periodi_assicurativi nvarchar(50),
    riferimento_pensione                nvarchar(50),
    numero_contributi_settimanali       nvarchar(50),
    data_liquidazione_pensione          nvarchar(50),
    pensione_sistema_contributivo       nvarchar(50),
    note                                nvarchar(150)
)
go

create index IX_GEA_casellario_trattamento
    on GEA_casellario_trattamento (anno, blocco, numero_trattamento)
go

create table GEA_fam_ac
(
    id_domanda int not null,
    stato      int not null
)
go

create table GEA_id_ic
(
    id_domanda int not null
)
go

create table GEA_importi_corrente
(
    id_domanda      int            not null,
    id_servizio     int            not null,
    id_tp_beneficio int            not null,
    anno            int            not null,
    periodo         int            not null,
    importo         numeric(18, 2) not null,
    constraint PK_GEA_importi_corrente
        primary key (id_domanda, id_servizio, id_tp_beneficio, anno, periodo)
            with (fillfactor = 95)
)
go

create table GEA_importi_corrente_somma
(
    id_domanda      int            not null
        constraint PK_GEA_importi_corrente_somma
            primary key
                with (fillfactor = 95),
    importo_inviato numeric(18, 2) not null,
    importo_attuale numeric(18, 2) not null
)
go

create table GEA_pagamenti
(
    id_domanda            int            not null,
    edizione              int            not null,
    id_servizio           int            not null,
    id_tp_beneficio       int            not null,
    codice_beneficio      varchar(100)   not null,
    descrizione_beneficio varchar(500),
    anno                  int            not null,
    periodo               int            not null,
    importo               numeric(18, 2) not null,
    numero_mandato        varchar(50)    not null,
    data_mandato          datetime       not null,
    posta                 numeric(20)    not null,
    data_pagamento        datetime       not null,
    data_siuss            datetime,
    constraint PK_GEA_pagamenti
        primary key (id_domanda, id_tp_beneficio, codice_beneficio, anno, periodo, data_mandato, posta)
            with (fillfactor = 95)
)
go

create table GEA_pagamenti_anagrafica
(
    id_blocco       int         not null,
    id_domanda_sogg int         not null,
    edizione        int         not null,
    id_soggetto     int         not null,
    id_domanda      int         not null,
    id_tp_pagamento int         not null,
    iban_ok         int         not null,
    iban            varchar(50) not null,
    constraint PK_GEA_pagamenti_anagrafica
        primary key (id_blocco, id_domanda_sogg, edizione, id_soggetto, id_domanda)
)
go

create index _dta_index_GEA_pagamenti_anagrafica_7_2013458447__K5
    on GEA_pagamenti_anagrafica (id_domanda)
go

create index _dta_index_GEA_pagamenti_anagrafica_7_2013458447__K5_K1_K4_K6_K8
    on GEA_pagamenti_anagrafica (id_domanda, id_blocco, id_soggetto, id_tp_pagamento, iban)
go

create table GEA_pagamenti_anagrafica_bu
(
    id_blocco       int         not null,
    id_domanda_sogg int         not null,
    edizione        int         not null,
    id_soggetto     int         not null,
    id_domanda      int         not null,
    id_tp_pagamento int         not null,
    iban_ok         int         not null,
    iban            varchar(50) not null
)
go

create table GEA_pagamenti_bu
(
    id_domanda            int            not null,
    edizione              int            not null,
    id_servizio           int            not null,
    id_tp_beneficio       int            not null,
    codice_beneficio      varchar(100)   not null,
    descrizione_beneficio varchar(500),
    anno                  int            not null,
    periodo               int            not null,
    importo               numeric(18, 2) not null,
    numero_mandato        varchar(50)    not null,
    data_mandato          datetime       not null,
    posta                 numeric(20)    not null,
    data_pagamento        datetime       not null,
    data_siuss            datetime
)
go

create table GEA_tp_attributi
(
    id_tp_attributo int           not null
        constraint PK_GEA_tp_attributi
            primary key
                with (fillfactor = 95),
    tp_attributo    nvarchar(500) not null,
    tipo            nvarchar(50)  not null
)
go

create table GEA_altre_info
(
    id_blocco       int           not null,
    id_domanda      int           not null,
    edizione        int           not null,
    id_tp_attributo int           not null
        constraint FK_GEA_altre_info_GEA_tp_attributi
            references GEA_tp_attributi,
    valore          nvarchar(255) not null,
    constraint PK_GEA_altre_info
        primary key (id_blocco, id_domanda, edizione, id_tp_attributo)
            with (fillfactor = 90)
)
go

create index _dta_index_GEA_altre_info_8_2104550731__K2_K3_K4_1_5
    on GEA_altre_info (id_domanda, edizione, id_tp_attributo) include (id_blocco, valore)
go

create table GEA_tp_benefici
(
    id_servizio     int not null,
    id_tp_beneficio int not null,
    tp_beneficio    nvarchar(255),
    periodicita     int not null,
    constraint PK_GEA_tp_benefici
        primary key (id_servizio, id_tp_beneficio)
            with (fillfactor = 95)
)
go

create table GEA_importi
(
    id_blocco       int            not null,
    id_domanda      int            not null,
    edizione        int            not null,
    id_servizio     int            not null,
    id_tp_beneficio int            not null,
    anno            int            not null,
    periodo         int            not null,
    importo         numeric(18, 2) not null,
    importo_teorico numeric(18, 2),
    nota            nvarchar(255),
    constraint PK_GEA_entita_importi
        primary key (id_blocco, id_domanda, edizione, id_servizio, id_tp_beneficio, anno, periodo)
            with (fillfactor = 95),
    constraint FK_GEA_importi_GEA_tp_benefici
        foreign key (id_servizio, id_tp_beneficio) references GEA_tp_benefici
)
go

create index _dta_index_GEA_importi_7_1976550275__K2_K3_K6_1_5_7_8
    on GEA_importi (id_domanda, edizione, anno) include (id_blocco, id_tp_beneficio, periodo, importo)
    with (fillfactor = 95)
go

create table GEA_tp_esclusioni
(
    id_servizio       int not null,
    id_tp_beneficio   int not null,
    id_tp_esclusione  int not null,
    tp_esclusione     nvarchar(255),
    genere_esclusione nvarchar(255),
    istruttoria       int,
    constraint PK_GEA_tp_esclusioni
        primary key (id_servizio, id_tp_beneficio, id_tp_esclusione)
            with (fillfactor = 95),
    constraint FK_GEA_tp_esclusioni_GEA_tp_benefici
        foreign key (id_servizio, id_tp_beneficio) references GEA_tp_benefici
)
go

create table GEA_motivi_esclusione
(
    id_blocco        int not null,
    id_domanda       int not null,
    edizione         int not null,
    id_servizio      int not null,
    id_tp_beneficio  int not null,
    anno             int not null,
    id_tp_esclusione int not null,
    constraint PK_GEA_motivi_esclusion
        primary key (id_blocco, id_domanda, edizione, id_tp_beneficio, anno, id_tp_esclusione)
            with (fillfactor = 90),
    constraint FK_GEA_motivi_esclusione_GEA_tp_benefici
        foreign key (id_servizio, id_tp_beneficio) references GEA_tp_benefici,
    constraint FK_GEA_motivi_esclusione_GEA_tp_esclusioni
        foreign key (id_servizio, id_tp_beneficio, id_tp_esclusione) references GEA_tp_esclusioni
)
go

create index IDX_GEA_motivi_esclusione_ID_DOMANDA_ID_TP_BENEFICIO
    on GEA_motivi_esclusione (id_domanda, id_tp_beneficio)
go

create table GEA_motivi_sospensione
(
    id_blocco        int not null,
    id_domanda       int not null,
    edizione         int not null,
    id_servizio      int not null,
    id_tp_beneficio  int not null,
    anno             int not null,
    periodo          int not null,
    id_tp_esclusione int not null,
    constraint PK_GEA_motivi_sospensione_1
        primary key (id_domanda, edizione, id_tp_beneficio, anno, periodo, id_tp_esclusione),
    constraint FK_GEA_motivi_sospensione_GEA_tp_benefici
        foreign key (id_servizio, id_tp_beneficio) references GEA_tp_benefici,
    constraint FK_GEA_motivi_sospensione_GEA_tp_esclusioni
        foreign key (id_servizio, id_tp_beneficio, id_tp_esclusione) references GEA_tp_esclusioni
)
go

create index IDX_GEA_motivi_sospensione_ID_DOMANDA_ID_TP_BENEFICIO
    on GEA_motivi_sospensione (id_domanda, id_tp_beneficio)
go

create table GEA_tp_esiti
(
    id_tp_esito int not null
        constraint PK_GEA_tp_esiti
            primary key
                with (fillfactor = 95),
    tp_esito    nvarchar(250)
)
go

create table GEA_tp_provvedimenti
(
    id_tp_provvedimento int           not null
        constraint PK_GEA_tp_provvedimenti
            primary key
                with (fillfactor = 95),
    tp_provvedimento    nvarchar(500) not null
)
go

create table GEA_provvedimenti
(
    id_blocco           int      not null,
    id_domanda          int      not null,
    id_servizio         int      not null,
    edizione            int      not null,
    id_tp_beneficio     int      not null,
    id_tp_provvedimento int      not null
        constraint FK_GEA_provvedimenti_GEA_tp_provvedimenti
            references GEA_tp_provvedimenti,
    id_istanza_gea      int      not null,
    data_provvedimento  datetime not null,
    n_provvedimento     nvarchar(255),
    constraint PK_GEA_provvedimenti_1
        primary key (id_domanda, edizione, id_servizio, id_tp_beneficio, id_tp_provvedimento)
            with (fillfactor = 95),
    constraint FK_GEA_provvedimenti_GEA_tp_benefici
        foreign key (id_servizio, id_tp_beneficio) references GEA_tp_benefici
)
go

create table GR_Att_dati
(
    ID_domanda             int          not null
        constraint PK_GR_Att_dati
            primary key
                with (fillfactor = 95),
    data_relazione         datetime     not null,
    data_scadenza          datetime,
    data_inizio            datetime     not null,
    assistente_sociale     nvarchar(50) not null,
    sana_anche_incongruita smallint     not null
)
go

create table GR_Att_familiari
(
    ID_domanda  int not null,
    ID_soggetto int not null,
    familiare   int not null,
    constraint PK_GR_Att_familiari
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table GR_Incentivo_lavoro
(
    ID_domanda           int      not null
        constraint PK_GR_Incentivo_lavoro
            primary key
                with (fillfactor = 95),
    ID_domanda_collegata int,
    data_inizio_lavoro   datetime not null,
    data_residenza_TAA   datetime
)
go

create table GR_Incentivo_lavoro_importi
(
    ID_domanda int not null
        constraint PK_GR_Incentivo_lavoro_importi
            primary key
                with (fillfactor = 95),
    importo    float
)
go

create table GR_Questionario
(
    ID_domanda      int      not null
        constraint PK_GR_questionario
            primary key
                with (fillfactor = 95),
    tp_questionario nvarchar(50),
    domanda_1       smallint,
    domanda_2a      smallint,
    domanda_2a_1    smallint,
    domanda_2b      smallint,
    domanda_2b_1    smallint,
    domanda_2c      smallint,
    domanda_2c_1    smallint,
    domanda_2d      smallint,
    domanda_2d_1    smallint,
    domanda_2e      smallint,
    domanda_2e_1    smallint,
    domanda_3a      smallint,
    domanda_3a_1    smallint,
    domanda_3b      smallint,
    domanda_3b_1    smallint,
    domanda_3c      smallint,
    domanda_3c_1    smallint,
    domanda_3d      smallint,
    domanda_3d_1    smallint,
    domanda_4a      smallint,
    domanda_4a_1    smallint,
    domanda_5a      smallint,
    domanda_5b      smallint,
    domanda_6       smallint,
    domanda_7       smallint,
    domanda_8       smallint,
    domanda_9       smallint,
    domanda_10      smallint not null
)
go

create table GR_Rinnovi
(
    ID_domanda             int      not null
        constraint PK_GR_Rinnovi
            primary key
                with (fillfactor = 95)
        constraint IX_GR_Rinnovi
            unique
                with (fillfactor = 95),
    data_rinnovo           datetime not null,
    ID_soggetto            int      not null,
    ciclo_24_mesi          int      not null,
    rinnovo                int      not null,
    rinnovo_con_beneficio  int      not null,
    tutti_lavorano         smallint not null,
    ID_attestazione_insuss int,
    importo_mensile        float    not null,
    anno_inizio            int      not null,
    mese_inizio            int      not null,
    per_mesi               int      not null,
    primo_rinnovo          smallint not null
)
go

create table GR_Rinnovi_12
(
    ID_domanda                       int      not null
        constraint PK_GR_Rinnovi_12
            primary key
                with (fillfactor = 95)
        constraint IX_GR_Rinnovi_12
            unique
                with (fillfactor = 95),
    data_rinnovo                     datetime not null,
    ID_soggetto                      int      not null,
    ciclo                            int      not null,
    rinnovo                          int      not null,
    rinnovo_con_beneficio            int      not null,
    tutti_lavorano                   smallint not null,
    ID_attestazione_insuss           int,
    importo_mensile                  float    not null,
    anno_inizio                      int      not null,
    mese_inizio                      int      not null,
    per_mesi                         int      not null,
    automatismo                      smallint not null,
    transizione                      smallint not null,
    rinnovo_con_beneficio_precedente smallint not null,
    controllo_spil_effettuato        bit
)
go

create table GR_Rinnovi_12_storico
(
    ID_domanda                       int      not null,
    data_storico                     datetime not null,
    data_rinnovo                     datetime not null,
    ID_soggetto                      int      not null,
    ciclo                            int      not null,
    rinnovo                          int      not null,
    rinnovo_con_beneficio            int      not null,
    tutti_lavorano                   smallint not null,
    ID_attestazione_insuss           int,
    importo_mensile                  float    not null,
    anno_inizio                      int      not null,
    mese_inizio                      int      not null,
    per_mesi                         int      not null,
    automatismo                      smallint not null,
    transizione                      smallint not null,
    rinnovo_con_beneficio_precedente smallint not null,
    constraint PK_GR_Rinnovi_12_storico
        primary key (ID_domanda, data_storico)
)
go

create table GR_Rinnovi_12_transizione
(
    ID_soggetto                  int      not null
        constraint PK_GR_Rinnovi_12_transizione
            primary key,
    data_fine_ciclo_24_mesi      datetime not null,
    rinnovi_esauriti             smallint not null,
    numero_rinnovi_con_beneficio int      not null
)
go

create table GR_Rinnovi_storico
(
    ID_domanda             int      not null,
    data_storico           datetime not null,
    data_rinnovo           datetime not null,
    ID_soggetto            int      not null,
    ciclo_24_mesi          int      not null,
    rinnovo                int      not null,
    rinnovo_con_beneficio  int      not null,
    tutti_lavorano         smallint not null,
    ID_attestazione_insuss int,
    importo_mensile        float    not null,
    anno_inizio            int      not null,
    mese_inizio            int      not null,
    per_mesi               int      not null,
    primo_rinnovo          smallint not null,
    constraint PK_GR_Rinnovi_storico
        primary key (ID_domanda, data_storico)
            with (fillfactor = 95)
)
go

create table GR_tp_attivita_lavorative
(
    ID_tp_attivita_lavorativa int       not null
        constraint PK_GR_tp_attivita_lavorative
            primary key
        constraint FK_GR_tp_attivita_lavorative_GR_familiari
            references GR_tp_attivita_lavorative,
    descrizione               nchar(20) not null
)
go

create table GR_tp_blacklist
(
    id_tp_blacklist    int           not null
        constraint PK_GR_tp_blacklist
            primary key
                with (fillfactor = 95),
    descrizione        nvarchar(200) not null,
    numero_mesi_blocco smallint      not null,
    blocco_cod_fisc    int           not null
)
go

create table GR_blacklist
(
    codice_fiscale            nchar(16) not null,
    id_domanda                int       not null,
    data_inizio_blocco        datetime  not null,
    data_fine_blocco          datetime  not null,
    id_tp_blacklist           int       not null
        constraint FK_GR_blacklist_GR_tp_blacklist
            references GR_tp_blacklist,
    blocca_solo_domande_apapi smallint  not null,
    n_determinazione          nchar(20) not null,
    data_inserimento_blocco   datetime,
    id_ente_inseritore        int,
    constraint PK_GR_blacklist
        primary key (codice_fiscale, id_domanda, data_inizio_blocco, data_fine_blocco, id_tp_blacklist)
)
go

create table GR_tp_esclusione_componente
(
    ID_tp_esclusione smallint not null
        constraint PK_GR_tp_esclusione_componente
            primary key
                with (fillfactor = 95),
    descrizione      nvarchar(100),
    descrizione_mask nvarchar(100)
)
go

create table GR_tp_esclusione_domanda
(
    ID_tp_esclusione smallint      not null
        constraint PK_GR_tp_esclusione_domanda
            primary key
                with (fillfactor = 95),
    ID_servizio      int,
    tp_esclusione    nvarchar(500) not null
)
go

create table GR_tp_pagamento
(
    ID_tp_pagamento smallint not null,
    ID_servizio     smallint not null
)
go

create table GR_tp_scelta_incongrua
(
    ID_tp_scelta_incongrua smallint     not null
        constraint PK_GR_tp_scelta_icongrua
            primary key
                with (fillfactor = 95),
    descrizione            nvarchar(50) not null
)
go

create table GR_tp_scelta_sociale_icef
(
    ID_tp_scelta_sociale_icef smallint      not null,
    ID_servizio               int           not null,
    tp_scelta                 nvarchar(200) not null,
    constraint PK_GR_tp_scelta_sociale_cef
        primary key (ID_tp_scelta_sociale_icef, ID_servizio)
            with (fillfactor = 95)
)
go

create table IC_C_ElaIN
(
    anno        int          not null,
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_IC_C_ElaIN
        primary key (anno, ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create index IND_IC_C_Elain
    on IC_C_ElaIN (ID_domanda)
    with (fillfactor = 95)
go

create table IC_C_ElaOUT
(
    anno          int          not null,
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    locked        int          not null,
    constraint PK_IC_C_Elaout
        primary key (anno, ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create index IND_IC_C_ElaOUT
    on IC_C_ElaOUT (ID_domanda)
    with (fillfactor = 95)
go

create index IND_IC_C_ElaOUT_node
    on IC_C_ElaOUT (node)
    with (fillfactor = 95)
go

create index _dta_index_IC_C_ElaOUT_7_1231499616__K1_K4_K2_K3_6
    on IC_C_ElaOUT (anno, node, ID_domanda, net) include (numeric_value)
    with (fillfactor = 95)
go

create table IC_cf_old
(
    codice_fiscale          nvarchar(16) not null,
    codice                  nvarchar(50) not null,
    data_nascita            smalldatetime,
    enabled                 smallint,
    ID_tp_esclusione        int,
    ID_tp_motivo_esclusione int,
    ID_tp_stato_pensione    int,
    data_esclusione         datetime,
    ID_tp_invalidita        int,
    constraint PK_IC_cf_old
        primary key (codice_fiscale, codice)
            with (fillfactor = 95)
)
go

create table IC_date_valuta
(
    anno        int      not null,
    bimestre    int      not null,
    data_valuta datetime not null,
    constraint PK_IC_date_valuta
        primary key (anno, bimestre)
            with (fillfactor = 95)
)
go

create table IC_domande_processate
(
    ID_domanda int not null
        constraint PK_IC_domande_processate
            primary key
                with (fillfactor = 95),
    processata int,
    ID_user    int
)
go

create table IC_periodi_benefici
(
    id_domanda      int      not null,
    id_tp_beneficio int      not null,
    data_da         datetime not null,
    data_a          datetime,
    constraint PK_IC_periodi_benefici
        primary key (id_domanda, id_tp_beneficio, data_da)
            with (fillfactor = 95)
)
go

create table IC_tp_stati_pensione
(
    ID_tp_stato_pensione int not null
        constraint PK_IC_tp_stati_pensione
            primary key
                with (fillfactor = 95),
    tp_stato_pensione    nvarchar(100)
)
go

create table IC_tp_variazioni
(
    ID_tp_variazione           smallint      not null
        constraint PK_IC_tp_variazioni
            primary key
                with (fillfactor = 95),
    tp_variazione              nvarchar(100) not null,
    data_profilo_rl            smallint,
    data_inizio_rl             smallint,
    data_fine_rl               smallint,
    codice_apss                smallint,
    codice_anaweb              nchar(10),
    ID_tp_variazione_categoria smallint,
    ID_tp_variazione_scope     smallint
)
go

create table IC_tp_variazioni_categorie
(
    ID_tp_variazione_categoria smallint      not null,
    tp_variazione_categoria    nvarchar(100) not null
)
go

create table IC_variazioni_soggetti
(
    ID_variazione    int identity
        constraint PK_IC_variazioni_soggetto
            primary key
                with (fillfactor = 95),
    ID_soggetto      int      not null,
    ID_tp_variazione smallint not null
        constraint FK_IC_variazioni_soggetti_IC_tp_variazioni
            references IC_tp_variazioni,
    data_inizio      datetime,
    data_fine        datetime,
    data_variazione  datetime,
    valore           numeric(12, 2),
    ID_evento_APSS   bigint,
    codice_ric_apss  nvarchar(50),
    origine_ric      nvarchar(50)
)
go

create index _dta_index_IC_variazioni_soggetti_9_962310688__K2_K3_K4_K5
    on IC_variazioni_soggetti (ID_soggetto, ID_tp_variazione, data_inizio, data_fine)
    with (fillfactor = 95)
go

create index _dta_index_IC_variazioni_soggetti_7_962310688__K10_K9_4_5
    on IC_variazioni_soggetti (origine_ric, codice_ric_apss) include (data_inizio, data_fine)
    with (fillfactor = 95)
go

create table IMPORT_PAT
(
    id                      float,
    COD_FISC                nvarchar(255),
    COGNOME                 nvarchar(255),
    NOME                    nvarchar(255),
    RIFERIMENTO             nvarchar(255),
    FIS_ANN_ONERI_LOOKUP_ID float,
    TIPO_NOTA               nvarchar(255),
    CODICE                  nvarchar(255),
    DESCRIZIONE             nvarchar(255),
    IMPORTO                 float,
    ID_tp_erogazione        char(3),
    F12                     nvarchar(255),
    F13                     nvarchar(255)
)
go

create table IMPORT_PAT_DECODIFICA
(
    TIPO_NOTA        nvarchar(255),
    CODICE           nvarchar(255),
    DESCRIZIONE      nvarchar(255),
    ID_tp_erogazione char(3)
)
go

create table IMPORT_PAT_DEFINITIVO
(
    COD_FISC         nvarchar(255),
    ID_tp_erogazione char(3),
    IMPORTO          float
)
go

create table INTMIL_cf_old
(
    codice_fiscale          nvarchar(16) not null,
    codice                  nvarchar(50) not null,
    data_nascita            smalldatetime,
    enabled                 smallint,
    ID_tp_esclusione        int,
    ID_tp_motivo_esclusione int,
    ID_tp_stato_pensione    int,
    data_esclusione         datetime,
    ID_tp_invalidita        int,
    constraint PK_INTMIL_cf_old
        primary key (codice_fiscale, codice)
            with (fillfactor = 95)
)
go

create table ISTAT_temp_estrazione_R_Benefici_2013
(
    ID_servizio           int           not null,
    servizio              nvarchar(100) not null,
    nodo_risultato        nvarchar(50),
    descrizione_beneficio nvarchar(200)
)
go

create table ISTAT_temp_estrazione_R_Benefici_2014
(
    ID_servizio           int           not null,
    servizio              nvarchar(100) not null,
    nodo_risultato        nvarchar(50),
    descrizione_beneficio nvarchar(200)
)
go

create table ISTAT_temp_estrazione_R_Benefici_2015
(
    ID_servizio           int           not null,
    servizio              nvarchar(100) not null,
    nodo_risultato        nvarchar(50),
    descrizione_beneficio nvarchar(200)
)
go

create table ISTAT_temp_estrazione_dich_2013
(
    [id individuo]                                                    int         not null,
    [codice fiscale]                                                  nchar(16)   not null,
    [codice catastale residenza]                                      nvarchar(4),
    [codice ISTAT residenza]                                          nvarchar(6) not null,
    [anno produzione redditi]                                         smallint    not null,
    [anno produzione patrimoni]                                       smallint,
    sesso                                                             nvarchar    not null,
    [anno nascita]                                                    int,
    [mese nascita]                                                    int,
    [patrimonio mobiliare]                                            float,
    [patrimonio immobiliare residenza]                                float,
    [patrimonio immobiliare altri]                                    float,
    [reddito autonomo]                                                float,
    [reddito dipendente]                                              float,
    [reddito da pensione]                                             float,
    [reddito da impresa agricola]                                     float,
    [imposte nette]                                                   decimal(38, 2),
    [Assegno regionale al nucleo  (L R 1_2005)]                       decimal(38, 2),
    [Assistenza figli part-time]                                      decimal(38, 2),
    [Contributo per costituzione di pensione di vecchiaia]            decimal(38, 2),
    [Contributo per costituzione di pensione complementare]           decimal(38, 2),
    [Reddito di garanzia APAPI (LP 13_07 Art35 com 2)]                decimal(38, 2),
    [Incentivo al lavoro]                                             decimal(38, 2),
    [Previdenza complementare (LR 5-2009)]                            decimal(38, 2),
    [Contributo famiglie numerose]                                    decimal(38, 2),
    [Contributi IVS]                                                  decimal(38, 2),
    [Contributo sostegno potere acquisto familie]                     decimal(38, 2),
    [Rendite pre lavoratori sordità da rumori]                        decimal(38, 2),
    [Rendite per lavoratori con silicosi o asbestosi]                 decimal(38, 2),
    [Indennità per ciechi civili, sordomuti ed invalidi civili]       decimal(38, 2),
    [Pensioni, assegni per ciechi civili, sordomuti, invalidi civili] decimal(38, 2),
    [Assegno di cura (LP 15_2012)]                                    decimal(38, 2),
    [Contributo riscatto a fini pensionistici del lavoro estero]      decimal(38, 2),
    [Interventi una tantum per situazioni di emergenza]               decimal(38, 2),
    [Assegno di cura]                                                 decimal(38, 2),
    [Assegno al nucleo familiare (L 448_98)]                          decimal(38, 2),
    [Assegno di maternità (L 448_98)]                                 decimal(38, 2),
    [Sussidi esenti IRPEF per finalità di studio (es LP30-1978)]      decimal(38, 2),
    [Contributo protesi dentarie e cure ortodontiche (LP20-91)]       decimal(38, 2),
    [Reddito di garanzia - Servizi Sociali - (LP13_07 Art35 com 2)]   decimal(38, 2),
    [Anticip assegno mantenimento tutela minori (LP 14_91)]           decimal(38, 2),
    [Concorso forf prest fisioterapia domicilio (DGP 1306_08)]        decimal(38, 2),
    [Borse di studio erogate dall'Opera Universitaria]                decimal(38, 2),
    [Cure odontoiatriche (LP27_07)]                                   decimal(38, 2),
    [Rendite INAIL]                                                   decimal(38, 2),
    [Bonus Bebe]                                                      decimal(38, 2),
    [Pensioni soc e maggiorazione soc trattamenti pensionistici]      decimal(38, 2),
    [Assegni periodici per mantenimento figli]                        decimal(38, 2),
    [Rimborso oneri deducibili ai fini ICEF]                          decimal(38, 2),
    [Altro ]                                                          decimal(38, 2)
)
go

create table ISTAT_temp_estrazione_dich_2014
(
    [id individuo]                                                                                             int         not null,
    [codice fiscale]                                                                                           nchar(16)   not null,
    [codice catastale residenza]                                                                               nvarchar(4),
    [codice ISTAT residenza]                                                                                   nvarchar(6) not null,
    [anno produzione redditi]                                                                                  smallint    not null,
    [anno produzione patrimoni]                                                                                smallint,
    sesso                                                                                                      nvarchar    not null,
    [anno nascita]                                                                                             int,
    [mese nascita]                                                                                             int,
    [patrimonio mobiliare]                                                                                     float,
    [patrimonio immobiliare residenza]                                                                         float,
    [patrimonio immobiliare altri]                                                                             float,
    [reddito autonomo]                                                                                         float,
    [reddito dipendente]                                                                                       float,
    [reddito da pensione]                                                                                      float,
    [reddito da impresa agricola]                                                                              float,
    [imposte nette]                                                                                            decimal(38, 2),
    [Assegno regionale al nucleo familiare (L.R.1/2005)]                                                       decimal(38, 2),
    [Assistenza figli part-time]                                                                               decimal(38, 2),
    [Contributo per costituzione di pensione di vecchiaia]                                                     decimal(38, 2),
    [Contributo per costituzione di pensione complementare]                                                    decimal(38, 2),
    [Reddito di garanzia - APAPI - (L.P.13/07 Art.35 comma 2)]                                                 decimal(38, 2),
    [Incentivo al lavoro]                                                                                      decimal(38, 2),
    [Previdenza complementare (L.R. 5-2009)]                                                                   decimal(38, 2),
    [Contributo famiglie numerose]                                                                             decimal(38, 2),
    [Contributi I.V.S.]                                                                                        decimal(38, 2),
    [Contributo a sostegno del potere d'acquisto dei nuclei familiari]                                         decimal(38, 2),
    [Rendite a favore dei lavoratori affetti da sordità da rumori]                                             decimal(38, 2),
    [Rendite a favore dei lavoratori affetti da silicosi o da asbestosi]                                       decimal(38, 2),
    [Indennità per ciechi civili, sordomuti ed invalidi civili]                                                decimal(38, 2),
    [Pensioni ed assegni per ciechi civili, sordomuti ed invalidi civili]                                      decimal(38, 2),
    [Assegno di cura (L.P. 15/2012)]                                                                           decimal(38, 2),
    [Contributo per riscatto a fini pensionistici del lavoro all'estero]                                       decimal(38, 2),
    [Interventi una tantum per situazioni di emergenza]                                                        decimal(38, 2),
    [Sussidio economico per assistenza di persone non autosufficienti (assegno di cura)]                       decimal(38, 2),
    [Assegno al nucleo familiare (L.448/98)]                                                                   decimal(38, 2),
    [Assegno di maternità (L.448/98)]                                                                          decimal(38, 2),
    [Sussidi esenti IRPEF per finalità di studio (es. L.P.30/1978)]                                            decimal(38, 2),
    [Contributo protesi dentarie e cure ortodontiche (L.P.20/91)]                                              decimal(38, 2),
    [Reddito di garanzia - Servizi Sociali - (L.P.13/07 Art.35 comma 2)]                                       decimal(38, 2),
    [Anticipazione dell'assegno di mantenimento a tutela dei figli minori (LP 14/91)]                          decimal(38, 2),
    [Concorso forfettario prest. fisioterapia domicilio, fibrosi cistica, displasia ectodermica (DGP 1306/08)] decimal(38, 2),
    [Borse di studio erogate dall'Opera Universitaria]                                                         decimal(38, 2),
    [Cure odontoiatriche (L.P.27/07)]                                                                          decimal(38, 2),
    [Rendite INAIL]                                                                                            decimal(38, 2),
    [Bonus Bebè]                                                                                               decimal(38, 2),
    [Pensioni sociali e maggiorazione sociale di trattamenti pensionistici]                                    decimal(38, 2),
    [Intervento di solidarietà sociale della Comunità del Primiero]                                            decimal(38, 2),
    [Assegni periodici per mantenimento figli]                                                                 decimal(38, 2),
    [Rimborso oneri deducibili ai fini ICEF]                                                                   decimal(38, 2),
    Altro                                                                                                      decimal(38, 2)
)
go

create table ISTAT_temp_estrazione_dich_2015
(
    [id individuo]                                             int         not null,
    [codice fiscale]                                           nchar(16)   not null,
    [codice catastale residenza]                               nvarchar(4),
    [codice ISTAT residenza]                                   nvarchar(6) not null,
    [anno produzione redditi]                                  smallint    not null,
    [anno produzione patrimoni]                                smallint,
    sesso                                                      nvarchar    not null,
    [anno nascita]                                             int,
    [mese nascita]                                             int,
    [patrimonio mobiliare]                                     float,
    [patrimonio immobiliare residenza]                         float,
    [patrimonio immobiliare altri]                             float,
    [reddito autonomo]                                         float,
    [reddito dipendente]                                       float,
    [reddito da pensione]                                      float,
    [reddito da impresa agricola]                              float,
    [imposte nette]                                            decimal(38, 2),
    [Assegno regionale nucleo LR1-2005]                        decimal(38, 2),
    [Assistenza figli part-time]                               decimal(38, 2),
    [Reddito garanzia APAPI LP13-07-Art35comma2]               decimal(38, 2),
    [Previdenza complementare LR5-2009]                        decimal(38, 2),
    [Contributo famiglie numerose]                             decimal(38, 2),
    [Contributo sostegno potere acquisto nuclei]               decimal(38, 2),
    [Assegno al nucleo familiare L448-98)]                     decimal(38, 2),
    [Assegno maternita L448-98]                                decimal(38, 2),
    [Sussidi esenti IRPEF per studio]                          decimal(38, 2),
    [Reddito garanzia Servizi Sociali LP13-07-Art35comma2]     decimal(38, 2),
    [Anticipazione assegno mantenimento tutela minori LP14-91] decimal(38, 2),
    [Borse studio Opera Universitaria]                         decimal(38, 2),
    [Bonus Bebè]                                               decimal(38, 2),
    [Pensioni sociali o maggiorazione NON invalidita]          decimal(38, 2),
    [Intervento solidarieta sociale Comunita del Primiero]     decimal(38, 2),
    [Assegni periodici per mantenimento figli]                 decimal(38, 2),
    var6                                                       decimal(38, 2)
)
go

create table ISTAT_temp_estrazione_dich_2016
(
    [id individuo]                                                                    int         not null,
    [codice fiscale]                                                                  nchar(16)   not null,
    [codice catastale residenza]                                                      nvarchar(4),
    [codice ISTAT residenza]                                                          nvarchar(6) not null,
    [anno produzione redditi]                                                         smallint    not null,
    [anno produzione patrimoni]                                                       smallint,
    sesso                                                                             nvarchar    not null,
    [anno nascita]                                                                    int,
    [mese nascita]                                                                    int,
    [patrimonio mobiliare]                                                            float,
    [patrimonio immobiliare residenza]                                                float,
    [patrimonio immobiliare altri]                                                    float,
    [reddito autonomo]                                                                float,
    [reddito dipendente]                                                              float,
    [reddito da pensione]                                                             float,
    [reddito da impresa agricola]                                                     float,
    [imposte nette]                                                                   decimal(38, 2),
    [Assegno regionale al nucleo familiare (L.R.1/2005)]                              decimal(38, 2),
    [Assistenza figli part-time]                                                      decimal(38, 2),
    [Reddito di garanzia - APAPI - (L.P.13/07 Art.35 comma 2)]                        decimal(38, 2),
    [Previdenza complementare (L.R. 5-2009)]                                          decimal(38, 2),
    [Contributo famiglie numerose]                                                    decimal(38, 2),
    [Contributo a sostegno del potere d'acquisto dei nuclei familiari]                decimal(38, 2),
    [Assegno al nucleo familiare (L.448/98)]                                          decimal(38, 2),
    [Assegno di maternità (L.448/98)]                                                 decimal(38, 2),
    [Sussidi esenti IRPEF per finalità di studio (es. L.P.30/1978)]                   decimal(38, 2),
    [Reddito di garanzia - Servizi Sociali - (L.P.13/07 Art.35 comma 2)]              decimal(38, 2),
    [Anticipazione dell'assegno di mantenimento a tutela dei figli minori (LP 14/91)] decimal(38, 2),
    [Borse di studio erogate dall'Opera Universitaria]                                decimal(38, 2),
    [Bonus Bebè]                                                                      decimal(38, 2),
    [Intervento di solidarietà sociale della Comunità del Primiero]                   decimal(38, 2),
    [Assegni periodici per mantenimento figli]                                        decimal(38, 2),
    var6                                                                              decimal(38, 2)
)
go

create table ISTAT_temp_estrazione_dich_2017
(
    [id individuo]                                                                     int         not null,
    [codice fiscale]                                                                   nchar(16)   not null,
    [codice catastale residenza]                                                       nvarchar(4),
    [codice ISTAT residenza]                                                           nvarchar(6) not null,
    [anno produzione redditi]                                                          smallint    not null,
    [anno produzione patrimoni]                                                        smallint,
    sesso                                                                              nvarchar    not null,
    [anno nascita]                                                                     int,
    [mese nascita]                                                                     int,
    [patrimonio mobiliare]                                                             float,
    [patrimonio immobiliare residenza]                                                 float,
    [patrimonio immobiliare altri]                                                     float,
    [reddito autonomo]                                                                 float,
    [reddito dipendente]                                                               float,
    [reddito da pensione]                                                              float,
    [reddito da impresa agricola]                                                      float,
    [imposte nette]                                                                    decimal(38, 2),
    [Assegno regionale al nucleo familiare (L.R.1/2005)]                               decimal(38, 2),
    [Assistenza figli part-time]                                                       decimal(38, 2),
    [Reddito di garanzia - APAPI - (L.P.13/07 Art.35 comma 2)]                         decimal(38, 2),
    [Previdenza complementare (L.R. 5-2009)]                                           decimal(38, 2),
    [Contributo famiglie numerose]                                                     decimal(38, 2),
    [Contributo a sostegno del potere d''acquisto dei nuclei familiari]                decimal(38, 2),
    [Assegno al nucleo familiare (L.448/98)]                                           decimal(38, 2),
    [Assegno di maternità (L.448/98)]                                                  decimal(38, 2),
    [Sussidi esenti IRPEF per finalità di studio (es. L.P.30/1978)]                    decimal(38, 2),
    [Reddito di garanzia - Servizi Sociali - (L.P.13/07 Art.35 comma 2)]               decimal(38, 2),
    [Anticipazione dell''assegno di mantenimento a tutela dei figli minori (LP 14/91)] decimal(38, 2),
    [Borse di studio erogate dall''Opera Universitaria]                                decimal(38, 2),
    [Bonus Bebè]                                                                       decimal(38, 2),
    [Intervento di solidarietà sociale della Comunità del Primiero]                    decimal(38, 2),
    [Assegni periodici per mantenimento figli]                                         decimal(38, 2),
    var6                                                                               decimal(38, 2)
)
go

create table ISTAT_temp_estrazione_dom_2013
(
    anno                                     smallint      not null,
    [id domanda]                             int           not null,
    [codice fiscale richiedente]             nvarchar(16)  not null,
    [codice catastale residenza richiedente] nvarchar(4),
    [codice ISTAT residenza richiedente]     nvarchar(6)   not null,
    [descrizione politica]                   nvarchar(100) not null
)
go

create table ISTAT_temp_estrazione_dom_2014
(
    anno                                     smallint      not null,
    [id domanda]                             int           not null,
    [codice fiscale richiedente]             nvarchar(16)  not null,
    [codice catastale residenza richiedente] nvarchar(4),
    [codice ISTAT residenza richiedente]     nvarchar(6)   not null,
    [descrizione politica]                   nvarchar(100) not null
)
go

create table ISTAT_temp_estrazione_dom_2015
(
    anno                                     smallint      not null,
    [id domanda]                             int           not null,
    [codice fiscale richiedente]             nvarchar(16)  not null,
    [codice catastale residenza richiedente] nvarchar(4),
    [codice ISTAT residenza richiedente]     nvarchar(6)   not null,
    [descrizione politica]                   nvarchar(100) not null
)
go

create table ISTAT_temp_estrazione_dom_beneficio_2013
(
    anno                    smallint not null,
    [id domanda]            int      not null,
    [descrizione beneficio] nvarchar(200),
    [valore beneficio]      float
)
go

create table ISTAT_temp_estrazione_dom_beneficio_2014
(
    anno                    smallint not null,
    [id domanda]            int      not null,
    [descrizione beneficio] nvarchar(200),
    [valore beneficio]      float
)
go

create table ISTAT_temp_estrazione_dom_beneficio_2015
(
    anno                    smallint not null,
    [id domanda]            int      not null,
    [descrizione beneficio] nvarchar(200),
    [valore beneficio]      float
)
go

create table ISTAT_temp_estrazione_dom_individui_2013
(
    anno           smallint not null,
    [id domanda]   int      not null,
    [id individuo] int      not null
)
go

create table ISTAT_temp_estrazione_dom_individui_2014
(
    anno           smallint not null,
    [id domanda]   int      not null,
    [id individuo] int      not null
)
go

create table ISTAT_temp_estrazione_dom_individui_2015
(
    anno           smallint not null,
    [id domanda]   int      not null,
    [id individuo] int      not null
)
go

create table ISTAT_temp_redditi_pat_2013
(
    codice_fiscale  nvarchar(16)   not null,
    ente_inseritore nvarchar(70),
    beneficio       nvarchar(300),
    importo         decimal(12, 2) not null
)
go

create table ISTAT_temp_redditi_pat_2014
(
    codice_fiscale  nvarchar(16)   not null,
    ente_inseritore nvarchar(70),
    beneficio       nvarchar(300),
    importo         decimal(12, 2) not null
)
go

create table ISTAT_temp_redditi_pat_2015
(
    codice_fiscale  nvarchar(16) not null,
    ente_inseritore nvarchar(70),
    beneficio       nvarchar(300),
    importo         decimal(38, 2)
)
go

create table ISTAT_temp_redditi_pat_2016
(
    codice_fiscale  nvarchar(16) not null,
    ente_inseritore nvarchar(70),
    beneficio       nvarchar(300),
    importo         decimal(38, 2)
)
go

create table IS_tp_scelta_sociale_icef
(
    ID_tp_scelta_sociale_icef smallint      not null,
    ID_servizio               int           not null,
    tp_scelta                 nvarchar(200) not null,
    constraint PK_IS_tp_scelta_sociale_cef
        primary key (ID_tp_scelta_sociale_icef, ID_servizio)
            with (fillfactor = 95)
)
go

create table IVSDM_tp_impresa
(
    ID_tp_impresa int          not null
        constraint PK_IVSDM_tp_impresa
            primary key,
    tipo_impresa  nvarchar(50) not null
)
go

create table IVSDM_tp_regolamento
(
    ID_tp_reg   int          not null
        constraint PK_IVSDM_tp_regolamento
            primary key
                with (fillfactor = 95),
    regolamento nvarchar(50) not null
)
go

create table IVSDM_tp_settore
(
    ID_tp_settore int          not null
        constraint PK_IVSDM_tp_settore
            primary key,
    settore       nvarchar(50) not null
)
go

create table IVS_R_luoghi_zone_svantaggiate
(
    ID_luogo     nvarchar(6)   not null,
    ID_servizio  int           not null,
    ID_provincia nvarchar(2)   not null,
    luogo        nvarchar(250) not null,
    estinto      smallint      not null,
    ordine       smallint,
    constraint PK_IVS_R_luoghi_zone_svantaggiate_1
        primary key (ID_luogo, ID_servizio)
            with (fillfactor = 95)
)
go

create table IVS_R_zone_svantaggiate
(
    ID_zona_svantaggiata smallint not null,
    ID_servizio          int      not null,
    zona                 nvarchar(250),
    DATA_FINE_LR4        datetime,
    DATA_FINE_LR37       datetime,
    DATA_INIZIO_LR4      datetime,
    DATA_INIZIO_LR71     datetime,
    estinto              smallint not null,
    ID_luogo             nvarchar(6),
    luogo                nvarchar(250),
    constraint PK_IVS_R_zone_svantaggiate
        primary key (ID_zona_svantaggiata, ID_servizio)
            with (fillfactor = 95),
    constraint FK_IVS_R_zone_svantaggiate_IVS_R_luoghi_zone_svantaggiate
        foreign key (ID_luogo, ID_servizio) references IVS_R_luoghi_zone_svantaggiate
)
go

create table IVS_duplica_domanda
(
    ID_domanda_originale int not null,
    ID_domanda_duplicata int not null
)
go

create table IVS_sostituisci_soggetto
(
    ID_soggetto          int       not null,
    ID_soggetto_titolare int       not null,
    codFisc_titolare     nchar(16) not null,
    codFisc_unita        nchar(16) not null
)
go

create table IVS_tp_agricoltore
(
    ID_tp_agricoltore int           not null,
    ID_servizio       int           not null,
    agricoltore       nvarchar(250) not null,
    constraint PK_IVS_tp_agricoltore
        primary key (ID_tp_agricoltore, ID_servizio)
            with (fillfactor = 95)
)
go

create table IVS_tp_altitudini
(
    ID_altitudine int           not null,
    ID_servizio   int           not null,
    descrizione   nvarchar(150) not null,
    constraint PK_IVS_tp_altitudini
        primary key (ID_altitudine, ID_servizio)
            with (fillfactor = 95)
)
go

create table IVS_tp_eta
(
    ID_eta                               nvarchar(50) not null,
    anno                                 int          not null,
    eta_min                              smallint     not null,
    eta_max                              smallint     not null,
    mese_raggiungimento_eta_min_compreso smallint     not null,
    mese_raggiungimento_eta_max_compreso smallint     not null,
    ID_eta_senza_flag_riduzione          nvarchar(50),
    constraint PK_IVS_tp_eta
        primary key (ID_eta, anno)
            with (fillfactor = 95)
)
go

create table IVS_tp_fascie
(
    ID_fascia smallint     not null
        constraint PK_IVS_tp_fascie
            primary key
                with (fillfactor = 95),
    fascia    nvarchar(50) not null
)
go

create table IVS_R_contributi
(
    ID_fascia        smallint     not null
        constraint FK_IVS_R_contributi_IVS_tp_fascie
            references IVS_tp_fascie,
    ID_eta           nvarchar(50) not null,
    anno             int          not null,
    contributo_annuo float        not null,
    constraint PK_IVS_R_contributi
        primary key (ID_fascia, ID_eta, anno)
            with (fillfactor = 95),
    constraint FK_IVS_R_contributi_IVS_tp_eta
        foreign key (ID_eta, anno) references IVS_tp_eta
)
go

create table InfoTN_ARNF
(
    ID_servizio                 int           not null,
    servizio                    nvarchar(100) not null,
    AnnoServizio                nvarchar(4),
    ID_domanda                  int           not null,
    idCAAF                      int,
    CAAF                        nvarchar(70),
    UffCAAF                     nvarchar(50),
    codISTATlocUffCAAF          nvarchar(6),
    locUffCAAF                  nvarchar(50),
    idPatronato                 int,
    Patronato                   nvarchar(70),
    UfficioPatronato            nvarchar(50),
    codISTATlocUffPatronato     nvarchar(6),
    locUffPatronato             nvarchar(50),
    data_presentazione          varchar(10),
    icef                        float,
    richiede_anf                int,
    ANF                         float,
    mensile                     float,
    idTabella                   float,
    tabella                     varchar(17)   not null,
    riga                        float,
    colonna                     varchar(5),
    richiede_fnum               int,
    FNUM                        float,
    richiede_contr_sost_consumi int,
    idTipoPag                   smallint      not null,
    tipoPag                     nvarchar(120) not null,
    CFrich                      nvarchar(16)  not null,
    genererich                  nvarchar,
    datanascrich                datetime,
    siglacittadinanza           nvarchar(4)   not null,
    cittadinanza                nvarchar(50)  not null,
    siglagruppo                 nchar(2),
    gruppo                      nvarchar(12),
    codISTATComuRes             nvarchar(6)   not null,
    comuRes                     nvarchar(60)  not null
)
go

create table InfoTN_ARNF_2016
(
    ID_servizio                 int           not null,
    servizio                    nvarchar(100) not null,
    AnnoServizio                int,
    ID_domanda                  int           not null,
    idCAAF                      int,
    CAAF                        nvarchar(70),
    UffCAAF                     nvarchar(50),
    codISTATlocUffCAAF          nvarchar(6),
    locUffCAAF                  nvarchar(50),
    idPatronato                 int,
    Patronato                   nvarchar(70),
    UfficioPatronato            nvarchar(50),
    codISTATlocUffPatronato     nvarchar(6),
    locUffPatronato             nvarchar(50),
    data_presentazione          varchar(10),
    icef                        float,
    richiede_anf                int,
    ANF                         float,
    mensile                     float,
    idTabella                   int,
    tabella                     varchar(17)   not null,
    riga                        int,
    colonna                     varchar(15),
    richiede_fnum               int,
    FNUM                        float,
    richiede_contr_sost_consumi int,
    idTipoPag                   smallint      not null,
    tipoPag                     nvarchar(120) not null,
    CFrich                      nvarchar(16)  not null,
    genererich                  nvarchar,
    datanascrich                datetime,
    siglacittadinanza           nvarchar(4)   not null,
    cittadinanza                nvarchar(50)  not null,
    siglagruppo                 nchar(2),
    gruppo                      nvarchar(12),
    codISTATComuRes             nvarchar(6)   not null,
    comuRes                     nvarchar(60)  not null
)
go

create table InfoTN_ARNF_back
(
    ID_servizio                 int           not null,
    servizio                    nvarchar(100) not null,
    AnnoServizio                nvarchar(4),
    ID_domanda                  int           not null,
    idCAAF                      int,
    CAAF                        nvarchar(70),
    UffCAAF                     nvarchar(50),
    codISTATlocUffCAAF          nvarchar(6),
    locUffCAAF                  nvarchar(50),
    idPatronato                 int,
    Patronato                   nvarchar(70),
    UfficioPatronato            nvarchar(50),
    codISTATlocUffPatronato     nvarchar(6),
    locUffPatronato             nvarchar(50),
    data_presentazione          varchar(10),
    icef                        float,
    richiede_anf                int,
    ANF                         float,
    mensile                     float,
    idTabella                   float,
    tabella                     varchar(17)   not null,
    riga                        float,
    colonna                     varchar(5),
    richiede_fnum               int,
    FNUM                        float,
    richiede_contr_sost_consumi int,
    idTipoPag                   smallint      not null,
    tipoPag                     nvarchar(120) not null,
    CFrich                      nvarchar(16)  not null,
    genererich                  nvarchar,
    datanascrich                datetime,
    siglacittadinanza           nvarchar(4)   not null,
    cittadinanza                nvarchar(50)  not null,
    siglagruppo                 nchar(2),
    gruppo                      nvarchar(12),
    codISTATComuRes             nvarchar(6)   not null,
    comuRes                     nvarchar(60)  not null
)
go

create table InfoTN_AssUnico_quoteAnn
(
    data_estrazione_icef    datetime     not null,
    AnnoServizio            int,
    ID_domanda              int          not null,
    idCAAF                  varchar      not null,
    CAAF                    varchar      not null,
    UffCAAF                 varchar      not null,
    codISTATlocUffCAAF      varchar      not null,
    locUffCAAF              varchar      not null,
    idPatronato             int          not null,
    Patronato               nvarchar(70),
    UfficioPatronato        nvarchar(50),
    codISTATlocUffPatronato nvarchar(6),
    locUffPatronato         nvarchar(50),
    data_presentazione      datetime,
    flag_caso_sociale       int          not null,
    flag_tipo_nucleo        int          not null,
    A_tot_anno              float,
    A_codmodpag             smallint,
    B1lib_tot_anno          float,
    B1lib_codmodpag         smallint,
    B1vinc_tot_anno         float,
    B1vinc_codmodpag        smallint,
    B2_tot_anno             float,
    B2_codmodpag            smallint,
    B3_tot_anno             float,
    B3_codmodpag            smallint,
    CFrich                  nvarchar(16) not null,
    genererich              nvarchar,
    datanascrich            datetime,
    siglacittadinanza       nvarchar(4),
    cittadinanza            varchar(50)  not null,
    siglagruppo             varchar(2),
    gruppo                  varchar(25),
    codISTATComuRes         nvarchar(6)  not null,
    comuRes                 nvarchar(60) not null
)
go

create clustered index IX_InfoTN_AssUnico_quoteAnn
    on InfoTN_AssUnico_quoteAnn (data_estrazione_icef)
    with (fillfactor = 95)
go

create table InfoTN_FAM_GEA
(
    ID_domanda             int           not null,
    data_edizione          datetime,
    lastediz               int,
    ID_servizio            int           not null,
    servizio               nvarchar(100) not null,
    id_soggetto_principale int           not null,
    id_soggetto            int           not null,
    ID_relazione_parentela int           not null,
    parentela              nvarchar(70)  not null
)
go

create table InfoTN_cod_fisc
(
    id_soggetto    int          not null,
    codice_fiscale nvarchar(16) not null
)
go

create table InfoTN_corrispondenza_ICEF
(
    id_soggetto      int       not null,
    codice_fiscale   nchar(16) not null,
    Id_epu_icef      int,
    Id_soggetto_apss nvarchar(50)
)
go

create table InfoTN_fasce_ARNF
(
    Anno_rif   int,
    Dal        datetime,
    Al         datetime,
    id_fascia  int,
    fascia_min float,
    fascia_max float,
    A_2_1      float,
    A_2_2      float,
    A_2_3      float,
    A_2_4      float,
    A_2_5      float,
    A_2_6      float,
    A_2_7      float,
    A_2_8      float,
    A_2_9      float,
    A_2_10     float,
    A_2_11     float,
    A_2_12     float,
    A_2_13     float,
    A_2_14     float,
    A_2_15     float,
    B_1_1      float,
    B_1_2      float,
    B_1_3      float,
    B_1_4      float,
    B_1_5      float,
    B_1_6      float,
    B_1_7      float,
    B_1_8      float,
    B_1_9      float,
    B_1_10     float,
    B_1_11     float,
    B_1_12     float,
    B_1_13     float,
    B_1_14     float,
    B_1_15     float,
    C_1        float,
    C_2        float,
    C_3        float,
    C_4        float,
    C_5        float,
    C_6        float,
    C_7        float,
    C_8        float,
    C_9        float,
    C_10       float,
    C_11       float,
    C_12       float,
    C_13       float,
    C_14       float,
    C_15       float
)
go

create table InfoTN_soggetti
(
    id_soggetto    int not null,
    ultima_domanda int
)
go

create table InfoTN_soggetto_cittadinanza
(
    id_soggetto   int          not null,
    cittadinanza  nvarchar(50) not null,
    codice_enco   nvarchar(3),
    stato         nvarchar(60),
    cod_catastale nvarchar(4)  not null
)
go

create table InfoTN_soggetto_nascita
(
    id_soggetto                  int          not null,
    codice_fiscale               nchar(16)    not null,
    nome                         nvarchar(35) not null,
    cognome                      nvarchar(35) not null,
    cittadinanza                 nvarchar(50),
    provincia_nascita            nvarchar(2)  not null,
    comune_nascita               nvarchar(60) not null,
    comune_nascita_cod_catastale nvarchar(4),
    stato_nascita                nvarchar(60) not null,
    stato_nascita_cod_catastale  nvarchar(4),
    data_nascita                 datetime     not null,
    ID_tp_sex                    nvarchar     not null,
    data_decesso                 datetime,
    data_riferimento             datetime
)
go

create table InfoTN_soggetto_residenza
(
    id_soggetto                      int          not null,
    indirizzo_residenza              nvarchar(50) not null,
    n_civ_residenza                  nvarchar(10) not null,
    comune_residenza                 nvarchar(60) not null,
    cap_residenza                    nvarchar(10) not null,
    provincia_di_residenza           nvarchar(2)  not null,
    stato_di_residenza               nvarchar(60) not null,
    stato_di_residenza_cod_catastale nvarchar(4),
    data_riferimento                 datetime
)
go

create table L6_descrizione_normativa
(
    anno                  int            not null,
    descrizione_normativa nvarchar(4000) not null,
    giunta                nvarchar(500)  not null
)
go

create table L6_mesi
(
    ID_mese      smallint not null,
    anno         int      not null,
    ID_ente      int      not null,
    mese         nvarchar(50),
    definitivo   smallint,
    data_mandato datetime,
    constraint PK_L6_mesi
        primary key (ID_mese, anno, ID_ente)
            with (fillfactor = 95)
)
go

create table L6_Rendicontazione
(
    ID_domanda            int      not null,
    ID_mese               smallint not null,
    anno                  int      not null,
    ID_ente               int      not null,
    importo_mensile       float,
    compensazione_mensile float,
    giorni                int,
    sospesa               smallint,
    arretrati             smallint,
    note                  nvarchar(2000),
    codice_utente         nvarchar(50),
    compila               smallint,
    constraint PK_L6_Rendicontazione
        primary key (ID_domanda, ID_mese, anno, ID_ente)
            with (fillfactor = 95),
    constraint FK_L6_Rendicontazione_L6_mesi
        foreign key (ID_mese, anno, ID_ente) references L6_mesi
)
go

create table L6_profili_finali_vecchi
(
    id_assistito         int,
    CodiceFiscale        nvarchar(255),
    anno                 int,
    assegno              float,
    risultato            int,
    risultato_automatico int,
    Motivazione          ntext,
    Data                 datetime
)
go

create table L6_rendicontazione_periodi
(
    ID_domanda  int      not null,
    ID_mese     smallint not null,
    anno        int      not null,
    periodo     smallint not null,
    ID_ente     int,
    da          datetime,
    a           datetime,
    importo_gg  float,
    percentuale float,
    constraint PK_L6_rendicontazione_periodi
        primary key (ID_domanda, ID_mese, anno, periodo)
            with (fillfactor = 95),
    constraint FK_L6_rendicontazione_periodi_L6_Rendicontazione
        foreign key (ID_domanda, ID_mese, anno, ID_ente) references L6_Rendicontazione
)
go

create table L6_tp_fascia_assegno
(
    id_tp_fascia_assegno smallint    not null
        constraint PK_L6_tp_fascia_assegno
            primary key
                with (fillfactor = 95),
    tp_fascia_assegno    varchar(80) not null
)
go

create table L6_tp_gradi_parentela
(
    ID_tp_grado_parentela smallint      not null
        constraint PK_L6_tp_gradi_parentela
            primary key
                with (fillfactor = 95),
    tp_grado_parentela    nvarchar(150) not null
)
go

create table L6_tp_situazioni_dom
(
    ID_tp_situazione_dom smallint     not null
        constraint PK_L6_situazioni_dom
            primary key
                with (fillfactor = 95),
    tp_situazione_dom    nvarchar(20) not null
)
go

create table L6_tp_stati_accompagnamento
(
    ID_tp_stato_accompagnamento smallint      not null
        constraint PK_L6_tp_stati_accompagnamento
            primary key
                with (fillfactor = 95),
    tp_stato_accompagnamento    nvarchar(150) not null
)
go

create table L6_tp_variazioni_situazione_dom
(
    ID_tp_variazione_situazione_dom smallint     not null
        constraint PK_L6_tp_variazioni_situazione_dom
            primary key
                with (fillfactor = 95),
    tp_variazione_situazione_dom    nvarchar(20) not null
)
go

create table LAV_R_Comunita
(
    ID_servizio          int not null,
    ID_comunita_di_valle int not null,
    ID_group_comunita    int not null,
    group_comunita       nvarchar(255),
    constraint PK_LAV_R_Comunita_1
        primary key (ID_servizio, ID_comunita_di_valle, ID_group_comunita)
            with (fillfactor = 95)
)
go

create table LAV_R_Enti
(
    ID_servizio          int           not null,
    ID_lav_ente          int           not null,
    lav_ente             nvarchar(100) not null,
    luogo                nvarchar(6),
    ID_provincia         nvarchar(2),
    ID_comunita_di_valle int,
    ID_group_comunita    int,
    consorzio            int,
    constraint [PK_LAV_R_Enti+]
        primary key (ID_servizio, ID_lav_ente)
            with (fillfactor = 95)
)
go

create table LAV_graduatoria
(
    ID_domanda         int          not null,
    edizione           int          not null,
    ID_servizio        int          not null,
    ID_ente            int          not null,
    ID_group_comunita  int          not null,
    data_graduatoria   datetime     not null,
    posizione          nchar(10)    not null,
    enabled            smallint     not null,
    fascia_inps_scau   nvarchar(10) not null,
    fascia             nvarchar(10),
    fascia_A           smallint     not null,
    fascia_B           smallint     not null,
    fascia_C           smallint     not null,
    fascia_D           smallint     not null,
    p_inps             float        not null,
    p_scau             float        not null,
    punti_icef         float        not null,
    occupato           smallint,
    inidoneo_scau      smallint,
    posizione_relativa int          not null,
    constraint PK_LAV_graduatoria
        primary key (ID_domanda, edizione, ID_servizio, ID_ente, ID_group_comunita, data_graduatoria, posizione,
                     fascia_inps_scau, posizione_relativa)
            with (fillfactor = 95)
)
go

create table LAV_istruttoria_graduatoria
(
    ID_istruttoria           int not null,
    ID_servizio              int not null,
    ID_lav_ente              int not null,
    ID_istruttoria_richiesta int not null,
    ID_istruttoria_risposta  int not null,
    ID_group_comunita        int,
    ID_consorzio             int,
    ID_tp_cooperativa        int,
    data_graduatoria         datetime,
    protocollo               nvarchar(10),
    data_protocollo          datetime,
    n_persone_richieste      int,
    prot_rich_consorzio      nvarchar(10),
    data_prot_rich_consorzio datetime,
    prot_risp_consorzio      nvarchar(10),
    data_prot_risp_consorzio datetime,
    note                     nvarchar(250),
    sostituzione             smallint,
    ID_ente                  int not null,
    ID_user                  int not null,
    constraint PK_LAV_istruttoria_graduatoria
        primary key (ID_istruttoria, ID_servizio, ID_lav_ente, ID_istruttoria_richiesta, ID_istruttoria_risposta,
                     ID_ente)
)
go

create table LAV_temp_occupazioni
(
    id_occupazione_temp int          not null,
    cognome             nvarchar(50),
    nome                nvarchar(50),
    data_nascita        datetime,
    codice_fiscale      nvarchar(16) not null,
    data_inizio         datetime,
    data_fine           datetime,
    cooperativa         nvarchar(50),
    consorzio           nvarchar(50),
    tipo                nvarchar(50),
    luogo               nvarchar(50),
    caposquadra         nchar(10),
    id_cooperativa      int,
    id_consorzio        int,
    id_luogo            nchar(10),
    id_group_comunita   int,
    id_occ              int,
    note                nvarchar(250)
)
go

create table LAV_tp_cooperative
(
    ID_servizio       int           not null,
    ID_consorzio      int           not null,
    ID_tp_cooperativa int           not null,
    tp_cooperativa    nvarchar(150) not null,
    indirizzo         nvarchar(150),
    ID_provincia      nvarchar(2),
    ID_comune         nvarchar(6),
    id_code_rif       int,
    constraint PK_LAV_tp_cooperative
        primary key (ID_servizio, ID_consorzio, ID_tp_cooperativa)
            with (fillfactor = 95)
)
go

create table LAV_tp_mansioni
(
    ID_servizio     int           not null,
    ID_tp_mansione  int           not null,
    tp_mansione     nvarchar(150) not null,
    isProgettone    smallint      not null,
    inps_scau       varchar(10),
    gruppo_mansione nvarchar(50),
    constraint PK_LAV_tp_mansioni
        primary key (ID_servizio, ID_tp_mansione)
            with (fillfactor = 95)
)
go

create table LAV_occupazioni
(
    codice_fiscale    nvarchar(16)  not null,
    ID_attivita       int           not null,
    anno              int           not null,
    ID_servizio       int,
    ID_group_comunita int           not null,
    ID_consorzio      int,
    ID_tp_cooperativa int           not null,
    ID_tp_mansione    int,
    INPS_SCAU         nvarchar(10)  not null,
    data_dal          datetime      not null,
    data_al           datetime      not null,
    data_presunta_al  datetime,
    caposquadra       int,
    confermato        smallint,
    ditta             nvarchar(250) not null,
    note              nvarchar(250) not null,
    import_dati       datetime,
    verifica_apss     int,
    new_cf            nvarchar(16),
    id_code_rif       int,
    constraint PK_LAV_occupazioni
        primary key (codice_fiscale, ID_attivita, anno)
            with (fillfactor = 95),
    constraint FK_LAV_occupazioni_LAV_occupazioni
        foreign key (ID_servizio, ID_tp_mansione) references LAV_tp_mansioni,
    constraint FK_LAV_occupazioni_LAV_tp_cooperative
        foreign key (ID_servizio, ID_consorzio, ID_tp_cooperativa) references LAV_tp_cooperative,
    constraint FK_LAV_occupazioni_LAV_tp_mansioni
        foreign key (ID_servizio, ID_tp_mansione) references LAV_tp_mansioni
)
go

create table LAV_stagionali
(
    ID_domanda        int          not null,
    ID_attivita       int          not null,
    ID_tp_cooperativa int          not null,
    ID_servizio       int          not null,
    ID_tp_mansione    int          not null,
    tp_lavoro         nvarchar(10) not null,
    data_dal          datetime     not null,
    data_al           datetime     not null,
    ditta             nvarchar(250),
    note              nvarchar(250),
    ID_consorzio      int,
    constraint PK_LAV_stagionali
        primary key (ID_domanda, ID_attivita)
            with (fillfactor = 95),
    constraint FK_LAV_stagionali_LAV_tp_mansioni
        foreign key (ID_servizio, ID_tp_mansione) references LAV_tp_mansioni
)
go

create table LAV_tp_motivi
(
    ID_tp_motivo    int          not null,
    ID_servizio     int          not null,
    tp_motivo       nvarchar(50) not null,
    ID_super_motivo int,
    super_motivo    nvarchar(50),
    in_graduatoria  int,
    escluso         int,
    constraint PK_LAV_tp_esclusioni
        primary key (ID_tp_motivo, ID_servizio)
            with (fillfactor = 95)
)
go

create table LAV_istruttoria_domande
(
    ID_istruttoria              int not null,
    ID_servizio                 int not null,
    ID_lav_ente                 int not null,
    ID_domanda                  int not null,
    ID_istruttoria_richiesta    int not null,
    ID_istruttoria_risposta     int not null,
    ID_group_comunita           int,
    ID_consorzio                int,
    ID_tp_cooperativa           int,
    inquadramento               nvarchar(10),
    assegnazione_autorizzazione smallint,
    protocollo_assegnazione     nvarchar(10),
    data_assegnazione           datetime,
    data_assunzione             datetime,
    data_scadenza_presunta      datetime,
    data_contratto_scaduto      datetime,
    rientro_in_graduatoria      smallint,
    id_tp_motivo_rifiuto_fase3  int,
    id_tp_motivo_rifiuto_fase4  int,
    id_tp_motivo_rifiuto_fase5  int,
    ID_tp_motivo_fine           int,
    motivazione_fine            nvarchar(150),
    note                        nvarchar(250),
    ID_ente                     int not null,
    ID_user                     int,
    numero_attività             int not null,
    constraint PK_LAV_istruttoria_domande
        primary key (ID_istruttoria, ID_servizio, ID_lav_ente, ID_domanda, ID_istruttoria_richiesta,
                     ID_istruttoria_risposta, ID_ente)
            with (fillfactor = 95),
    constraint FK_LAV_istruttoria_domande_LAV_R_Enti
        foreign key (ID_servizio, ID_lav_ente) references LAV_R_Enti,
    constraint FK_LAV_istruttoria_domande_LAV_istruttoria_domande
        foreign key (ID_istruttoria, ID_servizio, ID_lav_ente, ID_domanda, ID_istruttoria_richiesta,
                     ID_istruttoria_risposta, ID_ente) references LAV_istruttoria_domande,
    constraint FK_LAV_istruttoria_domande_LAV_tp_cooperative
        foreign key (ID_servizio, ID_consorzio, ID_tp_cooperativa) references LAV_tp_cooperative,
    constraint FK_LAV_istruttoria_domande_LAV_tp_motivi
        foreign key (id_tp_motivo_rifiuto_fase3, ID_servizio) references LAV_tp_motivi,
    constraint FK_LAV_istruttoria_domande_LAV_tp_motivi1
        foreign key (id_tp_motivo_rifiuto_fase4, ID_servizio) references LAV_tp_motivi,
    constraint FK_LAV_istruttoria_domande_LAV_tp_motivi2
        foreign key (id_tp_motivo_rifiuto_fase5, ID_servizio) references LAV_tp_motivi
)
go

create table LAV_tp_stati_upload
(
    ID_tp_stato_upload smallint     not null
        constraint LAV_tp_stati_upload_PK
            primary key,
    tp_stato_upload    nvarchar(50) not null
)
go

create table LastId
(
    ID nvarchar(50) not null
        constraint PK_LastId
            primary key nonclustered
                with (fillfactor = 95)
)
go

create table Locked
(
    hashCode      nvarchar(50) not null,
    entryKeyValue nvarchar(50) not null,
    locktime      datetime,
    ID_user       int          not null,
    constraint PK_Locked
        primary key nonclustered (hashCode, entryKeyValue)
            with (fillfactor = 95)
)
go

create table MUOVERSI_km_assegnati
(
    anno             smallint     not null,
    codice_fiscale   nvarchar(16) not null,
    km_assegnati     int          not null,
    assegno_cura     bit          not null,
    reddito_garanzia bit          not null,
    constraint PK_MUOVERSI_km_assegnati
        primary key (anno, codice_fiscale)
            with (fillfactor = 90)
)
go

create table MUOVERSI_tp_relazione
(
    ID_tp_relazione smallint not null
        constraint PK_MUOVERSI_tp_relazione
            primary key
                with (fillfactor = 95),
    tp_relazione    nvarchar(250)
)
go

create table Minimo_vitale
(
    codice_fiscale nvarchar(16) not null
        constraint PK_Minimo_vitale
            primary key
                with (fillfactor = 95),
    data_scadenza  datetime,
    ID_ente        smallint
)
go

create table ModPag_crc
(
    ID_servizio int           not null,
    ID_periodo  int           not null,
    posizione   int           not null,
    tabella     nvarchar(100) not null,
    colonna     nvarchar(100) not null,
    order_by    smallint      not null,
    constraint PK_ModPag_crc
        primary key (ID_servizio, ID_periodo, posizione)
            with (fillfactor = 95)
)
go

create table ModPag_disabled
(
    disabled bit
)
go

create table NM_Eventi_Fallback
(
    ID_Evento      int identity
        constraint PK_NM_Eventi_Fallback
            primary key,
    ID_tp_evento   smallint not null,
    data           datetime not null,
    data_notifica  datetime,
    messaggio      xml      not null,
    nrTentativo    smallint,
    data_tentativo datetime,
    errori         ntext
)
go

create index NM_Eventi_Fallback_Messaggio_PrimaryXmlIndex
    on NM_Eventi_Fallback (messaggio)
go

create index NM_Eventi_Fallback_Messaggio_SecondaryXmlIndex_Path
    on NM_Eventi_Fallback (messaggio)
go

create index NM_Eventi_Fallback_Messaggio_SecondaryXmlIndex_Property
    on NM_Eventi_Fallback (messaggio)
go

create index NM_Eventi_Fallback_Messaggio_SecondaryXmlIndex_Value
    on NM_Eventi_Fallback (messaggio)
go

create table NM_lock
(
    uuid      varchar(32) not null
        constraint pk_NM_lock
            primary key
                with (fillfactor = 95),
    id        varchar(80) not null
        constraint id_NM_lock
            unique
                with (fillfactor = 95),
    locked_ts datetime    not null
)
go

create table NM_tp_consumer
(
    id_tp_consumer smallint not null
        constraint PK_NM_tp_consumer
            primary key
                with (fillfactor = 95),
    nome           nvarchar(255),
    descrizione    nvarchar(255),
    configurazione xml
)
go

create table NM_tp_evento
(
    id_tp_evento    smallint not null
        constraint PK_NM_tp_evento
            primary key
                with (fillfactor = 95),
    nome            nvarchar(255),
    descrizione     nvarchar(max),
    usersFilter     smallint not null,
    deleteAfterDays smallint
)
go

create table NM_Eventi
(
    ID_Evento       int identity
        constraint PK_NM_Eventi
            primary key
                with (fillfactor = 95),
    ID_tp_evento    smallint not null
        constraint FK_NM_Eventi_NM_tp_evento
            references NM_tp_evento,
    data            datetime not null,
    data_notifica   datetime,
    messaggio       xml,
    allegati        text,
    testo           text,
    mittente        nvarchar(500),
    destinatari     nvarchar(max),
    oggetto         nvarchar(max),
    destinatari_to  nvarchar(max),
    destinatari_cc  nvarchar(max),
    destinatari_bcc nvarchar(max),
    severity        nvarchar(50)
)
go

create table NM_tp_notifica
(
    id_tp_notifica smallint      not null
        constraint PK_NM_tp_notifica
            primary key
                with (fillfactor = 95),
    nome           nvarchar(255) not null,
    descrizione    nvarchar(max),
    notifier_class nvarchar(255) not null,
    configurazione xml,
    id_tp_consumer smallint      not null
        constraint FK_NM_tp_notifica_NM_tp_consumer
            references NM_tp_consumer
)
go

create table NM_Notifiche
(
    ID_Notifica    int identity
        constraint PK_NM_Notifiche
            primary key
                with (fillfactor = 95),
    ID_Evento      int      not null
        constraint FK_NM_Notifiche_NM_Eventi
            references NM_Eventi,
    ID_tp_notifica smallint not null
        constraint FK_NM_Notifiche_NM_tp_notifica
            references NM_tp_notifica,
    data           datetime,
    data_notifica  datetime,
    data_evasione  datetime,
    nrTentativo    smallint,
    note           nvarchar(max),
    inUso          smallint
)
go

create index IDX_NM_Notifiche_dataevasione
    on NM_Notifiche (data_evasione) include (ID_Evento, inUso)
    with (fillfactor = 95)
go

create table NM_tp_evento_notifica
(
    ID_tp_evento   smallint not null
        constraint FK_NM_tp_evento_notifica_NM_tp_evento
            references NM_tp_evento,
    ID_tp_notifica smallint not null
        constraint FK_NM_tp_evento_notifica_NM_tp_notifica
            references NM_tp_notifica,
    constraint PK_NM_tp_evento_notifica
        primary key (ID_tp_evento, ID_tp_notifica)
            with (fillfactor = 95)
)
go

create table NO_icef_epu
(
    IDCATEGORIA         nvarchar(50),
    CATEGORIAECONOMICA  nvarchar(50),
    TICO                smallint,
    SITUAZIONEGIURIDICA nvarchar(50),
    IDENTIFICATIVO      nvarchar(50),
    IDENTE              smallint,
    CODICEFISCALE       nvarchar(16),
    COGNOME             nvarchar(50),
    NOME                nvarchar(50),
    DATAFINE            nvarchar(50)
)
go

create table NewsAnni
(
    anno             int not null,
    ID_servizio      int not null,
    ID_superservizio int not null
        constraint FK_NewsAnni_Activator_Superservizi
            references Activator_Superservizi,
    enabled          int not null,
    constraint PK_NewsAnni
        primary key (anno, ID_servizio)
            with (fillfactor = 95)
)
go

create table News
(
    ID_news     int            not null
        constraint PK_News
            primary key
                with (fillfactor = 95),
    anno        int            not null,
    ID_servizio int            not null,
    commento    nvarchar(2000) not null,
    link        nvarchar(500),
    enabled     int            not null,
    constraint FK_News_NewsAnni
        foreign key (anno, ID_servizio) references NewsAnni
)
go

create table NormativaAnni
(
    anno             int not null,
    ID_servizio      int not null,
    ID_superservizio int not null
        constraint FK_NormativaAnni_Activator_Superservizi
            references Activator_Superservizi,
    enabled          int not null,
    constraint PK_NormativaAnni
        primary key (anno, ID_servizio)
            with (fillfactor = 95)
)
go

create table Normativa
(
    ID_documenti int            not null
        constraint PK_Normativa
            primary key
                with (fillfactor = 95),
    anno         int            not null,
    ID_servizio  int            not null,
    posizione    int            not null,
    intestazione nvarchar(10)   not null,
    testo        nvarchar(90)   not null,
    commento     nvarchar(2000) not null,
    link         nvarchar(500)  not null,
    enabled      int            not null,
    constraint FK_Normativa_NormativaAnni
        foreign key (anno, ID_servizio) references NormativaAnni
)
go

create table NucleiAutonomi
(
    ID_domanda   int not null
        constraint PK_NucleiAutonomi
            primary key
                with (fillfactor = 95),
    adulto       smallint,
    orfano       smallint,
    autonomo     smallint,
    nuovo_nucleo smallint
)
go

create table OR_PROPERTIES
(
    PROPERTY_ID    int identity
        primary key
            with (fillfactor = 95),
    PROPERTY_KEY   varchar(255) not null collate Latin1_General_CI_AS
        unique
            with (fillfactor = 95),
    PROPERTY_VALUE varchar(255) collate Latin1_General_CI_AS
)
go

create table Odo_listino
(
    id_odo_listino int           not null
        constraint PK_Odo_listino
            primary key
                with (fillfactor = 95),
    id_servizio    int           not null,
    codice         nchar(10)     not null,
    descrizione    nvarchar(300) not null,
    tariffa        decimal(9, 2) not null,
    enabled        smallint      not null
)
go

create table PAT_IC_IA
(
    anno           smallint     not null,
    codice_fiscale nvarchar(16) not null,
    constraint PK_PAT_IC_IA
        primary key (anno, codice_fiscale)
            with (fillfactor = 95)
)
go

create table PAT_detrazioni
(
    anno              smallint       not null,
    codice_fiscale    nvarchar(16)   not null,
    ID_tp_detrazione  char(3)        not null,
    ID_ente_fornitore int            not null,
    importo           decimal(12, 2) not null,
    constraint PK_PAT_detrazioni
        primary key (anno, codice_fiscale, ID_tp_detrazione, ID_ente_fornitore)
)
go

create table PAT_erogazioni_removed
(
    anno              smallint       not null,
    codice_fiscale    nvarchar(16)   not null,
    ID_tp_erogazione  char(3)        not null,
    ID_ente_fornitore int            not null,
    importo           decimal(12, 2) not null
)
go

create table PAT_tp_erogazioni
(
    anno             smallint not null,
    ID_tp_erogazione char(3)  not null,
    tp_erogazione    nvarchar(300),
    ordine           smallint,
    codificaPAT      int,
    constraint PK_PAT_tp_erogazioni
        primary key (ID_tp_erogazione, anno)
            with (fillfactor = 90)
)
go

create table PAT_erogazioni
(
    anno              smallint       not null,
    codice_fiscale    nvarchar(16)   not null,
    ID_tp_erogazione  char(3)        not null,
    ID_ente_fornitore int            not null,
    importo           decimal(12, 2) not null,
    constraint PK_PAT_erogazioni
        primary key (anno, codice_fiscale, ID_tp_erogazione, ID_ente_fornitore)
            with (fillfactor = 90),
    constraint FK_PAT_erogazioni_PAT_tp_erogazioni
        foreign key (ID_tp_erogazione, anno) references PAT_tp_erogazioni
)
go

create index [NonClusteredIndex-20181026-154753]
    on PAT_erogazioni (codice_fiscale)
go

create index [NonClusteredIndex-20181026-155410]
    on PAT_erogazioni (anno)
go

create table PENSPLAN_dati
(
    id_domanda                           int          not null
        constraint PK_PENSPLAN_dati
            primary key,
    id_codiceFondo                       int,
    n_marca_bollo                        nvarchar(14) not null,
    codice_operatore                     nvarchar(20) not null,
    descrizione_operatore                nvarchar(50) not null,
    lingua_cittadino                     nchar(2),
    lingua_utente                        nchar(2),
    vsef                                 nvarchar(10),
    data_vsef                            datetime,
    codice_fiscale_anomalo               nvarchar(16),
    data_iscrizione                      datetime,
    indicatore                           float,
    iscrizione_ok                        int,
    residenza_ok                         int,
    id_codiceFondoPensplan               nvarchar(50),
    scansione_dom_def                    smallint,
    presenza_marca_bollo                 smallint,
    firma_richiesta                      smallint,
    firma_mandato_assistenza             smallint,
    firma_timbro_infopoint               smallint,
    scansione_ec_inps                    smallint,
    scansione_doc_id                     smallint,
    scansione_vse                        smallint,
    iscrizione_fondo_pens                smallint,
    data_invio_mail                      datetime,
    data_riscontro                       datetime,
    iban                                 nvarchar(30),
    descrizione_fondo_pens               nvarchar(50),
    iscrizione2anni                      smallint,
    data_iscrizione_2                    datetime,
    situazione_economica                 smallint,
    periodo_difficolta_coincidente_inps  smallint,
    ID_tp_stato_esito                    smallint,
    motivo_sospensione                   nvarchar(300),
    data_invio_lettera_sospensione       datetime,
    motivo_rigetto                       nvarchar(300),
    data_invio_lettera_preavviso_rigetto datetime,
    data_integrazione                    datetime,
    data_invio_lettera_rigetto           datetime,
    data_accoglimento                    datetime,
    valutazione                          int,
    motivo_valutazione_negativa          nvarchar(500),
    operatore_istr                       nvarchar(50),
    operatore_contr                      nvarchar(50),
    motivo_preavviso_rigetto             nvarchar(300),
    data_integrazione_preavviso          datetime,
    conflitto_interessi                  smallint
)
go

create table PENSPLAN_fondi
(
    id_servizio    int           not null,
    id_codiceFondo int           not null,
    covip          int           not null,
    nomeFondo      nvarchar(255) not null,
    nomeBreve      nvarchar(150) not null,
    ordinamento    int,
    constraint PK_PENSPLAN_fondi
        primary key (id_servizio, id_codiceFondo)
)
go

create table PENSPLAN_gestione_esiti
(
    ID_domanda                           int      not null
        primary key,
    scansione_dom_def                    smallint not null,
    presenza_marca_bollo                 smallint not null,
    firma_richiesta                      smallint not null,
    firma_mandato_assistenza             smallint not null,
    firma_timbro_infopoint               smallint not null,
    scansione_ec_inps                    smallint not null,
    scansione_doc_id                     smallint not null,
    scansione_vse                        smallint not null,
    iscrizione_fondo_pens                smallint not null,
    data_invio_mail                      datetime,
    data_riscontro                       datetime,
    iban                                 nvarchar(20),
    descrizione_fondo_pens               nvarchar(50),
    iscrizione2anni                      smallint not null,
    data_iscrizione                      datetime,
    situazione_economica                 smallint not null,
    periodo_difficolta_coincidente_inps  smallint not null,
    ID_tp_stato_esito                    int      not null,
    motivo_sospensione                   nvarchar(50),
    data_invio_lettera_sospensione       datetime,
    motivo_rigetto                       nvarchar(250),
    data_invio_lettera_preavviso_rigetto datetime,
    data_integrazione                    datetime,
    data_invio_lettera_rigetto           datetime,
    data_accoglimento                    datetime,
    valutazione                          int      not null,
    motivo_valutazione_negativa          nvarchar(250),
    operatore_istr                       nvarchar(50),
    operatore_contr                      nvarchar(50),
    conflitto_interessi                  smallint
)
go

create table PENSPLAN_periodi
(
    anno            int,
    codice_fiscale  varchar(16),
    id_domanda      int,
    n_pratica       int,
    data_inizio     datetime,
    data_fine       datetime,
    giorni          int,
    mesi            int,
    settimane       int,
    tipo_provv      varchar(50),
    tipo_int        varchar(50),
    stato           varchar(50),
    n_aderente      int,
    stato_aderente  varchar(50),
    data_cessazione datetime,
    numero_aderente int
)
go

create table PENSPLAN_periodi_2017
(
    codice_fiscale           nchar(16) not null,
    data_inizio              datetime  not null,
    data_fine                datetime  not null,
    tipo_provenienza         nchar(50),
    tipo_intervento          nchar(50),
    stato_pratica            nchar(50),
    numero_aderente          int,
    stato_aderente           nchar(50),
    data_cessazione_aderente datetime,
    codice_fiscale_pensplan  nchar(16),
    constraint PK_PENSPLAN_periodi
        primary key (codice_fiscale, data_inizio, data_fine)
            with (fillfactor = 95)
)
go

create table PENSPLAN_tp_difficolta
(
    id_tp_difficolta int           not null,
    id_servizio      int           not null,
    tp_difficolta    nvarchar(100) not null,
    tp_diff          nvarchar(100),
    constraint PK_PENSPLAN_tp_difficolta
        primary key (id_tp_difficolta, id_servizio)
            with (fillfactor = 95)
)
go

create table PENSPLAN_tp_stato_esiti
(
    ID_servizio       int           not null,
    ID_tp_stato_esito int           not null,
    tp_stato_esito    nvarchar(250) not null,
    stati_edges       nvarchar(250) not null,
    primary key (ID_servizio, ID_tp_stato_esito)
)
go

create table PENSPLAN_trasmissioni
(
    id_domanda                   int          not null,
    edizione_doc                 int          not null,
    data_presentazione           datetime,
    data_edizione                datetime     not null,
    cognome                      nvarchar(35),
    nome                         nvarchar(35),
    codice_fiscale               nvarchar(16) not null,
    ID_tp_sex                    nvarchar,
    data_nascita                 datetime,
    luogo_nascita                nvarchar(60) not null,
    indirizzo_residenza          nvarchar(61) not null,
    cap_residenza                nvarchar(10) not null,
    frazione_residenza           nvarchar(50),
    comune_residenza             nvarchar(4),
    indirizzo_domicilio          nvarchar(61),
    cap_domicilio                nvarchar(10),
    frazione_domicilio           nvarchar(50),
    comune_domicilio             nvarchar(4),
    lingua_richiedente           nchar(3),
    telefono                     nvarchar(20),
    cellulare                    nvarchar(50),
    e_mail                       nvarchar(50),
    pec                          nvarchar(50),
    n_marca_bollo                nvarchar(14) not null,
    situazione_economica         float,
    data_condizione_economica    datetime,
    n_attestazione               nvarchar(10),
    codiceFondo                  int          not null,
    tipo_richiesta               varchar      not null,
    tipo_provvidenza             varchar(3)   not null,
    tp_diff1                     nvarchar(100),
    tp_diff2                     nvarchar(100),
    tp_diff3                     nvarchar(100),
    tp_diff4                     nvarchar(100),
    tp_diff5                     nvarchar(100),
    data_dal1                    datetime,
    data_al1                     datetime,
    data_dal2                    datetime,
    data_al2                     datetime,
    data_dal3                    datetime,
    data_al3                     datetime,
    data_dal4                    datetime,
    data_al4                     datetime,
    data_dal5                    datetime,
    data_al5                     datetime,
    n_giornate_indennizzate      int,
    n_giornate_astensione_totale int,
    patronato                    nvarchar(15),
    codice_operatore             nvarchar(20) not null,
    idonea                       float,
    checkout                     float,
    stato_invio                  int          not null,
    codice_fiscale_anomalo       nvarchar(16),
    data_dal6                    datetime,
    data_al6                     datetime,
    data_dal7                    datetime,
    data_al7                     datetime,
    data_dal8                    datetime,
    data_al8                     datetime,
    data_dal9                    datetime,
    data_al9                     datetime,
    data_dal10                   datetime,
    data_al10                    datetime,
    data_dal11                   datetime,
    data_al11                    datetime,
    data_dal12                   datetime,
    data_al12                    datetime,
    data_dal13                   datetime,
    data_al13                    datetime,
    data_dal14                   datetime,
    data_al14                    datetime,
    data_dal15                   datetime,
    data_al15                    datetime,
    data_dal16                   datetime,
    data_al16                    datetime,
    data_dal17                   datetime,
    data_al17                    datetime,
    data_dal18                   datetime,
    data_al18                    datetime,
    data_dal19                   datetime,
    data_al19                    datetime,
    data_dal20                   datetime,
    data_al20                    datetime,
    data_dal21                   datetime,
    data_al21                    datetime,
    data_dal22                   datetime,
    data_al22                    datetime,
    data_dal23                   datetime,
    data_al23                    datetime,
    data_dal24                   datetime,
    data_al24                    datetime,
    data_dal25                   datetime,
    data_al25                    datetime,
    data_dal26                   datetime,
    data_al26                    datetime,
    data_dal27                   datetime,
    data_al27                    datetime,
    data_dal28                   datetime,
    data_al28                    datetime,
    data_dal29                   datetime,
    data_al29                    datetime,
    data_dal30                   datetime,
    data_al30                    datetime,
    ID_tp_stato_esito            nvarchar(2),
    motivo_sospensione           nvarchar(300),
    motivo_preavviso_rigetto     nvarchar(300),
    motivo_rigetto               nvarchar(300),
    valutazione                  nvarchar,
    motivo_valutazione_negativa  nvarchar(500)
)
go

create table PNS_lavdisc_versamenti
(
    ID_domanda   int          not null
        constraint PK_PNS_lavdisc_versamenti
            primary key
                with (fillfactor = 95),
    Ente_Prev_E1 nvarchar(50) not null,
    importo_E1   float        not null,
    Ente_Prev_E2 nvarchar(50) not null,
    importo_E2   nvarchar(50) not null,
    anno         smallint     not null
)
go

create table PNS_tp_anni
(
    ID_servizio smallint not null,
    anno        int      not null,
    constraint PK_PNS_tp_anni
        primary key (ID_servizio, anno)
            with (fillfactor = 95)
)
go

create table PNS_tp_categorie
(
    ID_tp_categoria smallint not null
        constraint PK_PNS_tp_categorie
            primary key
                with (fillfactor = 95),
    categoria       nvarchar(60)
)
go

create table PNS_tp_contribuente
(
    ID_contribuente smallint not null
        constraint PK_PNS_tp_contribuente
            primary key
                with (fillfactor = 95),
    contribuente    nvarchar(50)
)
go

create table PNS_tp_occupazioni
(
    ID_tp_occupazione smallint not null
        constraint PK_PNS_tp_occupazioni
            primary key
                with (fillfactor = 95),
    tp_occupazione    nvarchar(255)
)
go

create table PNS_tp_periodi
(
    ID_tp_periodo smallint not null
        constraint PK_PNS_tp_periodi
            primary key
                with (fillfactor = 95),
    ID_servizio   int      not null,
    periodo       nvarchar(255)
)
go

create table PNS_tp_riferimenti
(
    ID_riferimento smallint not null
        constraint PK_PNS_tp_riferimenti
            primary key
                with (fillfactor = 90),
    ID_servizio    int      not null,
    riferimento    nvarchar(50)
)
go

create table PROTO_Pitre_Cancellati
(
    blocco      int not null,
    ID_PiTre    int not null,
    ID_Doc      int not null,
    ID_Edizione int,
    Trasmesso   bit,
    Proto_Num   nvarchar(50),
    PRoto_Data  datetime,
    constraint PK_PROTO_Pitre_Cancellati
        primary key (ID_PiTre, ID_Doc, blocco)
            with (fillfactor = 95)
)
go

create table PROTO_Pitre_clesiusInviiForzati
(
    ID_doc           int      not null,
    edizione_doc     int      not null,
    blocco           int      not null,
    data_inserimento datetime not null,
    note             nvarchar(max),
    constraint PK_PROTO_Pitre_InviiForzati
        primary key (ID_doc, edizione_doc)
            with (fillfactor = 95)
)
go

create table PROTO_Pitre_protocolli
(
    ID_pitre       int not null
        constraint PK_Pitre
            primary key
                with (fillfactor = 95),
    ProtoTrasmesso bit,
    ProtoNum       nvarchar(255),
    ProtoData      datetime,
    Request_Xml    xml,
    Response_Xml   xml
)
go

create table PROTO_Pitre_tp_files
(
    ID_tp_file  smallint not null
        constraint PK_PROTO_Pitre_tp_Files
            primary key
                with (fillfactor = 95),
    Descrizione nvarchar(255)
)
go

create table PROTO_Pitre_files
(
    ID_pitre_document int           not null,
    ID_tp_file        smallint      not null
        constraint FK_PROTO_Pitre_Files_PROTO_Pitre_tp_Files
            references PROTO_Pitre_tp_files,
    ID_pitre_file     int,
    Description       nvarchar(255),
    Filename          nvarchar(255) not null,
    Mimetype          nvarchar(255) not null,
    Version           smallint,
    signature         bit           not null,
    hashing           nvarchar(max),
    constraint PK_Proto_Pitre_attachments
        primary key (ID_pitre_document, ID_tp_file)
)
go

create table PROVA_VCS_associazioni
(
    ID_associazione   int identity (0, 1)
        constraint PROVA_VCS_associazioni_PK
            primary key,
    nome_associazione nvarchar(250),
    sede              nvarchar(250)
)
go

create table PRUNI_tp_studente
(
    ID_tp_stud_parauni int           not null
        constraint PK_PRUNI_tp_studente
            primary key
                with (fillfactor = 95),
    tp_stud_parauni    nvarchar(150) not null
)
go

create table PRUNI_studente
(
    ID_domanda           int      not null
        constraint PK_PRUNI_studente
            primary key
                with (fillfactor = 95),
    ID_tp_stud_parauni   int      not null
        constraint FK_PRUNI_studente_PRUNI_tp_studente
            references PRUNI_tp_studente,
    domicilioASresidenza smallint not null
)
go

create table QRTZ_BLOB_TRIGGERS
(
    TRIGGER_NAME  varchar(200) not null,
    TRIGGER_GROUP varchar(200) not null,
    BLOB_DATA     image
)
go

create table QRTZ_CALENDARS
(
    CALENDAR_NAME varchar(200) not null
        constraint PK_QRTZ_CALENDARS
            primary key
                with (fillfactor = 95),
    CALENDAR      image        not null
)
go

create table QRTZ_FIRED_TRIGGERS
(
    ENTRY_ID          varchar(95)  not null
        constraint PK_QRTZ_FIRED_TRIGGERS
            primary key
                with (fillfactor = 95),
    TRIGGER_NAME      varchar(200) not null,
    TRIGGER_GROUP     varchar(200) not null,
    IS_VOLATILE       varchar      not null,
    INSTANCE_NAME     varchar(200) not null,
    FIRED_TIME        bigint       not null,
    PRIORITY          int          not null,
    STATE             varchar(16)  not null,
    JOB_NAME          varchar(200),
    JOB_GROUP         varchar(200),
    IS_STATEFUL       varchar,
    REQUESTS_RECOVERY varchar
)
go

create table QRTZ_JOB_DETAILS
(
    JOB_NAME          varchar(200) not null,
    JOB_GROUP         varchar(200) not null,
    DESCRIPTION       varchar(250),
    JOB_CLASS_NAME    varchar(250) not null,
    IS_DURABLE        varchar      not null,
    IS_VOLATILE       varchar      not null,
    IS_STATEFUL       varchar      not null,
    REQUESTS_RECOVERY varchar      not null,
    JOB_DATA          image,
    constraint PK_QRTZ_JOB_DETAILS
        primary key (JOB_NAME, JOB_GROUP)
            with (fillfactor = 95)
)
go

create table QRTZ_JOB_LISTENERS
(
    JOB_NAME     varchar(200) not null,
    JOB_GROUP    varchar(200) not null,
    JOB_LISTENER varchar(200) not null,
    constraint PK_QRTZ_JOB_LISTENERS
        primary key (JOB_NAME, JOB_GROUP, JOB_LISTENER)
            with (fillfactor = 95),
    constraint FK_QRTZ_JOB_LISTENERS_QRTZ_JOB_DETAILS
        foreign key (JOB_NAME, JOB_GROUP) references QRTZ_JOB_DETAILS
            on delete cascade
)
go

create table QRTZ_LOCKS
(
    LOCK_NAME varchar(40) not null
        constraint PK_QRTZ_LOCKS
            primary key
                with (fillfactor = 95)
)
go

create table QRTZ_PAUSED_TRIGGER_GRPS
(
    TRIGGER_GROUP varchar(200) not null
        constraint PK_QRTZ_PAUSED_TRIGGER_GRPS
            primary key
                with (fillfactor = 95)
)
go

create table QRTZ_SCHEDULER_STATE
(
    INSTANCE_NAME     varchar(200) not null
        constraint PK_QRTZ_SCHEDULER_STATE
            primary key
                with (fillfactor = 95),
    LAST_CHECKIN_TIME bigint       not null,
    CHECKIN_INTERVAL  bigint       not null
)
go

create table QRTZ_TRIGGERS
(
    TRIGGER_NAME   varchar(200) not null,
    TRIGGER_GROUP  varchar(200) not null,
    JOB_NAME       varchar(200) not null,
    JOB_GROUP      varchar(200) not null,
    IS_VOLATILE    varchar      not null,
    DESCRIPTION    varchar(250),
    NEXT_FIRE_TIME bigint,
    PREV_FIRE_TIME bigint,
    PRIORITY       int,
    TRIGGER_STATE  varchar(16)  not null,
    TRIGGER_TYPE   varchar(8)   not null,
    START_TIME     bigint       not null,
    END_TIME       bigint,
    CALENDAR_NAME  varchar(200),
    MISFIRE_INSTR  smallint,
    JOB_DATA       image,
    END_JOB_TIME   bigint,
    constraint PK_QRTZ_TRIGGERS
        primary key (TRIGGER_NAME, TRIGGER_GROUP)
            with (fillfactor = 95),
    constraint FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS
        foreign key (JOB_NAME, JOB_GROUP) references QRTZ_JOB_DETAILS
)
go

create table QRTZ_CRON_TRIGGERS
(
    TRIGGER_NAME    varchar(200) not null,
    TRIGGER_GROUP   varchar(200) not null,
    CRON_EXPRESSION varchar(120) not null,
    TIME_ZONE_ID    varchar(80),
    constraint PK_QRTZ_CRON_TRIGGERS
        primary key (TRIGGER_NAME, TRIGGER_GROUP)
            with (fillfactor = 95),
    constraint FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS
        foreign key (TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS
            on delete cascade
)
go

create table QRTZ_SIMPLE_TRIGGERS
(
    TRIGGER_NAME    varchar(200) not null,
    TRIGGER_GROUP   varchar(200) not null,
    REPEAT_COUNT    bigint       not null,
    REPEAT_INTERVAL bigint       not null,
    TIMES_TRIGGERED bigint       not null,
    constraint PK_QRTZ_SIMPLE_TRIGGERS
        primary key (TRIGGER_NAME, TRIGGER_GROUP)
            with (fillfactor = 95),
    constraint FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS
        foreign key (TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS
            on delete cascade
)
go

create table QRTZ_TRIGGER_LISTENERS
(
    TRIGGER_NAME     varchar(200) not null,
    TRIGGER_GROUP    varchar(200) not null,
    TRIGGER_LISTENER varchar(200) not null,
    constraint PK_QRTZ_TRIGGER_LISTENERS
        primary key (TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_LISTENER)
            with (fillfactor = 95),
    constraint FK_QRTZ_TRIGGER_LISTENERS_QRTZ_TRIGGERS
        foreign key (TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS
            on delete cascade
)
go

create table QUERY_job_notturni
(
    id              int identity,
    tipo_operazione nvarchar(50),
    sql             nvarchar(max)
)
go

create table RDC_R_CONF
(
    rdc_anno               int,
    ID_servizio            int,
    cf_operatore           nvarchar(16),
    enabled                smallint,
    start_ID_Domanda       int,
    anno_AUP               int,
    esecuzione_singola     int,
    effettua_detrazione    int,
    fattore_moltiplicativo int,
    destinazione           nvarchar(50)
)
go

create table RECCRED_WS_tp_Comunicazioni
(
    ID_tp_comunicazione smallint      not null
        constraint PK_RECCRED_WS_tp_Comunicazioni
            primary key,
    tp_comunicazione    nvarchar(100) not null
)
go

create table RECCRED_WS_Comunicazioni
(
    ID_comunicazione    int identity
        constraint PK_RECCRED_WS_Comunicazioni
            primary key,
    ID_tp_comunicazione smallint      not null
        constraint FK_RECCRED_WS_Comunicazioni_RECCRED_WS_tp_Comunicazioni
            references RECCRED_WS_tp_Comunicazioni,
    anno                int           not null,
    mese                int           not null,
    data                datetime      not null,
    content_type        nvarchar(100) not null,
    request             nvarchar(max),
    response            nvarchar(max)
)
go

create table RECCRED_tp_stati_upload
(
    ID_tp_stato_upload smallint     not null
        constraint PK_RECCRED_tp_stati_upload
            primary key
                with (fillfactor = 95),
    tp_stato_upload    nvarchar(50) not null
)
go

create table REC_IMPORTI_AUP_2019
(
    PROT_domanda             nchar(25),
    codice_fiscale           nchar(16) not null,
    anno                     int       not null,
    importo                  numeric(10, 2),
    mese_inizio              int       not null,
    mese_fine                int       not null,
    STATO_domanda            nchar(50),
    data_interrogazione_INPS datetime,
    ID_domanda               int,
    da_ricalcolare           smallint,
    mesi_erogati             int,
    data_presentazione       datetime,
    importo_quota_A          numeric(10, 2),
    importo_quota_B          numeric(10, 2)
)
go

create table REC_importi
(
    PROT_domanda             nchar(25),
    codice_fiscale           nchar(16) not null,
    anno                     int       not null,
    importo                  numeric(10, 2),
    mese_inizio              int       not null,
    mese_fine                int       not null,
    STATO_domanda            nchar(50),
    data_interrogazione_INPS datetime,
    ID_domanda               int,
    da_ricalcolare           smallint,
    mesi_erogati             int,
    data_presentazione       datetime,
    importo_quota_A          numeric(10, 2),
    importo_quota_B          numeric(10, 2),
    tipo_quota_B             nvarchar(50),
    constraint id_REC_importi
        primary key (codice_fiscale, anno, mese_inizio, mese_fine)
)
go

create table REC_importi_TEMP
(
    PROT_domanda             nchar(25),
    codice_fiscale           nchar(16) not null,
    anno                     int       not null,
    importo                  numeric(10, 2),
    mese_inizio              int       not null,
    mese_fine                int       not null,
    STATO_domanda            nchar(50),
    data_interrogazione_INPS datetime,
    ID_domanda               int,
    da_ricalcolare           smallint,
    mesi_erogati             int,
    data_presentazione       datetime,
    importo_quota_A          numeric(10, 2),
    importo_quota_B          numeric(10, 2),
    tipo_quota_B             nvarchar(50),
    constraint REC_importi_TEMP_PK
        primary key (codice_fiscale, anno, mese_fine, mese_inizio)
)
go

create table REC_importi_TEMP_2
(
    PROT_domanda             nchar(25),
    codice_fiscale           nchar(16) not null,
    anno                     int       not null,
    importo                  numeric(10, 2),
    anno_mese_inizio         int       not null,
    anno_mese_fine           int       not null,
    STATO_domanda            nchar(50),
    data_interrogazione_INPS datetime,
    ID_domanda               int,
    da_ricalcolare           smallint,
    mesi_erogati             int,
    data_presentazione       datetime,
    importo_quota_A          numeric(10, 2),
    importo_quota_B          numeric(10, 2),
    tipo_quota_B             nvarchar(50),
    primary key (codice_fiscale, anno, anno_mese_fine, anno_mese_inizio)
)
go

create table REC_importi_TEMP_3
(
    PROT_domanda             nchar(25),
    codice_fiscale           nchar(16) not null,
    anno                     int       not null,
    importo                  numeric(10, 2),
    anno_mese_inizio         int       not null,
    anno_mese_fine           int       not null,
    STATO_domanda            nchar(50),
    data_interrogazione_INPS datetime,
    ID_domanda               int,
    da_ricalcolare           smallint,
    mesi_erogati             int,
    data_presentazione       datetime,
    importo_quota_A          numeric(10, 2),
    importo_quota_B          numeric(10, 2),
    tipo_quota_B             nvarchar(50),
    constraint REC_importi_TEMP_3_PK
        primary key (codice_fiscale, anno, anno_mese_inizio, anno_mese_fine)
)
go

create table REC_importi_TEMP_4
(
    PROT_domanda             nchar(25),
    codice_fiscale           nchar(16) not null,
    anno                     int       not null,
    importo                  numeric(10, 2),
    anno_mese_inizio         int       not null,
    anno_mese_fine           int       not null,
    STATO_domanda            nchar(50),
    data_interrogazione_INPS datetime,
    ID_domanda               int,
    da_ricalcolare           smallint,
    mesi_erogati             int,
    data_presentazione       datetime,
    importo_quota_A          numeric(10, 2),
    importo_quota_B          numeric(10, 2),
    tipo_quota_B             nvarchar(50),
    constraint REC_importi_TEMP_4_PK
        primary key (anno, anno_mese_inizio, anno_mese_fine, codice_fiscale)
)
go

create table REC_importi_TEMP_5
(
    PROT_domanda             nchar(25),
    codice_fiscale           nchar(16) not null,
    anno                     int       not null,
    importo                  numeric(10, 2),
    anno_mese_inizio         int       not null,
    anno_mese_fine           int       not null,
    STATO_domanda            nchar(50),
    data_interrogazione_INPS datetime,
    ID_domanda               int,
    da_ricalcolare           smallint,
    mesi_erogati             int,
    data_presentazione       datetime,
    importo_quota_A          numeric(10, 2),
    importo_quota_B          numeric(10, 2),
    tipo_quota_B             nvarchar(50),
    constraint REC_importi_TEMP_5_PK
        primary key (anno, anno_mese_inizio, anno_mese_fine, codice_fiscale)
)
go

create table REI_WS_azioni_inps
(
    ID_domanda    int not null,
    reicom        int,
    tp_invio_inps nchar(2),
    data          datetime,
    xml           xml,
    xmlEsito      xml
)
go

create table REI_dati_inviati_daINPS
(
    ID                   float,
    [data presentazione] nvarchar(255),
    [codice fiscale]     nvarchar(255),
    [cognome e nome]     nvarchar(255)
)
go

create table REI_esiti
(
    ID_domanda              int          not null,
    ID_INPS                 int,
    MESE                    int          not null,
    ANNO                    int          not null,
    CODICE_ENTE             nvarchar(4),
    DATA_MODULO             datetime,
    COGNOME_RICH            nvarchar(35),
    NOME_RICH               nvarchar(35),
    COD_FISCALE_RICH        nvarchar(16),
    STATO_DOMANDA           nvarchar(255),
    NOTE                    nvarchar(255),
    ANNO_MESE_COMP          nvarchar(50) not null,
    DSU_PROTOCOLLO_DOM      nvarchar(255),
    DSU_DATA_SOTT           nvarchar(255),
    ISEE_ESITO              nvarchar(255),
    ISEE_COD_ESITO          nvarchar(255),
    ISEE_DESC               nvarchar(255),
    DSU_PROTOCOLLO_PERIODO  nvarchar(255),
    DSU_DATA_SOTT_PERIODO   datetime,
    DSU_DATA_SCAD_PERIODO   datetime,
    AMMORTIZ_COMPATIBILITA  nvarchar(255),
    COND_LAV                nvarchar(255),
    COND_LAV_DESC           nvarchar(255),
    IMPORTO_CALC_BASE_ANNUA float,
    IMPORTO_DISP            float,
    DATA_POSTE              datetime,
    ESITO_POSTE             nvarchar(255),
    NOTE_ISEE               nvarchar(255),
    xml                     xml,
    data_esito              datetime
)
go

create table REI_esiti_richieste
(
    ID_RICH_NUCLEO         int,
    DATA_PRES_MODU         datetime,
    DESC_STATO_DOM         nvarchar(50),
    ANNO_MESE              nvarchar(50),
    NOME_RICH              nvarchar(50),
    COGNOME_RICH           nvarchar(50),
    COD_FISC_RICH          nvarchar(16),
    INDIR_NOTIFICA_RICH    nvarchar(50),
    NUM_TELEFONO           nvarchar(50),
    ESITO_REI              nchar(250),
    DETTAGLIO_VERIFICA_REI nvarchar(250),
    ESITO_ISEE             nvarchar(250),
    ESITO_AMMORTIZZATORI   nvarchar(250),
    ESITO_COND_LAV         nvarchar(250),
    ESITO_RESIDENZA        nvarchar(250),
    ESITO_CITTADINANZA     nvarchar(250),
    ESITO_55_ENNE          nvarchar(250),
    VAL_ISEE               decimal(18, 2),
    VAL_ISRE               decimal(18, 2),
    IMPORTO_DISPOSTO       decimal(18, 2),
    DATA_INVIO_POSTE       datetime,
    DATA_RENDICONTAZIONE   datetime,
    ESITO_RENDICONTAZIONE  nvarchar(250)
)
go

create table REI_esiti_tp_anno_mese_comp
(
    ANNO_MESE_COMP nvarchar(50) not null
        constraint PK_REI_esiti_tp_anno_mese_comp
            primary key
                with (fillfactor = 95),
    ANNO           int          not null,
    MESE           int          not null
)
go

create table REI_esiti_tp_stato
(
    STATO_DOMANDA             nchar(10),
    DESCRIZIONE_STATO_DOMANDA nvarchar(50),
    CANCELLABILE              int,
    ORDINE_STATO_INPS         int,
    RITRASMETTIBILE           int
)
go

create table REI_importi
(
    ID_domanda int            not null,
    mese       int            not null,
    anno       int            not null,
    importo    numeric(10, 2) not null,
    constraint PK_REI_esiti
        primary key (ID_domanda, mese, anno)
            with (fillfactor = 95)
)
go

create table REI_listaID_INPS
(
    ID                   float,
    [data presentazione] nvarchar(255),
    [codice fiscale]     nvarchar(255),
    [cognome e nome]     nvarchar(255),
    aggiornatoClesius    int,
    ID_domanda           int
)
go

create table REI_richieste_inps
(
    ID_richiesta   int identity
        constraint PK_REI_richieste_inps_1
            primary key
                with (fillfactor = 95),
    ID_user        int not null,
    data_creazione datetime,
    nome_file      nvarchar(50),
    xml            xml
)
go

create table REI_richieste_inps_domande
(
    ID_richiesta        int not null
        constraint FK_REI_richieste_inps_domande_REI_richieste_inps
            references REI_richieste_inps,
    ID_domanda          int not null,
    edizione_doc        int,
    id_tp_comunicazione nvarchar(3),
    constraint PK_REI_richieste_INPS
        primary key (ID_richiesta, ID_domanda)
            with (fillfactor = 95)
)
go

create table REI_richieste_inps_enti
(
    ID_ente  int         not null,
    ID_luogo nvarchar(6) not null,
    constraint PK_REI_enti_luoghi_invio_dati_INPS
        primary key (ID_ente, ID_luogo)
            with (fillfactor = 95)
)
go

create table REI_temp_trento
(
    Id_domanda        float,
    Codicefiscalerich nvarchar(255)
)
go

create table REI_tp_anno_reiCom
(
    ID_servizio int not null,
    anno_rif    int not null,
    constraint PK_REI_tp_anno_reiCom
        primary key (ID_servizio, anno_rif)
)
go

create table REI_tp_comunicazioni
(
    id_tp_comunicazione nchar(3) not null
        constraint PK_REI_tp_comunicazioni
            primary key,
    comunicazione       nvarchar(100)
)
go

create table REI_tp_decadenze
(
    ID_decadenza int           not null,
    ID_servizio  int           not null,
    tp_decadenza nvarchar(250) not null,
    constraint PK_REI_tp_decadenza
        primary key (ID_decadenza, ID_servizio)
            with (fillfactor = 95)
)
go

create table REI_tp_lavoro
(
    ID_tp_lavoro int not null,
    ID_servizio  int not null,
    tp_lavoro    nvarchar(250),
    ID_INPS      nvarchar(2),
    constraint PK_REI_tp_lavoro
        primary key (ID_tp_lavoro, ID_servizio)
)
go

create table REI_COM
(
    ID_domanda                 int          not null,
    ID_soggetto                int          not null,
    reicom                     int          not null,
    ID_tp_cittadinanza_nascita nvarchar(4),
    ID_servizio                int,
    estero                     int          not null,
    italia                     int          not null,
    ID_provincia_residenza     nvarchar(2)  not null,
    ID_comune_residenza        nvarchar(6)  not null,
    indirizzo_residenza        nvarchar(50) not null,
    n_civ_residenza            nvarchar(10) not null,
    cap_residenza              nvarchar(10) not null,
    ID_tp_cittadinanza         nvarchar(4),
    e_mail                     nvarchar(50),
    cellulare                  nvarchar(20),
    telefono                   nvarchar(20),
    ID_tp_lavoro               int          not null,
    dataInizioLavoro           datetime     not null,
    dataFineLavoro             datetime,
    redditoPresunto            numeric(18, 2),
    sedeLavoro                 nvarchar(50) not null,
    intestazioneDatoreLavoro   nvarchar(50) not null,
    anno_reiCom                int,
    data_reiCom                datetime     not null,
    creazioneFileINPS          int,
    reiCom_invioINPS           int,
    ID_reiComINPS              int,
    constraint PK_REI_COM
        primary key (ID_domanda, ID_soggetto, reicom)
            with (fillfactor = 95),
    constraint FK_REI_COM_REI_tp_anno_reiCom
        foreign key (ID_servizio, anno_reiCom) references REI_tp_anno_reiCom,
    constraint FK_REI_COM_REI_tp_lavoro
        foreign key (ID_tp_lavoro, ID_servizio) references REI_tp_lavoro
)
go

create table REI_tp_progetti
(
    ID_progetto int not null,
    ID_servizio int not null,
    tipo_INPS   nvarchar(2),
    progetto    nvarchar(200),
    constraint PK_REI_tp_progetti
        primary key (ID_progetto, ID_servizio)
            with (fillfactor = 95)
)
go

create table REI_tp_progetti_sottoscrizioni
(
    ID_progetto_sott int not null,
    ID_servizio      int not null,
    tipo_INPS        nvarchar(2),
    sottoscrizione   nvarchar(150),
    constraint PK_REI_tp_progetti_sottoscrizioni
        primary key (ID_progetto_sott, ID_servizio)
            with (fillfactor = 95)
)
go

create table REI_tp_verifiche
(
    id_verifica    nchar(2)     not null
        constraint PK_rei_verifiche
            primary key,
    tipo_controllo nvarchar(50) not null,
    obbligatorio   smallint     not null
)
go

create table REPORT_DATASOURCE
(
    DATASOURCE_ID    int identity
        primary key
            with (fillfactor = 95),
    NAME             varchar(255) not null
        unique
            with (fillfactor = 95),
    DRIVER           varchar(255),
    URL              varchar(255) not null,
    USERNAME         varchar(255),
    PASSWORD         varchar(255),
    MAX_IDLE         int,
    MAX_ACTIVE       int,
    MAX_WAIT         numeric(19),
    VALIDATION_QUERY varchar(255),
    JNDI             tinyint
)
go

create table REPORT_ALERT
(
    ALERT_ID      int identity
        primary key
            with (fillfactor = 95),
    NAME          varchar(255) not null
        unique
            with (fillfactor = 95),
    DESCRIPTION   varchar(255) not null,
    ALERT_QUERY   text         not null,
    DATASOURCE_ID int
        constraint FKF81C86714330D5A7
            references REPORT_DATASOURCE
)
go

create table REPORT_EXPORT_OPTIONS
(
    EXPORT_OPTION_ID        int identity
        primary key
            with (fillfactor = 95),
    XLS_REMOVE_EMPTY_SPACE  tinyint not null,
    XLS_ONE_PAGE_PER_SHEET  tinyint not null,
    XLS_AUTO_DETECT_CELL    tinyint not null,
    XLS_WHITE_BACKGROUND    tinyint not null,
    HTML_REMOVE_EMPTY_SPACE tinyint not null,
    HTML_WHITE_BACKGROUND   tinyint not null,
    HTML_USE_IMAGES         tinyint not null,
    HTML_WRAP_BREAK         tinyint not null
)
go

create table REPORT
(
    REPORT_ID        int identity
        primary key
            with (fillfactor = 95),
    NAME             varchar(255) not null collate Latin1_General_CI_AS,
    DESCRIPTION      varchar(255) not null,
    REPORT_FILE      varchar(255) not null,
    PDF_EXPORT       tinyint      not null,
    CSV_EXPORT       tinyint      not null,
    XLS_EXPORT       tinyint      not null,
    HTML_EXPORT      tinyint      not null,
    RTF_EXPORT       tinyint      not null,
    TEXT_EXPORT      tinyint      not null,
    EXCEL_EXPORT     tinyint      not null,
    FILL_VIRTUAL     tinyint      not null,
    HIDDEN_REPORT    tinyint      not null,
    REPORT_QUERY     text,
    DATASOURCE_ID    int
        constraint FK8FDF49344330D5A7
            references REPORT_DATASOURCE,
    CHART_ID         int,
    EXPORT_OPTION_ID int
        constraint FK8FDF4934F4DD5A50
            references REPORT_EXPORT_OPTIONS,
    PER_RESPONSABILI int,
    GROUP_KEY        int
)
go

declare @MS_DefaultView tinyint = 2
exec sp_addextendedproperty 'MS_DefaultView', @MS_DefaultView, 'SCHEMA', 'dbo', 'TABLE', 'REPORT'
go

exec sp_addextendedproperty 'MS_Filter', N'((REPORT.NAME ALike "%ARCHIVIO%"))', 'SCHEMA', 'dbo', 'TABLE', 'REPORT'
go

declare @MS_OrderByOn bit = '0'
exec sp_addextendedproperty 'MS_OrderByOn', @MS_OrderByOn, 'SCHEMA', 'dbo', 'TABLE', 'REPORT'
go

declare @MS_Orientation tinyint = 0
exec sp_addextendedproperty 'MS_Orientation', @MS_Orientation, 'SCHEMA', 'dbo', 'TABLE', 'REPORT'
go

declare @MS_RowHeight smallint = 10110
exec sp_addextendedproperty 'MS_RowHeight', @MS_RowHeight, 'SCHEMA', 'dbo', 'TABLE', 'REPORT'
go

exec sp_addextendedproperty 'MS_TableMaxRecords', 10000, 'SCHEMA', 'dbo', 'TABLE', 'REPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'REPORT_ID'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'REPORT_ID'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'REPORT_ID'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'NAME'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'NAME'
go

declare @MS_ColumnWidth smallint = 5250
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'NAME'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'DESCRIPTION'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'DESCRIPTION'
go

declare @MS_ColumnWidth smallint = 6300
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'DESCRIPTION'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'REPORT_FILE'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'REPORT_FILE'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'REPORT_FILE'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'PDF_EXPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'PDF_EXPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'PDF_EXPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'CSV_EXPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'CSV_EXPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'CSV_EXPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'XLS_EXPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'XLS_EXPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'XLS_EXPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'HTML_EXPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'HTML_EXPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'HTML_EXPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'RTF_EXPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'RTF_EXPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'RTF_EXPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'TEXT_EXPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'TEXT_EXPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'TEXT_EXPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'EXCEL_EXPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'EXCEL_EXPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'EXCEL_EXPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'FILL_VIRTUAL'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'FILL_VIRTUAL'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'FILL_VIRTUAL'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'HIDDEN_REPORT'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'HIDDEN_REPORT'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'HIDDEN_REPORT'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'REPORT_QUERY'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'REPORT_QUERY'
go

declare @MS_ColumnWidth smallint = 9465
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'REPORT_QUERY'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'DATASOURCE_ID'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'DATASOURCE_ID'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'DATASOURCE_ID'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'CHART_ID'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'CHART_ID'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'CHART_ID'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'EXPORT_OPTION_ID'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'EXPORT_OPTION_ID'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'EXPORT_OPTION_ID'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'PER_RESPONSABILI'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'PER_RESPONSABILI'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'PER_RESPONSABILI'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN',
     'GROUP_KEY'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'GROUP_KEY'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'REPORT', 'COLUMN', 'GROUP_KEY'
go

create index UQ__REPORT__29221CFB
    on REPORT (NAME)
    with (fillfactor = 95)
go

create table REPORT_CHART
(
    CHART_ID         int identity
        primary key
            with (fillfactor = 95),
    NAME             varchar(255) not null
        unique
            with (fillfactor = 95),
    DESCRIPTION      varchar(255) not null,
    CHART_QUERY      text         not null,
    CHART_TYPE       int          not null,
    WIDTH            int          not null,
    HEIGHT           int          not null,
    X_AXIS_LABEL     varchar(255),
    Y_AXIS_LABEL     varchar(255),
    SHOW_LEGEND      tinyint      not null,
    SHOW_TITLE       tinyint      not null,
    SHOW_VALUES      tinyint      not null,
    PLOT_ORIENTATION int,
    DATASOURCE_ID    int
        constraint FKF836D4F34330D5A7
            references REPORT_DATASOURCE,
    REPORT_ID        int
        constraint FKF836D4F3AAEF4A13
            references REPORT
)
go

alter table REPORT
    add constraint FK8FDF4934164AA2ED
        foreign key (CHART_ID) references REPORT_CHART
go

create table REPORT_GROUP_MAP
(
    REPORT_ID   int not null
        constraint FKEF946211AAEF4A13
            references REPORT,
    ID_UFFICIO  int not null,
    ID_ENTE     int not null,
    ID_SERVIZIO int not null,
    constraint PK_REPORT_GROUP_MAP
        primary key (REPORT_ID, ID_UFFICIO, ID_ENTE, ID_SERVIZIO)
            with (fillfactor = 95)
)
go

create table REPORT_PARAMETER
(
    PARAMETER_ID  int identity
        primary key
            with (fillfactor = 95),
    NAME          varchar(255) not null,
    TYPE          varchar(255) not null,
    CLASSNAME     varchar(255) not null,
    DATA          text,
    DATASOURCE_ID int
        constraint FKBC64163E4330D5A7
            references REPORT_DATASOURCE,
    DESCRIPTION   varchar(255),
    REQUIRED      tinyint,
    MULTI_SELECT  tinyint,
    VISIBLE       tinyint
)
go

create index UQ__REPORT_PARAMETER__3D2915A8
    on REPORT_PARAMETER (NAME)
    with (fillfactor = 95)
go

create table REPORT_PARAMETER_MAP
(
    REPORT_ID    int not null
        constraint FK23FF1FBBAAEF4A13
            references REPORT,
    PARAMETER_ID int
        constraint FK23FF1FBB1AFAD98D
            references REPORT_PARAMETER,
    REQUIRED     tinyint,
    SORT_ORDER   int,
    STEP         int,
    MAP_ID       int not null,
    primary key (REPORT_ID, MAP_ID)
        with (fillfactor = 95)
)
go

create table REPORT_PARAMETER_tmp
(
    PARAMETER_ID  int identity,
    NAME          varchar(255) not null,
    TYPE          varchar(255) not null,
    CLASSNAME     varchar(255) not null,
    DATA          text,
    DATASOURCE_ID int,
    DESCRIPTION   varchar(255),
    REQUIRED      tinyint,
    MULTI_SELECT  tinyint,
    VISIBLE       tinyint
)
go

create table RIC_AUP_C_ElaIN
(
    ID_domanda  int          not null,
    anno        int          not null,
    mese        int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_RIC_AUP_C_ElaIN
        primary key (ID_domanda, anno, mese, net, node)
            with (fillfactor = 95)
)
go

create table RIC_AUP_C_ElaOUT
(
    ID_domanda    int          not null,
    anno          int          not null,
    mese          int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    numeric_value float,
    constraint PK_RIC_AUP_C_ElaOUT
        primary key (ID_domanda, anno, mese, net, node)
            with (fillfactor = 95)
)
go

create table RIC_C_Confronto
(
    stream       varchar(10),
    id_domanda   int,
    node         varchar(50),
    value_before float,
    value_after  float,
    dif          float
)
go

create table RIC_C_ElaIN
(
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_RIC_C_ElaIN
        primary key (ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create table RIC_C_ElaOUT
(
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    constraint PK_RIC_C_ElaOUT
        primary key (ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create table RIC_C_Elab
(
    id_domanda  int          not null,
    net         nvarchar(50) not null,
    elab_date   smalldatetime,
    fingerprint nvarchar(50),
    checkfp     smallint,
    n_elab      int,
    constraint PK_RIC_C_Elab
        primary key (id_domanda, net)
            with (fillfactor = 95)
)
go

create table RIC_Confronto
(
    stream       varchar(50),
    id_domanda   int,
    net          varchar(50),
    node         varchar(50),
    value_before float,
    value_after  float,
    dif          float
)
go

create table RIC_Differenze
(
    id_blocco  int      not null,
    id_domanda int      not null,
    forzata    smallint not null,
    constraint PK_RIC_differenze
        primary key (id_blocco, id_domanda, forzata)
            with (fillfactor = 95)
)
go

create table RIC_ER_C_ElaOUT
(
    ID_domanda        int not null,
    ID_soggetto       int not null,
    ID_tp_prestazione int not null,
    importo           numeric(12, 2),
    constraint PK_RIC_ER_C_elaout
        primary key (ID_domanda, ID_soggetto, ID_tp_prestazione)
)
go

create table RIC_IC_C_ElaIN
(
    anno        int          not null,
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_RIC_IC_C_ElaIN
        primary key (anno, ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create index IND_RIC_IC_C_Elain
    on RIC_IC_C_ElaIN (ID_domanda)
    with (fillfactor = 95)
go

create table RIC_IC_C_ElaOUT
(
    anno          int          not null,
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    locked        int          not null,
    constraint PK_RIC_IC_C_Elaout
        primary key (anno, ID_domanda, net, node)
            with (fillfactor = 95)
)
go

create index IND_RIC_IC_C_ElaOUT
    on RIC_IC_C_ElaOUT (ID_domanda)
    with (fillfactor = 95)
go

create table RIC_swap
(
    ID_domanda         int not null,
    Assegni            float,
    Assegno_imposto    float,
    Auto               float,
    C1_aut             float,
    C1_dip             float,
    C1_pens            float,
    C2_agr             float,
    C3_imp             float,
    C4_part            float,
    C5_altri           float,
    Canone             float,
    Figli_fam_numerose float,
    Imm_strumentali    float,
    Importo            float,
    Imposte            float,
    Invalidi           float,
    Lavoratori         float,
    Lavoro_fem         float,
    Mediche            float,
    Monogenitore       float,
    Mq                 float,
    Mutuo              float,
    N_componenti       float,
    P_complementare    float,
    PI                 float,
    PM                 float,
    Precedente         float,
    Previdenza         float,
    Requisiti          float,
    RES                float,
    Spesa              float,
    Spese_redditi      float,
    Ultra65            float,
    Voto               float
)
go

create table RIC_swap2
(
    ID_domanda         int not null,
    Assegni            float,
    Auto               float,
    C1_aut             float,
    C1_dip             float,
    C1_pens            float,
    C2_agr             float,
    C3_imp             float,
    C4_part            float,
    C5_altri           float,
    Canone             float,
    Figli_fam_numerose float,
    Imm_strumentali    float,
    Imposte            float,
    Invalidi           float,
    Lavoratori         float,
    Lavoro_fem         float,
    Mediche            float,
    Monogenitore       float,
    Mq                 float,
    Mutuo              float,
    N_componenti       float,
    P_complementare    float,
    PI                 float,
    PM                 float,
    Previdenza         float,
    RES                float,
    Spese_redditi      float,
    Ultra65            float
)
go

create table ROV_assunzioni_tp_condanne
(
    id_tp_condanna int not null
        constraint PK_ROV_assunzioni_tp_condanne
            primary key
                with (fillfactor = 95),
    condanna       nvarchar(200)
)
go

create index IX_ROV_assunzioni_tp_condanne
    on ROV_assunzioni_tp_condanne (id_tp_condanna)
    with (fillfactor = 95)
go

create table ROV_assunzioni_tp_penali
(
    id_tp_penale int not null
        constraint PK_ROV_assunzioni_tp_penali
            primary key
                with (fillfactor = 95),
    penale       nvarchar(200)
)
go

create index IX_ROV_assunzioni_tp_penali
    on ROV_assunzioni_tp_penali (id_tp_penale)
    with (fillfactor = 95)
go

create table ROV_assunzioni_tp_titoli
(
    id_tp_titolo int not null
        constraint PK_ROV_assunzioni_tp_titoli
            primary key
                with (fillfactor = 95),
    titolo       nvarchar(200)
)
go

create index IX_ROV_assunzioni_tp_titoli
    on ROV_assunzioni_tp_titoli (id_tp_titolo)
    with (fillfactor = 95)
go

create table RPAF_Determine
(
    ID_Determina  int not null
        constraint PK_RPAF_Determine
            primary key
                with (fillfactor = 95),
    Numero        int,
    DataDetermina datetime,
    Cancella      bit
)
go

create table RPAF_Registro
(
    ID_soggetto                           int not null
        constraint PK_RPAF_Registro
            primary key
                with (fillfactor = 95),
    codice_fiscale                        nvarchar(16),
    cognome                               nvarchar(35),
    nome                                  nvarchar(35),
    ID_tp_sex                             nvarchar,
    ID_provincia_nascita                  nvarchar(2),
    ID_luogo_nascita                      nvarchar(6),
    data_nascita                          datetime,
    ID_provincia_residenza                nvarchar(2),
    ID_comune_residenza                   nvarchar(6),
    indirizzo_residenza                   nvarchar(50),
    n_civ_residenza                       nvarchar(10),
    cap_residenza                         nvarchar(10),
    telefono_residenza                    nvarchar(20),
    e_mail                                nvarchar(50),
    cellulare                             nvarchar(50),
    ID_tp_cittadinanza                    nvarchar(4),
    frazione_residenza                    nvarchar(50),
    titolo_sanitario                      bit,
    corso_formativo                       bit,
    attivita_lavorativa                   bit,
    note                                  nvarchar(200),
    domicilio                             bit,
    id_comune_domicilio                   nvarchar(6),
    id_provincia_domicilio                nvarchar(2),
    domicilio_cap                         nvarchar(10),
    domicilio_frazione                    nvarchar(50),
    domicilio_indirizzo                   nvarchar(50),
    domicilio_n_civico                    nvarchar(10),
    condanne_penali                       bit,
    titolo_studio                         nvarchar(100),
    titolo_studio_rilasciato              nvarchar(100),
    titolo_studio_data                    datetime,
    id_provincia_titolo_studio            nvarchar(2),
    id_nazione_titolo_studio              nvarchar(6),
    conoscenza_italiano                   bit,
    attestato_frequenza                   nvarchar(100),
    attestato_frequenza_rilascio          nvarchar(100),
    attestato_frequenza_data              datetime,
    id_provincia_attestato_frequenza      nvarchar(2),
    attestato_frequenza_mant              nvarchar(100),
    attestato_frequenza_rilascio_mant     nvarchar(100),
    attestato_frequenza_data_mant         datetime,
    id_provincia_attestato_frequenza_mant nvarchar(2),
    Versamenti_mant                       bit,
    ID_tp_istituto_mant                   int,
    consenso_recapito_telefonico          bit,
    consenso_disp_territoriale            bit,
    ID_tp_istituto_titolo                 int,
    ID_tp_lingua_titolo                   int,
    ID_tp_istituto_corso                  int,
    permesso_soggiorno                    bit,
    permesso_soggiorno_numero             nvarchar(50),
    permesso_questura                     nvarchar(50),
    permesso_data                         datetime,
    permesso_scadenza                     datetime,
    marca_bollo_numero                    nvarchar(50),
    marca_bollo_data                      datetime,
    ID_tp_documento_permesso              int,
    non_disponibile                       bit,
    patente                               bit,
    Esito                                 bit,
    Eliminato                             bit,
    Deroghe                               bit,
    DataFineIscrizione                    datetime,
    DataUltimoRinnovo                     datetime,
    ID_domanda                            int,
    ID_Determina_Iscrizione               int
        constraint FK_RPAF_Registro_RPAF_Determine
            references RPAF_Determine,
    ID_Determina_Cancellazione            int,
    ID_User_UltimaModifica                int,
    Data_UltimaModifica                   datetime,
    ID_Primadomanda                       int,
    DataIscrizione                        datetime
)
go

create table RPAF_Registro_bak
(
    ID_soggetto                      int not null,
    codice_fiscale                   nvarchar(16),
    cognome                          nvarchar(35),
    nome                             nvarchar(35),
    ID_tp_sex                        nvarchar,
    ID_provincia_nascita             nvarchar(2),
    ID_luogo_nascita                 nvarchar(6),
    data_nascita                     datetime,
    ID_provincia_residenza           nvarchar(2),
    ID_comune_residenza              nvarchar(6),
    indirizzo_residenza              nvarchar(50),
    n_civ_residenza                  nvarchar(10),
    cap_residenza                    nvarchar(10),
    telefono_residenza               nvarchar(20),
    e_mail                           nvarchar(50),
    cellulare                        nvarchar(50),
    ID_tp_cittadinanza               nvarchar(4),
    frazione_residenza               nvarchar(50),
    titolo_sanitario                 bit,
    corso_formativo                  bit,
    attivita_lavorativa              bit,
    note                             nvarchar(200),
    domicilio                        bit,
    id_comune_domicilio              nvarchar(6),
    id_provincia_domicilio           nvarchar(2),
    domicilio_cap                    nvarchar(10),
    domicilio_frazione               nvarchar(50),
    domicilio_indirizzo              nvarchar(50),
    domicilio_n_civico               nvarchar(10),
    condanne_penali                  bit,
    titolo_studio                    nvarchar(100),
    titolo_studio_rilasciato         nvarchar(100),
    titolo_studio_data               datetime,
    id_provincia_titolo_studio       nvarchar(2),
    id_nazione_titolo_studio         nvarchar(6),
    conoscenza_italiano              bit,
    attestato_frequenza              nvarchar(100),
    attestato_frequenza_rilascio     nvarchar(100),
    attestato_frequenza_data         datetime,
    id_provincia_attestato_frequenza nvarchar(2),
    consenso_recapito_telefonico     bit,
    consenso_disp_territoriale       bit,
    ID_tp_istituto_titolo            int,
    ID_tp_lingua_titolo              int,
    ID_tp_istituto_corso             int,
    permesso_soggiorno               bit,
    permesso_soggiorno_numero        nvarchar(50),
    permesso_questura                nvarchar(50),
    permesso_data                    datetime,
    permesso_scadenza                datetime,
    marca_bollo_numero               nvarchar(50),
    marca_bollo_data                 datetime,
    ID_tp_documento_permesso         int,
    non_disponibile                  bit,
    patente                          bit,
    Esito                            bit,
    Eliminato                        bit,
    Deroghe                          bit,
    DataFineIscrizione               datetime,
    ID_domanda                       int,
    ID_Determina_Iscrizione          int,
    ID_Determina_Cancellazione       int,
    ID_User_UltimaModifica           int,
    Data_UltimaModifica              datetime
)
go

create table R_Altre_Funzioni
(
    ID_funzione int            not null
        constraint PK_R_Altre_Funzioni
            primary key
                with (fillfactor = 95),
    ID_ente     int            not null,
    ID_ufficio  int            not null,
    ID_user     int            not null,
    ordine      int            not null,
    combo       nvarchar(50)   not null,
    descrizione nvarchar(1000) not null,
    url         nvarchar(100)  not null,
    param       nvarchar(2000) not null,
    contesto    nvarchar(500)  not null
)
go

create table R_Banche
(
    id_banca int          not null
        constraint PK_R_Banche
            primary key
                with (fillfactor = 95),
    banca    nvarchar(50) not null
)
go

create table R_Banche_Sportelli
(
    ID_comune_catastale nvarchar(4) not null,
    ID_sportello        smallint    not null,
    comune_catastale    nvarchar(50),
    sportello           nvarchar(50),
    abi                 nvarchar(5),
    cab                 nvarchar(5),
    indirizzo           nvarchar(50),
    cap                 nchar(10),
    ID_banca            smallint,
    luogo               smallint,
    constraint PK_R_Banche_Sportelli
        primary key (ID_comune_catastale, ID_sportello)
            with (fillfactor = 95)
)
go

create table R_Benefici_extended
(
    ID_beneficio   int          not null,
    ID_servizio    int          not null,
    ID_periodo     int          not null,
    tipo_beneficio nvarchar(50) not null,
    input_output   nvarchar(50) not null,
    nodo_risultato nvarchar(50) not null,
    chiave_report  nvarchar(50)
)
go

create table R_Comunita_Enti
(
    ID_comunita_di_valle int not null
        constraint PK_R_Comunita_Enti
            primary key
                with (fillfactor = 95),
    ID_Ente              int,
    Comunita             nvarchar(70)
)
go

create table R_DB
(
    tipo           nvarchar(50)  not null,
    ambiente       nvarchar(50)  not null,
    cleszone       nvarchar(500) not null,
    clesiuswebapps nvarchar(500) not null
)
go

create table R_Enti
(
    ID_ente     int           not null
        constraint PK_R_Enti
            primary key nonclustered
                with (fillfactor = 90),
    ente        nvarchar(70),
    codice_ente nvarchar(15),
    tp_ente     nvarchar(3),
    enabled     smallint      not null,
    luogo       nvarchar(50),
    prov        nvarchar(50),
    cap         nvarchar(6),
    pref        nvarchar(5),
    regione     nvarchar(50),
    header      nvarchar(200) not null,
    footer      nvarchar(200) not null,
    indirizzo   nvarchar(200),
    luogo_ente  nvarchar(200),
    cap_ente    nvarchar(6)
)
go

create table Isee159_abilitazioni_ws
(
    ID_ente_erogatore int not null
        constraint PK_Isee159_abilitazioni_ws
            primary key
                with (fillfactor = 95)
        constraint FK_Isee159_abilitazioni_ws_R_Enti
            references R_Enti,
    codice_ente       nvarchar(50),
    codice_ufficio    nvarchar(50),
    cf_operatore      nvarchar(16)
)
go

create table R_Enti_Associati
(
    ID_ente           int not null,
    ID_ente_associato int not null,
    ID_servizio       int not null,
    nominativo        nvarchar(70),
    constraint PK_R_Comunita_Enti_Servizi
        primary key (ID_ente, ID_ente_associato, ID_servizio)
            with (fillfactor = 95)
)
go

create table R_IBAN
(
    Descrizione      char(100),
    Codice_stato     char(2) not null
        constraint PK_R_IBAN
            primary key,
    Limite_lunghezza int,
    Obbligatorio     int
)
go

create table R_Luoghi_Banche
(
    ID_comune_catastale nvarchar(4) not null,
    comune_catastale    nvarchar(50)
)
go

create table R_Luoghi_Zone_Climatiche
(
    ID_luogo       nvarchar(6) not null
        constraint R_Luoghi_Zone_Climatiche_PK
            primary key,
    zona_climatica char        not null,
    gas            smallint    not null
)
go

create table R_Province
(
    ID_provincia nvarchar(2) not null
        constraint PK_R_Province
            primary key nonclustered
                with (fillfactor = 95),
    provincia    nvarchar(25),
    ordine       int,
    ID_regione   nvarchar(3)
)
go

create table R_Luoghi
(
    ID_luogo           nvarchar(6)  not null
        constraint IX_R_Luoghi
            unique
                with (fillfactor = 95),
    ID_provincia       nvarchar(2)  not null
        constraint FK_R_Luoghi_R_Province
            references R_Province,
    luogo              nvarchar(60) not null,
    provincia          nvarchar(50) not null,
    cod_catastale      nvarchar(4),
    estinto            smallint     not null,
    data_modifica      datetime,
    note               nvarchar(200),
    estinto_dal        datetime,
    istituito_dal      datetime,
    ID_luogo_istituito nvarchar(6),
    luogo_istat        nvarchar(60),
    constraint PK_R_Luoghi
        primary key nonclustered (ID_luogo, ID_provincia)
            with (fillfactor = 95)
)
go

create table AMS_Incarichi
(
    ID_Incarico          int identity
        constraint PK_AMS_Incarichi
            primary key
                with (fillfactor = 95),
    ID_domanda           int not null
        constraint FK_AMS_Incarichi_AMS_Registro
            references AMS_Registro,
    NR                   nvarchar(10),
    dal                  datetime,
    al                   datetime,
    ID_tp_disponibilita  int
        constraint FK_AMS_Incarichi_AMS_tp_disponibilita
            references AMS_tp_disponibilita,
    ID_comune_residenza  nvarchar(6)
        constraint FK_AMS_Incarichi_R_luoghi
            references R_Luoghi (ID_luogo),
    ID_tp_stato_incarico smallint
        constraint FK_AMS_Incarichi_AMS_StatiIncarico
            references AMS_tp_stati_incarico
)
go

create table BAR_dati
(
    ID_domanda                   int            not null
        constraint PK_BAR_dati
            primary key
                with (fillfactor = 95),
    ID_tp_intervento             int            not null,
    rampa_accesso                smallint       not null,
    servoscala                   smallint       not null,
    piattaforma_elevatrice       smallint       not null,
    ascensore                    smallint       not null,
    ampliamento_porta            smallint       not null,
    percorsi_orizzontali         smallint       not null,
    dispositivi                  smallint       not null,
    automazioni                  smallint       not null,
    servizio_igienico            smallint       not null,
    sistemi_sollevamento         smallint       not null,
    altro                        smallint       not null,
    altro_descrizione            nvarchar(255)  not null,
    importo                      decimal(12, 2) not null,
    residenza_anni               smallint       not null,
    ID_provincia                 nvarchar(2)    not null,
    ID_luogo                     nvarchar(6)    not null,
    localita                     nvarchar(50)   not null,
    indirizzo                    nvarchar(50)   not null,
    n_civico                     nvarchar(10)   not null,
    piano                        nvarchar(10)   not null,
    cap                          nvarchar(10)   not null,
    p_ed                         nvarchar(10)   not null,
    p_f                          nvarchar(10)   not null,
    p_m                          nvarchar(10)   not null,
    ID_comune_catastale          nvarchar(6)    not null,
    comune_catastale             nvarchar(50)   not null,
    isResidenza                  smallint       not null,
    isResidenzaFutura            smallint,
    imm_invalido                 smallint       not null,
    imm_familiare                smallint       not null,
    ID_tp_titolo_giuridico       int            not null,
    altri_proprietari            nvarchar(255)  not null,
    no_altri_contr               smallint       not null,
    altri_contr                  smallint       not null,
    altri_contr_ente             nvarchar(255)  not null,
    agevolazioni_fiscali         smallint       not null,
    contributi_inail             smallint       not null,
    interventi_conclusi          smallint       not null,
    interventi_iniziati          smallint       not null,
    interventi_non_iniziati      smallint       not null,
    no_obbligo_urbanistico       smallint       not null,
    obb_urbanistico              smallint       not null,
    parti_comuni                 smallint       not null,
    no_parti_comuni              smallint       not null,
    no_contr_LP_1_1991           smallint       not null,
    contr_LP_1_1991              smallint       not null,
    aggravamento                 smallint       not null,
    danneggiamento               smallint       not null,
    no_contr_LP_1_1991_da_4_anni smallint       not null,
    contr_LP_1_1991_da_4_anni    smallint       not null,
    nuove_cond_fisiche           smallint       not null,
    inopportuna_permanenza       smallint       not null,
    cert_invalidita              smallint       not null,
    cert_intervento_necessario   smallint       not null,
    dich_consenso_modifiche      smallint       not null,
    fattura                      smallint       not null,
    doc_fotografica              smallint       not null,
    verbale_ass                  smallint       not null,
    verbale_ass_parti_comuni     smallint       not null,
    cert_int_per_aggravamento    smallint       not null,
    cert_cond_fisiche            smallint       not null,
    elaborati_grafici            smallint       not null,
    computo_metrico              smallint       not null,
    preventivo_spesa             smallint       not null,
    ID_tp_invalidita             int            not null,
    spesa_ammessa                decimal(12, 2) not null,
    data_finanziamento           datetime,
    cod_finanziamento            nvarchar(10),
    n_condomini                  int,
    quota_possesso               decimal(12, 3),
    perc_condominiale            int,
    constraint FK_BAR_dati_R_Luoghi
        foreign key (ID_luogo, ID_provincia) references R_Luoghi
)
go

create table CANA_dati
(
    ID_domanda                    int            not null
        constraint PK_CANA_dati
            primary key,
    importo                       decimal(12, 2) not null,
    residenza_anni                smallint       not null,
    ID_provincia_res              nvarchar(2)    not null,
    ID_luogo_res                  nvarchar(6)    not null,
    localita_res                  nvarchar(50)   not null,
    indirizzo_res                 nvarchar(50)   not null,
    n_civico_res                  nvarchar(10)   not null,
    piano_res                     nvarchar(10)   not null,
    cap_res                       nvarchar(10)   not null,
    p_ed_res                      nvarchar(10)   not null,
    p_f_res                       nvarchar(10)   not null,
    p_m_res                       nvarchar(10)   not null,
    ID_comune_catastale_res       nvarchar(6)    not null,
    isResidenza                   smallint       not null,
    imm_invalido_res              smallint       not null,
    imm_familiare_res             smallint       not null,
    ID_tp_titolo_giuridico_res    int            not null,
    altri_proprietari_res         nvarchar(255)  not null,
    no_altri_contr                smallint       not null,
    altri_contr                   smallint       not null,
    altri_contr_ente              nvarchar(255)  not null,
    agevolazioni_fiscali          smallint       not null,
    no_contr_LP_1_1991            smallint       not null,
    cert_invalidita               smallint       not null,
    cert_intervento_necessario    smallint       not null,
    cert_perizia_tecnica          smallint       not null,
    cert_spese_tecniche           smallint       not null,
    cert_spese_notarili           smallint       not null,
    cert_relazione_tecnica        smallint       not null,
    ID_tp_invalidita              int            not null,
    spesa_ammessa                 decimal(12, 2) not null,
    ID_provincia_catastale_res    nvarchar(2),
    rendita_catastale_res         decimal(12, 2),
    ID_provincia_na               nvarchar(2),
    ID_luogo_na                   nvarchar(6),
    localita_na                   nvarchar(50),
    indirizzo_na                  nvarchar(50),
    n_civico_na                   nvarchar(10),
    piano_na                      nvarchar(10),
    cap_na                        nvarchar(10),
    p_ed_na                       nvarchar(10),
    p_f_na                        nvarchar(10),
    p_m_na                        nvarchar(10),
    ID_provincia_catastale_na     nvarchar(2),
    ID_comune_catastale_na        nvarchar(6),
    rendita_catastale_na          decimal(12, 2),
    importo_spese_tecniche        decimal(12, 2),
    importo_spese_notarili        decimal(12, 2),
    classe_non_autosufficienza_ok smallint,
    constraint FK_CANA_dati_R_Luoghi
        foreign key (ID_luogo_res, ID_provincia_res) references R_Luoghi
)
go

create table CAT_fabbricati
(
    ID_immobile          int          not null,
    ID_titolarita        int          not null,
    codice_fiscale       nvarchar(16) not null,
    anno                 smallint     not null,
    ID_soggetto_catasto  int          not null,
    cod_catastale        nvarchar(4)  not null,
    categoria            nvarchar(3),
    rendita              decimal(12, 2),
    quota_num            numeric,
    quota_deno           numeric,
    cognome              nvarchar(50),
    nome                 nvarchar(50),
    ID_provincia_nascita nvarchar(2),
    ID_luogo_nascita     nvarchar(6),
    data_nascita         datetime,
    ID_tp_sex            nvarchar,
    ID_provincia_fabb    nvarchar(2),
    ID_luogo_fabb        nvarchar(6),
    ind_fabb             nvarchar(100),
    civico_fabb          nvarchar(100),
    numero_ped           nchar(10)    not null,
    subalterno           smallint,
    ID_comune_catastale  nvarchar(4)  not null,
    foglio               nvarchar(4),
    PM                   nvarchar(50),
    codice_diritto       nvarchar(3),
    numero               nchar(10),
    denominatore         nchar(10),
    consistenza          nvarchar(7),
    superficie           nvarchar(5),
    constraint PK_CAT_fabbricati_2007
        primary key (ID_immobile, ID_titolarita, codice_fiscale, anno),
    constraint FK_CAT_fabbricati_R_Luoghi
        foreign key (ID_luogo_nascita, ID_provincia_nascita) references R_Luoghi,
    constraint FK_CAT_fabbricati_R_Luoghi1
        foreign key (ID_luogo_fabb, ID_provincia_fabb) references R_Luoghi
)
go

create index IX_CAT_fabbricati
    on CAT_fabbricati (anno, codice_fiscale)
go

create index idx_CAF_fab_codfisc_anno
    on CAT_fabbricati (codice_fiscale, anno) include (ID_immobile, ID_titolarita)
go

create table Energia_comuni
(
    ID_luogo         nvarchar(6) not null
        constraint PK_Energia_comuni
            primary key
                with (fillfactor = 95)
        constraint FK_Energia_comuni_R_Luoghi
            references R_Luoghi (ID_luogo),
    fascia_climatica smallint
)
go

create table PRUNI_studente_domicilio
(
    ID_domanda             int not null
        constraint PK_PRUNI_studente_domicilio
            primary key
                with (fillfactor = 95),
    ID_provincia_domicilio nvarchar(2),
    ID_comune_domicilio    nvarchar(6),
    indirizzo_domicilio    nvarchar(50),
    n_civ_domicilio        nvarchar(50),
    cap_domicilio          nvarchar(50),
    nazione_domicilio      nvarchar(50),
    constraint FK_PRUNI_studente_domicilio_R_Luoghi
        foreign key (ID_comune_domicilio, ID_provincia_domicilio) references R_Luoghi
)
go

create table R_CAP
(
    ID_cap   nvarchar(5) not null,
    ID_luogo nvarchar(6) not null
        constraint FK_R_CAP_R_Luoghi
            references R_Luoghi (ID_luogo),
    constraint PK_R_CAP
        primary key (ID_cap, ID_luogo)
            with (fillfactor = 95)
)
go

create table R_Comprensori
(
    ID_luogo        nvarchar(6) not null
        constraint PK_R_Comprensori
            primary key
                with (fillfactor = 95)
        constraint FK_R_Comprensori_R_Luoghi
            references R_Luoghi (ID_luogo),
    ID_comprensorio nchar(4)    not null,
    ID_ente         smallint    not null,
    ID              int         not null
)
go

create table R_Comunita
(
    ID_luogo             nvarchar(6)  not null
        constraint FK_R_Comunita_R_Luoghi
            references R_Luoghi (ID_luogo),
    ID_comunita_di_valle int          not null,
    comunita_di_valle    nvarchar(70) not null,
    codice_ente          nvarchar(15),
    constraint PK_R_Comunita
        primary key (ID_luogo, ID_comunita_di_valle)
            with (fillfactor = 95)
)
go

create table R_GDF
(
    ID_luogo    nvarchar(6) not null
        constraint FK_R_GDF_R_Luoghi
            references R_Luoghi (ID_luogo),
    ID_tenenza  smallint    not null,
    tenenza     nvarchar(50),
    codice_ente nvarchar(15),
    constraint PK_R_GDF
        primary key (ID_luogo, ID_tenenza)
            with (fillfactor = 95)
)
go

create table R_Querys
(
    ID   int not null
        constraint PK_R_Querys
            primary key nonclustered
                with (fillfactor = 95),
    sql  nvarchar(4000),
    note text
)
go

create table R_Referenti_ComprensoriComuni
(
    ID_referente smallint      not null
        constraint PK_R_Referenti_ComprensoriComuni
            primary key
                with (fillfactor = 95),
    ID_ente      int           not null,
    cognome      nvarchar(50)  not null,
    nome         nvarchar(50)  not null,
    email        nvarchar(100) not null
)
go

create table R_Regioni
(
    ID_regione     nvarchar(3) not null
        constraint PK_R_regioni
            primary key nonclustered
                with (fillfactor = 95),
    regione        nvarchar(50),
    codice_dm_1986 int
)
go

create table R_ASL
(
    ID_regione nvarchar(3) not null
        constraint FK_R_ASL_R_Regioni
            references R_Regioni,
    ID_ASL     nvarchar(6) not null
        constraint PK_R_ASL
            primary key
                with (fillfactor = 95),
    asl        nvarchar(50),
    regione    nvarchar(50)
)
go

create table R_Relazioni_parentela_duplicati
(
    ID_servizio                int not null,
    ID_periodo                 int not null,
    ID_relazione_parentela     int not null,
    ID_servizio_new            int not null,
    ID_periodo_new             int not null,
    ID_relazione_parentela_new int not null,
    generazione_new            int
)
go

create table R_Responsabili_copia
(
    ID_user     int not null,
    ID_servizio int not null
)
go

create table R_Responsabili_dati
(
    ID_servizio       int not null,
    ID_ente           int not null,
    cognome           nvarchar(15),
    nome              nvarchar(15),
    persona_giuridica nvarchar(250),
    constraint PK_R_Responsabili_dati
        primary key (ID_servizio, ID_ente)
            with (fillfactor = 95)
)
go

create table R_ServiziHTML
(
    ID_servizio int           not null
        constraint PK_R_ServiziHTML
            primary key
                with (fillfactor = 95),
    classe      nvarchar(100) not null,
    nomePagina  nvarchar(100) not null,
    idCreator   nvarchar(100) not null,
    dbDirect    nvarchar(100) not null,
    nomeElabora nvarchar(100) not null,
    enabled     int           not null
)
go

create table R_Servizi_duplicati
(
    ID_servizio               int          not null,
    ID_periodo                int          not null,
    servizio_old              nvarchar(100),
    net                       nvarchar(50) not null,
    ID_servizio_new           int          not null,
    ID_periodo_new            int          not null,
    servizio_new              nvarchar(100),
    net_new                   nvarchar(50),
    generazione_new           int,
    duplicaReportTOT          int,
    duplicaReportABILITAZIONI int,
    beforeReportReplace       nvarchar(250),
    afterReportReplace        nvarchar(250),
    duplicaReportTODO         int,
    duplicaReportDaProcedura  int,
    constraint PK_R_Servizi_duplicati
        primary key (ID_servizio, ID_periodo, net, ID_servizio_new, ID_periodo_new)
            with (fillfactor = 95)
)
go

create table R_Servizi_tabelle_coinvolte
(
    ID_servizio int           not null,
    ID_periodo  int           not null,
    posizione   int           not null,
    tabella     nvarchar(100) not null,
    colonna     nvarchar(100) not null,
    order_by    smallint      not null,
    constraint PK_R_Servizi_tabelle_coinvolte
        primary key (ID_servizio, ID_periodo, posizione)
)
go

create table R_Servlet
(
    servlet_name  nvarchar(100) not null
        constraint PK_R_Servlet
            primary key
                with (fillfactor = 95),
    servlet_class nvarchar(250) not null,
    enabled       int,
    loaded_time   datetime
)
go

create table R_Servlet_mappings
(
    servlet_name    nvarchar(100) not null
        constraint FK_R_Servlet_mapping_R_Servlet
            references R_Servlet,
    servlet_mapping nvarchar(250) not null,
    constraint PK_R_Servlet_mapping
        primary key (servlet_name, servlet_mapping)
            with (fillfactor = 95)
)
go

create table R_Servlet_params
(
    servlet_name nvarchar(100)  not null
        constraint FK_R_Servlet_params_R_Servlet
            references R_Servlet,
    param_name   nvarchar(250)  not null,
    param_value  nvarchar(1250) not null,
    constraint PK_R_Servlet_params
        primary key (servlet_name, param_name)
            with (fillfactor = 95)
)
go

create table R_Superservizi
(
    id_superservizio   int not null
        constraint PK_R_Superservizi
            primary key
                with (fillfactor = 95),
    superservizio      nvarchar(250),
    descrizione        nvarchar(250),
    alias              nvarchar(50),
    domicilio_digitale smallint
)
go

create table R_Uffici
(
    ID_ente        int      not null
        constraint FK_R_Uffici_R_Enti
            references R_Enti,
    ID_ufficio     int      not null,
    ufficio        nvarchar(50),
    indirizzo      nvarchar(50),
    tel            nvarchar(20),
    email          nvarchar(50),
    ftp            nvarchar(80),
    enabled        smallint not null,
    luogo          nvarchar(50),
    prov           nvarchar(50),
    cap            nvarchar(6),
    pref           nvarchar(5),
    regione        nvarchar(50),
    codice_ufficio nvarchar(50),
    constraint PK_R_Uffici
        primary key nonclustered (ID_ente, ID_ufficio)
            with (fillfactor = 95)
)
go

create table R_Utenti
(
    ID_user                                int          not null
        constraint PK_R_Utenti
            primary key
                with (fillfactor = 90),
    username                               nvarchar(15) not null,
    descrizione                            nvarchar(30) not null,
    password                               nvarchar(50) not null,
    privilegi                              nvarchar(30) not null,
    gruppo                                 nvarchar(50),
    ID_ente                                int
        constraint FK_R_Utenti_R_Enti
            references R_Enti,
    ID_ufficio                             int,
    enabled                                smallint     not null,
    tel                                    nvarchar(20),
    fax                                    nvarchar(20),
    email                                  nvarchar(100),
    data_modifica_pw                       datetime,
    data_ultimo_accesso                    datetime,
    responsabileEnte                       smallint     not null,
    visualizzaPratiche_no_resp             smallint,
    email_cer                              nvarchar(100),
    accesso_senza_applet                   smallint     not null,
    invio_mail                             smallint     not null,
    abilitato_procedura_firma_grafometrica int          not null,
    cf                                     nvarchar(16),
    id_user_parent                         int,
    collaboratore                          smallint,
    constraint FK_R_Utenti_R_Uffici
        foreign key (ID_ente, ID_ufficio) references R_Uffici
)
go

exec sp_addextendedproperty 'MS_Description', 'Il numero di fax dell''utente', 'SCHEMA', 'dbo', 'TABLE', 'R_Utenti',
     'COLUMN', 'fax'
go

create table AUP_file_upload
(
    ID_file            int identity
        constraint PK_AUP_file_upload
            primary key
                with (fillfactor = 95),
    ID_ente            int           not null
        constraint FK_AUP_file_upload_R_Enti
            references R_Enti,
    nome_file          nvarchar(200) not null,
    data_upload        datetime      not null,
    ID_user            int           not null
        constraint FK_AUP_file_upload_R_Utenti
            references R_Utenti,
    file_aup           text          not null,
    content_type       nvarchar(100) not null,
    ID_tp_stato_upload smallint      not null
        constraint FK_AUP_file_upload_AUP_tp_stati_upload
            references AUP_tp_stati_upload,
    message            nvarchar(4000)
)
go

create table AUP_flussi
(
    id_rendicontazione    int identity
        constraint PK_AUP_flussi
            primary key
                with (fillfactor = 95),
    id_entita             int          not null,
    anno                  int          not null,
    id_blocco_flusso      int,
    codice_fiscale        nvarchar(16) not null,
    idPersona             decimal(12),
    nome                  nvarchar(100),
    cognome               nvarchar(100),
    id_tp_rendicontazione int          not null
        constraint FK_AUP_flussi_AUP_tp_rendicontazione
            references AUP_tp_rendicontazione,
    tariffa               decimal(8, 2),
    quantita              decimal(4),
    dal                   datetime,
    al                    datetime,
    codice_fiscale_flusso nvarchar(16),
    ID_file               int
        constraint FK_AUP_flussi_AUP_file_upload
            references AUP_file_upload,
    pagato                smallint
)
go

create table Avvisi_flags
(
    ID_avviso int not null
        constraint FK_Avvisi_flags_Avvisi
            references Avvisi,
    ID_user   int not null
        constraint FK_Avvisi_flags_R_Utenti
            references R_Utenti,
    constraint PK_Avvisi_flags
        primary key (ID_avviso, ID_user)
            with (fillfactor = 95)
)
go

create table Azioni_domande
(
    ID        int      not null,
    ID_azione int      not null
        constraint FK_Azioni_domande_Azioni
            references Azioni,
    data      datetime not null,
    ID_user   int
        constraint FK_Azioni_domande_R_Utenti
            references R_Utenti,
    importo   float
)
go

create clustered index CI_Azioni_domande_id_domanda
    on Azioni_domande (ID)
    with (fillfactor = 95)
go

create index IDX_Azioni_domande_ID_azione
    on Azioni_domande (ID_azione) include (ID)
    with (fillfactor = 95)
go

create table LAV_file_upload
(
    ID_file            int identity (0, 1)
        constraint LAV_file_upload_PK
            primary key,
    ID_ente            int           not null
        constraint LAV_file_upload_R_Enti_FK
            references R_Enti,
    nome_file          nvarchar(200) not null,
    data_upload        datetime      not null,
    ID_user            int           not null
        constraint LAV_file_upload_R_Utenti_FK
            references R_Utenti,
    file_lav           text          not null,
    content_type       nvarchar(100) not null,
    ID_tp_stato_upload smallint      not null
        constraint LAV_file_upload_LAV_tp_stati_upload_FK
            references LAV_tp_stati_upload,
    message            nvarchar(4000)
)
go

create table LAV_occupazioni_caricate
(
    codice_fiscale    nvarchar(16)  not null,
    anno              int           not null,
    ID_servizio       int,
    ID_consorzio      int,
    ID_tp_cooperativa int           not null,
    ID_tp_mansione    int,
    INPS_SCAU         nvarchar(10)  not null,
    data_dal          datetime      not null,
    data_al           datetime      not null,
    caposquadra       int,
    confermato        smallint,
    ditta             nvarchar(250) not null,
    note              nvarchar(250) not null,
    import_dati       datetime,
    verifica_apss     int,
    new_cf            nvarchar(16),
    id_code_rif       int,
    ID_file           int
        constraint FK_LAV_occupazioni_caricate_LAV_file_upload
            references LAV_file_upload,
    constraint LAV_occupazioni_caricate_FK_LAV_pt_cooperative
        foreign key (ID_servizio, ID_consorzio, ID_tp_cooperativa) references LAV_tp_cooperative,
    constraint LAV_occupazioni_caricate_FK_LAV_tp_mansioni
        foreign key (ID_servizio, ID_tp_mansione) references LAV_tp_mansioni
)
go

create table RECCRED_file_upload
(
    ID_file                    int identity
        constraint PK_RECCRED_file_upload
            primary key
                with (fillfactor = 95),
    ID_ente                    int           not null
        constraint FK_RECCRED_file_upload_R_Enti
            references R_Enti,
    nome_file                  nvarchar(200) not null,
    data_upload                datetime      not null,
    ID_user_upload             int           not null
        constraint FK_RECCRED_file_upload_R_Utenti
            references R_Utenti,
    data_validazione           datetime,
    ID_user_validazione        int
        constraint FK_RECCRED_file_upload_R_Utenti1
            references R_Utenti,
    data_acquisizione_apapi    datetime,
    ID_user_acquisizione_apapi int
        constraint FK_RECCRED_file_upload_R_Utenti2
            references R_Utenti,
    file_reccred               text          not null,
    content_type               nvarchar(100) not null,
    ID_tp_stato_upload         smallint      not null
        constraint FK_RECCRED_file_upload_RECCRED_tp_stati_upload
            references RECCRED_tp_stati_upload,
    message                    nvarchar(4000)
)
go

create table RECCRED_flussi
(
    id_rendicontazione             int identity
        constraint PK_RECCRED_flussi
            primary key,
    ID_file                        int           not null
        constraint FK_RECCRED_flussi_RECCRED_file_upload
            references RECCRED_file_upload,
    codice_fiscale_flusso          nvarchar(16)  not null,
    codice_fiscale                 nvarchar(16)  not null,
    nome                           nvarchar(35),
    cognome                        nvarchar(35),
    importo                        decimal(8, 2) not null,
    validato                       smallint      not null,
    acquisito_apapi                smallint      not null,
    ID_domanda_aup                 int,
    nome_richiedente_aup           nvarchar(35),
    cognome_richiedente_aup        nvarchar(35),
    codice_fiscale_richiedente_aup nvarchar(16),
    qB1L                           decimal(8, 2) not null,
    importo_recuperato             decimal(8, 2) not null,
    recuperato_apapi               smallint      not null
)
go

create table REPORT_LOG
(
    LOG_ID     int identity
        primary key
            with (fillfactor = 95),
    START_TIME datetime,
    END_TIME   datetime,
    STATUS     varchar(255),
    MESSAGE    text,
    REPORT_ID  int
        constraint FK901BE599AAEF4A13
            references REPORT,
    USER_ID    int
        constraint FK_REPORT_LOG_R_Utenti
            references R_Utenti,
    ALERT_ID   int
        constraint FK901BE59920DA4A2D
            references REPORT_ALERT
)
go

create index ID_Enti_R_Utenti
    on R_Utenti (ID_ente)
    with (fillfactor = 90)
go

create unique index username
    on R_Utenti (username)
    with (fillfactor = 90)
go

create table R_Variabili_Sistema
(
    nome   nvarchar(50)   not null
        constraint PK_R_Variabili_Sistema
            primary key
                with (fillfactor = 95),
    valore nvarchar(4000) not null
)
go

create table R_Zone_agricole
(
    ID_zona_agricola int          not null,
    zona_agricola    nvarchar(30) not null,
    ID_dich          int          not null,
    constraint PK_R_zone_agricole
        primary key (ID_dich, ID_zona_agricola)
            with (fillfactor = 95)
)
go

create table R_comuni_cat_openkat
(
    ID_comune_catastale nvarchar(4)  not null,
    comune_catastale    nvarchar(40) not null
)
go

create table R_comuni_catastali
(
    ID_comune_catastale   nvarchar(4)  not null
        constraint PK_R_comuni_catastali_1
            primary key
                with (fillfactor = 95),
    comune_catastale      nvarchar(40) not null,
    ufficio_catastale     nvarchar(40),
    cod_catastale         nvarchar(4)  not null,
    ID_luogo              nvarchar(40) not null,
    catasto_trento        smallint     not null,
    note                  nvarchar(50),
    data_modifica         datetime,
    ID_luogo_vecchio      nvarchar(40),
    cod_catastale_vecchio nvarchar(4),
    mancante              smallint     not null
)
go

create table R_comuni_catastali_openkat
(
    ID_comune_catastale   nvarchar(4)   not null,
    comune_catastale      nvarchar(100) not null,
    comune_amministrativo nvarchar(100) not null,
    ufficio_catastale     nvarchar(100) not null,
    cod_catastale         nvarchar(4)   not null,
    catasto_trento        smallint,
    note                  nvarchar(50),
    data_modifica         datetime
)
go

create table R_eta_ultra65
(
    ID_eta_ultra65 int not null
        constraint PK_R_eta_ultra65
            primary key
                with (fillfactor = 95),
    mesi_da_65     int not null,
    data_da        datetime,
    data_a         datetime
)
go

create table R_ricalcolo_blocco
(
    id_blocco                 int identity,
    id_blocco_riferimento     int,
    id_operazione             int,
    descrizione               nvarchar(300),
    net                       nvarchar(200) not null,
    Input_elab                smallint,
    NodeOutTable              nvarchar(50),
    ElabTable                 nvarchar(50),
    DefaultInTable            nvarchar(50),
    ElaInTable                nvarchar(50),
    ElaOutTable               nvarchar(50),
    NodeInTable               nvarchar(50),
    Time_start                datetime,
    Time_end                  datetime,
    Mid_time_elab             int,
    stato                     int           not null,
    ripristinoForzatoDichICEF smallint      not null,
    id_servizio               int,
    id_periodo                int,
    test_result               text,
    lock                      int
)
go

create table R_ricalcolo_cache
(
    id_domanda int not null,
    id_blocco  int not null,
    constraint PK_R_ricalcolo_cache
        primary key (id_domanda, id_blocco)
            with (fillfactor = 95)
)
go

create index _dta_index_R_ricalcolo_cache_7_136439610__K2_K1
    on R_ricalcolo_cache (id_blocco, id_domanda)
    with (fillfactor = 95)
go

create table R_ricalcolo_estensione
(
    id_operazione  int identity,
    sql_operazione text          not null,
    descrizione    nvarchar(200) not null,
    net            nvarchar(200),
    tipo           int           not null
)
go

create table R_ricalcolo_superblocco
(
    ID_superblocco             int           not null,
    ID_blocco                  int           not null,
    ID_riferimento_superblocco int,
    request                    nvarchar(250) not null,
    descrizione                nvarchar(200),
    constraint PK_R_ricalcolo_superblocco
        primary key (ID_superblocco, ID_blocco)
            with (fillfactor = 95)
)
go

create table R_superservizio_temp
(
    ID_servizio      int,
    servizio         nvarchar(100),
    ID_superservizio int,
    superservizio    nvarchar(100),
    ALIAS            nvarchar(50)
)
go

create table Redditi_attualizzati
(
    ID_dichiarazione                          int            not null
        constraint PK_Redditi_attualizzati
            primary key,
    ID_dich                                   int,
    ID_tp_reddito_attualizzato                int,
    anno_rif_redd                             int,
    data_rif_patr                             datetime,
    denominazione                             nvarchar(150),
    indirizzo                                 nvarchar(150),
    mensilita_antecedente                     decimal(12, 2),
    mensilita_2antecedente                    decimal(12, 2),
    dipendente                                smallint       not null,
    lavoro_autonomo                           smallint       not null,
    att_integrativa                           smallint       not null,
    riduzione_orario                          smallint       not null,
    att_dopo_disoccupaz                       smallint       not null,
    disocc_antecedente                        smallint       not null,
    disocc_succ_dip                           smallint       not null,
    disocc_succ_no_dip                        smallint       not null,
    disocc_succ_no_aut                        smallint       not null,
    cassa_integrazione                        smallint       not null,
    att_dopo_disoccupaz_parasub               smallint       not null,
    disocc_scad_contr                         smallint       not null,
    ammortizzatori_sociali                    smallint       not null,
    pens_reversibilita                        smallint       not null,
    altre_entrate                             smallint       not null,
    percezione_altri_redditi                  smallint       not null,
    perc_redd_mensilita_antecedente           decimal(12, 2) not null,
    perc_redd_mensilita_2antecedente          decimal(12, 2) not null,
    percezione_altri_redditi_invalidi         smallint       not null,
    perc_redd_mensilita_antecedente_invalidi  decimal(12, 2) not null,
    perc_redd_mensilita_2antecedente_invalidi decimal(12, 2) not null
)
go

create table Resoconto
(
    ID_utente               int      not null,
    ID_dichiarazione        int      not null,
    NuovaICEF               smallint not null,
    NuovaNullatenenti       smallint not null,
    DuplicaICEF             smallint not null,
    DuplicaICEFNullatenenti smallint not null,
    Modifica                int      not null,
    Anno                    int      not null,
    Semestre                int      not null,
    modifica_utente         int,
    attualizzata            int      not null,
    constraint PK_Resoconto
        primary key (ID_dichiarazione, Anno, Semestre)
            with (fillfactor = 90)
)
go

create index _dta_index_Resoconto_7_1831729628__K9_K8_K1_K2_3_4_5_6_7_10_11
    on Resoconto (Semestre, Anno, ID_utente, ID_dichiarazione) include (NuovaICEF, NuovaNullatenenti, DuplicaICEF,
                                                                        DuplicaICEFNullatenenti, Modifica,
                                                                        modifica_utente, attualizzata)
go

create table Richiedenti
(
    ID_domanda             int not null
        constraint PK_Richiedenti_1
            primary key
                with (fillfactor = 95),
    ID_soggetto            int not null,
    ID_tp_titolo           int,
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    cap_residenza          nvarchar(10),
    ID_comune_residenza    nvarchar(6),
    ID_provincia_residenza nvarchar(2),
    telefono               nvarchar(20),
    e_mail                 nvarchar(50),
    constraint FK_Richiedenti_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table Richiedenti_tp_titoli
(
    ID_tp_titolo int not null
        constraint PK_Richiedenti_tp_titoli
            primary key
                with (fillfactor = 95)
        constraint FK_Richiedenti_tp_titoli_Richiedenti_tp_titolo
            references Richiedenti_tp_titoli,
    tp_titolo    nvarchar(250)
)
go

create table Richiedenti_tp_titolo
(
    ID_servizio  int           not null,
    ID_tp_titolo int           not null,
    tp_titolo    nvarchar(200) not null,
    constraint PK_Richiedenti_tp_titolo
        primary key (ID_servizio, ID_tp_titolo)
)
go

create table Richieste_certificazione
(
    ID_richiesta   int not null
        constraint PK_RichiesteCertificazione
            primary key nonclustered
                with (fillfactor = 95),
    data_richiesta datetime,
    ID_user        int,
    data_creazione datetime,
    ora_creazione  nvarchar(8),
    identificatore nvarchar(200),
    xml            nvarchar(200)
)
go

create table Richieste_certificazione_lista
(
    ID_richiesta    int not null,
    ID_domanda      int not null,
    esito_controllo nvarchar(4000),
    constraint PK_Richieste_certificazione_lista
        primary key (ID_richiesta, ID_domanda)
            with (fillfactor = 90)
)
go

create table Richieste_certificazione_prestazioni
(
    ID_richiesta   int not null,
    ID_domanda     int not null,
    tipo           nvarchar(50),
    specificaAltro nvarchar(50)
)
go

create table Richieste_prenotate_sblocco
(
    ID_domanda           int      not null,
    data_prenota_sblocco datetime not null,
    ID_user              int      not null,
    da_confermare        smallint not null,
    constraint PK_Richieste_prenot
        primary key (ID_domanda, data_prenota_sblocco)
            with (fillfactor = 95)
)
go

create table Richieste_sblocco
(
    ID_dichiarazione                 int      not null,
    ID_domanda                       int      not null,
    data_richiesta_sblocco           datetime not null,
    ID_user                          int      not null,
    ID_tp_stato_dich_prima           int,
    ID_tp_stato_prima                int,
    beneficio_prima                  float,
    ID_tp_stato_dich_dopo            int,
    ID_tp_stato_dopo                 int,
    beneficio_dopo                   float,
    data_ripristino                  datetime,
    ripristino_forzato               smallint not null,
    edizione_dom_prima               int,
    edizione_dom_dopo                int,
    edizione_dich_prima              int,
    edizione_dich_dopo               int,
    ripristino_da_dich_non_veritiera int      not null,
    constraint PK_Richieste_sblocco
        primary key (ID_dichiarazione, ID_domanda, data_richiesta_sblocco)
            with (fillfactor = 90)
)
go

create table Richieste_sblocco_sql
(
    ID_dichiarazione       int      not null,
    ID_domanda             int      not null,
    data_richiesta_sblocco datetime not null,
    posizione              int      not null,
    sql                    ntext    not null,
    posizione_sql          int      not null,
    crc                    int      not null,
    constraint PK_Richieste_sblocco_sql
        primary key (ID_dichiarazione, ID_domanda, data_richiesta_sblocco, posizione)
)
go

create table SAN_R_Anni_accademici
(
    ID_anno_accademico int          not null
        constraint PK_SAN_R_Anni_accademici
            primary key
                with (fillfactor = 95),
    anno_accademico    nvarchar(10) not null
)
go

create table SAN_R_Sedi
(
    ID_anno_accademico int         not null,
    ID_sede_corso      nvarchar(6) not null,
    ID_sede            int         not null,
    sede               nvarchar(50),
    constraint PK_SAN_R_Sedi
        primary key (ID_anno_accademico, ID_sede_corso)
            with (fillfactor = 95)
)
go

create table SAN_R_Comuni_in_sede
(
    ID_anno_accademico int         not null
        constraint FK_SAN_R_Comuni_in_sede_SAN_R_Anni_accademici
            references SAN_R_Anni_accademici,
    ID_sede_corso      nvarchar(6) not null,
    ID_comune          nvarchar(6) not null,
    constraint PK_SAN_R_Comuni_in_sede
        primary key (ID_anno_accademico, ID_sede_corso, ID_comune)
            with (fillfactor = 95),
    constraint FK_SAN_R_Comuni_in_sede_SAN_R_Sedi
        foreign key (ID_anno_accademico, ID_sede_corso) references SAN_R_Sedi
)
go

create table SAN_tp_corso
(
    ID_tp_corso        smallint not null,
    ID_anno_accademico int      not null,
    tp_corso           nvarchar(5),
    ordinamento        nvarchar(200),
    constraint PK_SAN_tp_corso
        primary key (ID_tp_corso, ID_anno_accademico)
            with (fillfactor = 95)
)
go

create table SAN_Merito
(
    ID_anno_accademico int      not null,
    ID_tp_corso        smallint not null,
    anno_prima_imm     int      not null,
    minimo_deroga      float,
    minimo             float,
    massimo            float,
    minimo_premio      float,
    constraint PK_SAN_Merito
        primary key (ID_anno_accademico, ID_tp_corso, anno_prima_imm)
            with (fillfactor = 95),
    constraint FK_SAN_Merito_SAN_tp_corso
        foreign key (ID_tp_corso, ID_anno_accademico) references SAN_tp_corso
)
go

create table SAN_R_Corsi
(
    ID_corso           int           not null,
    ID_anno_accademico int           not null,
    corso              nvarchar(250) not null,
    tipo               char(4),
    anni_corso         int,
    anni_aggiuntivi    decimal(5, 2),
    ID_tp_corso        smallint,
    ordinamento        nvarchar(200),
    constraint PK_SAN_R_Corsi
        primary key (ID_corso, ID_anno_accademico)
            with (fillfactor = 95),
    constraint FK_SAN_R_Corsi_SAN_tp_corso
        foreign key (ID_tp_corso, ID_anno_accademico) references SAN_tp_corso
)
go

create table SAN_tp_iscrizione
(
    ID_tp_iscrizione int          not null
        constraint PK_SAN_tp_iscrizione
            primary key
                with (fillfactor = 95),
    tp_iscrizione    nvarchar(50) not null
)
go

create table SAN_tp_nucleo
(
    ID_tp_nucleo smallint not null
        constraint PK_SAN_tp_nucleo
            primary key
                with (fillfactor = 95),
    tp_nucleo    nvarchar(50)
)
go

create table SCUOLE_tp_classi
(
    ID_tp_classe smallint     not null
        constraint PK_SCUOLE_tp_classi
            primary key
                with (fillfactor = 95),
    tp_classe    nvarchar(10) not null
)
go

create table SOGGETTI_CAT_TEMP
(
    ID_SOGGETTO_CAT int,
    CODICE_FISCALE  nvarchar(16)
)
go

create table SPORT_tp_classifica
(
    ID_tp_classifica smallint     not null
        constraint PK_SPORT_classifica
            primary key
                with (fillfactor = 95),
    ID_servizio      int          not null,
    classifica       nvarchar(20) not null,
    punti            smallint     not null,
    posizione        smallint
)
go

create table SPORT_tp_disciplina
(
    ID_tp_disciplina smallint not null,
    ID_servizio      int      not null,
    tp_disciplina    nvarchar(50),
    indice_frequenza float,
    ordine           smallint,
    constraint PK_SPORT_tp_disciplina
        primary key (ID_tp_disciplina, ID_servizio)
            with (fillfactor = 95)
)
go

create table SPORT_tp_rappresentativa
(
    ID_tp_rappresentativa int       not null
        constraint PK_SPORT_tp_rappresentative
            primary key
                with (fillfactor = 95),
    tp_rappresentativa    nchar(50) not null,
    ID_servizio           int       not null
)
go

create table SPORT_tp_sottoscrittore
(
    ID_tp_sottoscrittore smallint      not null,
    tp_sottoscrittore    nvarchar(250) not null
)
go

create table STUD_R_luoghi_enti_nidi
(
    ID_ente  int         not null,
    ID_luogo nvarchar(6) not null,
    constraint PK_STUD_R_luoghi_enti_nidi
        primary key (ID_ente, ID_luogo)
            with (fillfactor = 95)
)
go

create table STUD_R_scuole
(
    ID_scuola     smallint not null
        constraint PK_STUD_R_scuole
            primary key
                with (fillfactor = 95),
    descrizione   nvarchar(255),
    scuola        nvarchar(255),
    denominazione nvarchar(255),
    luogo         nvarchar(255),
    tp_scuola     nvarchar(255),
    codice_sae    nvarchar(10)
)
go

create table STUD_Studenti_Variazioni_1
(
    ID_STUD_Studenti_Variazioni int identity
        constraint PK_STUD_Studenti_Variazioni_1_1
            primary key,
    data                        xml
)
go

create table STUD_dati_famiglia
(
    ID_domanda                     int not null
        constraint PK_STUD_dati_famiglia
            primary key
                with (fillfactor = 95),
    impegno_riconoscimento_assegno smallint,
    no_assegno_in_c5               smallint,
    giudiziali                     nvarchar(max),
    non_giudiziali                 nvarchar(max)
)
go

create table STUD_esiti
(
    ID_domanda      int          not null,
    ID_soggetto     int          not null,
    ID_tp_beneficio nvarchar(50) not null,
    importo         float        not null,
    constraint PK_STUD_esiti
        primary key (ID_domanda, ID_soggetto, ID_tp_beneficio)
            with (fillfactor = 95)
)
go

create table STUD_esiti_storico
(
    data_richiesta_sblocco datetime     not null,
    ID_domanda             int          not null,
    ID_soggetto            int          not null,
    ID_tp_beneficio        nvarchar(50) not null,
    importo                float        not null,
    constraint PK_STUD_esiti_storico
        primary key (data_richiesta_sblocco, ID_domanda, ID_soggetto, ID_tp_beneficio)
            with (fillfactor = 95)
)
go

create table STUD_tp_scuola
(
    ID_tp_scuola smallint     not null
        constraint PK_STUD_tp_scuola
            primary key
                with (fillfactor = 95),
    tp_scuola    nvarchar(50) not null,
    ordine       smallint     not null,
    eta_min      int,
    eta_max      int
)
go

create table STUD_tp_tariffe
(
    ID_tp_tariffa smallint     not null
        constraint PK_STUD_tp_tariffe
            primary key
                with (fillfactor = 95),
    tp_tariffa    nvarchar(50) not null
)
go

create table STUD_tp_beneficio
(
    ID_tp_beneficio nvarchar(50) not null,
    ID_tp_tariffa   smallint     not null
        constraint FK_STUD_tp_beneficio_STUD_tp_tariffe
            references STUD_tp_tariffe,
    descrizione     nvarchar(250),
    tipo_importo    nvarchar(50),
    colonna_report  nvarchar(250),
    constraint PK_STUD_tp_beneficio_1
        primary key (ID_tp_beneficio, ID_tp_tariffa)
)
go

create table STUD_tp_validazione_esito
(
    id_validazione_esito int not null
        constraint PK_tp_validazione_esito
            primary key
                with (fillfactor = 95),
    validazione_esito    nvarchar(255)
)
go

create table S_Azioni_domande
(
    id_scenario int      not null,
    ID          int      not null,
    ID_azione   int      not null
        constraint S_FK_Azioni_domande_Azioni
            references Azioni,
    data        datetime not null,
    ID_user     int
        constraint S_FK_Azioni_domande_R_Utenti
            references R_Utenti,
    importo     float,
    constraint S_PK_Azioni_domande
        primary key nonclustered (id_scenario, ID, ID_azione, data)
            with (fillfactor = 95)
)
go

create table S_C_Confronto
(
    stream       varchar(50),
    id_domanda   int,
    net          varchar(50),
    node         varchar(50),
    value_before float,
    value_after  float,
    dif          float
)
go

create table S_C_Elab
(
    id_scenario int          not null,
    id_domanda  int          not null,
    net         nvarchar(50) not null,
    elab_date   smalldatetime,
    fingerprint nvarchar(50),
    checkfp     smallint,
    n_elab      int,
    constraint PK_S_C_Elab
        primary key (id_scenario, id_domanda, net)
            with (fillfactor = 95)
)
go

create table S_C_ElaIN
(
    id_scenario int          not null,
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_S_C_ElaIN
        primary key (id_scenario, ID_domanda, net, node)
            with (fillfactor = 95),
    constraint FK_S_C_ElaIN_S_C_Elab
        foreign key (id_scenario, ID_domanda, net) references S_C_Elab
)
go

create table S_C_ElaOUT
(
    id_scenario   int          not null,
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    constraint PK_S_C_ElaOUT
        primary key (id_scenario, ID_domanda, net, node)
            with (fillfactor = 95),
    constraint FK_S_C_ElaOUT_S_C_Elab
        foreign key (id_scenario, ID_domanda, net) references S_C_Elab
)
go

create table Siuss_da_inviare
(
    codice_fiscale   nvarchar(16) not null,
    id_superservizio int          not null,
    constraint PK_Siuss_da_inviare
        primary key (codice_fiscale, id_superservizio)
)
go

create table Siuss_tp_invciv
(
    ID_tp_invciv int not null
        constraint PK_Siuss_tp_invciv
            primary key
                with (fillfactor = 95),
    tp_invciv    nvarchar(200)
)
go

create table Siuss_tp_invii_dati
(
    ID_tp_invio_dati int not null
        constraint PK_Siuss_tp_invii_dati
            primary key
                with (fillfactor = 95),
    tp_invio_dati    nvarchar(100)
)
go

create table Siuss_tp_prese_carico
(
    ID_tp_presa_carico int not null
        constraint PK_Siuss_tp_prese_carico
            primary key
                with (fillfactor = 95),
    tp_presa_carico    nvarchar(100)
)
go

create table Siuss_tp_presenze_prova_mezzi
(
    ID_tp_presenza_prova_mezzi int not null
        constraint PK_Siuss_tp_presenze_prova_mezzi
            primary key
                with (fillfactor = 95),
    tp_presenza_prova_mezzi    nvarchar(200)
)
go

create table Siuss_trasmissioni
(
    identificazione_flusso        nvarchar(50) not null
        constraint PK_Siuss_trasmissioni
            primary key
                with (fillfactor = 95),
    xml_generato                  xml,
    data_generazione              datetime,
    ID_domanda                    int,
    data_pagamento                datetime,
    ID_servizio                   int,
    ID_periodo                    int,
    ID_ente_erogatore             int,
    ID_beneficio                  int,
    esito                         nvarchar(20),
    messaggio                     text,
    codice_ente                   nvarchar(10),
    denominazione_ente            nvarchar(100),
    indirizzo_ente                nvarchar(4),
    cf_operatore                  nvarchar(16),
    codice_fiscale                nvarchar(16),
    cognome                       nvarchar(128),
    nome                          nvarchar(128),
    anno_nascita                  int,
    luogo_nascita                 nvarchar(4),
    genere                        int,
    cittadinanza                  nvarchar(50),
    cittadinanza_seconda          nvarchar(50),
    regione_residenza             nvarchar(2),
    comune_residenza              nvarchar(6),
    nazione_residenza             int,
    tipo_operazione               nvarchar,
    presenza_prova_mezzi          int,
    carattere_prestazione         int,
    prot_dsu                      nvarchar(30),
    anno_dsu                      int,
    data_dsu                      date,
    codice_prestazione            nvarchar(10),
    denominazione_prestazione     nvarchar(200),
    protocollo_domanda            nvarchar(32),
    data_evento                   date,
    data_inizio_prestazione       date,
    data_fine_prestazione         date,
    data_erogazione_prestazione   date,
    importo_prestazione           numeric(18, 2),
    periodo_erogazione            int,
    importo_mensile               numeric(18, 2),
    importo_quota_ente            numeric(18, 2),
    importo_quota_utente          numeric(18, 2),
    importo_quota_ssn             numeric(18, 2),
    importo_quota_richiesta       numeric(18, 2),
    soglia_isee                   numeric(18, 2),
    ore_servizio_mensile          numeric(18, 2),
    presa_carico                  int,
    area_utenza                   int,
    mobilita                      int,
    attivita_vita_quotidiana      int,
    disturbi_area_cognitiva       int,
    disturbi_comportamentali      int,
    necessita_cure_sanitarie      int,
    area_reddituale               int,
    area_supporto                 int,
    fonte_derivazione_valutazione int,
    strumento_valutazione         int,
    inv_civ                       nvarchar(200),
    fonte_derivazione_invalidita  int,
    necessita_interventi_sociali  nvarchar(200),
    ID_conf                       int
)
go

create table Soggetti_apapi_azioni_domande
(
    ID        int,
    ID_azione int,
    data      datetime,
    ID_user   int,
    importo   float
)
go

create table Soggetti_apapi_doc_backup
(
    ID                    int not null
        constraint PK_Soggetti_apapi_backup_doc_backup
            primary key
                with (fillfactor = 95),
    ID_servizio           int not null,
    ID_user               int not null,
    data_presentazione    datetime,
    ID_tp_stato           int not null,
    ID_tp_stato_doc       smallint,
    protocollo_ente       nvarchar(50),
    luogo_attestazione    nvarchar(50),
    data_attestazione     datetime,
    luogo_sottoscrizione  nvarchar(50),
    data_sottoscrizione   datetime,
    errors                nvarchar(50),
    crc_residenza         int,
    crc_altro_recapito    int,
    crc_pagamento         int,
    var_residenza         smallint,
    var_altro_recapito    smallint,
    var_pagamento         smallint,
    note                  nvarchar(200),
    ID_tp_variazione      smallint,
    ID_domanda_aggiornata int
)
go

create table Soggetti_apapi_sottoscrittore
(
    ID                          int          not null
        constraint PK_Soggetti_apapi_sottoscrittore
            primary key
                with (fillfactor = 95),
    ID_soggetto                 int          not null,
    ID_tp_motivo_sottoscrizione nvarchar(8)  not null,
    ID_provincia_residenza      nvarchar(2)  not null,
    ID_comune_residenza         nvarchar(6)  not null,
    indirizzo_residenza         nvarchar(50) not null,
    n_civ_residenza             nvarchar(10) not null,
    cap_residenza               nvarchar(10) not null,
    telefono_residenza          nvarchar(20),
    constraint FK_Soggetti_apapi_sottoscrittore_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table Soggetti_apapi_tp_crc
(
    ID_tp_crc     smallint not null
        constraint PK_Soggetti_apapi_tp_crc
            primary key,
    descrizione   nvarchar(50),
    campi         nvarchar(500),
    campi_domanda nvarchar(500)
)
go

create table Soggetti_apapi_tp_delega
(
    ID_tp_delega smallint      not null
        constraint PK_Soggetti_apapi_tp_delega
            primary key
                with (fillfactor = 95),
    tp_delega    nvarchar(200) not null
)
go

create table Soggetti_apapi_delegato
(
    ID           int not null,
    ID_soggetto  int not null,
    ID_tp_delega smallint
        constraint FK_Soggetti_apapi_delegato_Soggetti_apapi_tp_delega
            references Soggetti_apapi_tp_delega,
    constraint PK_Soggetti_apapi_delegato
        primary key (ID, ID_soggetto)
            with (fillfactor = 95)
)
go

create table Soggetti_apapi_tp_indirizzi
(
    ID_tp_indirizzo smallint not null
        constraint PK_Soggetti_apapi_tp_indirizzo
            primary key
                with (fillfactor = 95),
    tp_indirizzo    nvarchar(50)
)
go

create table Soggetti_apapi_tp_variazioni
(
    ID_tp_variazione smallint not null
        constraint PK_Soggetti_apapi_tp_variazioni
            primary key
                with (fillfactor = 95),
    variazione       nvarchar(150)
)
go

create table Soggetti_apapi_doc
(
    ID                    int not null
        constraint PK_Soggetti_apapi_doc
            primary key
                with (fillfactor = 95),
    ID_servizio           int not null,
    ID_user               int not null,
    data_presentazione    datetime,
    ID_tp_stato           int not null,
    ID_tp_stato_doc       smallint,
    protocollo_ente       nvarchar(50),
    luogo_attestazione    nvarchar(50),
    data_attestazione     datetime,
    luogo_sottoscrizione  nvarchar(50),
    data_sottoscrizione   datetime,
    errors                nvarchar(50),
    crc_residenza         int,
    crc_altro_recapito    int,
    crc_pagamento         int,
    var_residenza         smallint,
    var_altro_recapito    smallint,
    var_pagamento         smallint,
    note                  nvarchar(200),
    ID_tp_variazione      smallint
        constraint FK_Soggetti_apapi_doc_Soggetti_apapi_tp_variazioni
            references Soggetti_apapi_tp_variazioni,
    ID_domanda_aggiornata int
)
go

create table Soggetti_errati
(
    codice_errato    varchar(50),
    codice_corretto  varchar(50),
    cognome          varchar(50),
    nome             varchar(50),
    sesso            varchar(50),
    data_nascita     varchar(50),
    luogo_nascita    varchar(250),
    belfiore_nascita varchar(50),
    processato       int
)
go

create table Soggetti_giuridici
(
    ID_soggetto_giuridico int not null
        constraint PK_Soggetti_giuridici
            primary key
                with (fillfactor = 95),
    ID_user               int,
    partita_iva           nvarchar(20),
    descrizione           nvarchar(250),
    import_s1p            int
)
go

create table Soggetti_modificati_2017CAMBIAMI
(
    ID_soggetto_catasto int          not null,
    cognome             nvarchar(35) not null,
    nome                nvarchar(35) not null,
    ID_tp_sex           nvarchar     not null,
    data_nascita        datetime     not null,
    ID_luogo_nascita    nvarchar(6)  not null,
    codice_fiscale      nvarchar(16) not null
)
go

create table Soggetti_modificati_2018CAMBIAMI
(
    ID_soggetto_catasto int          not null,
    cognome             nvarchar(35) not null,
    nome                nvarchar(35) not null,
    ID_tp_sex           nvarchar     not null,
    data_nascita        datetime     not null,
    ID_luogo_nascita    nvarchar(6)  not null,
    codice_fiscale      nvarchar(16) not null
)
go

create table Soggetti_modificati_XXXXCAMBIAMI
(
    ID_soggetto_catasto int          not null,
    cognome             nvarchar(35) not null,
    nome                nvarchar(35) not null,
    ID_tp_sex           nvarchar     not null,
    data_nascita        datetime     not null,
    ID_luogo_nascita    nvarchar(6)  not null,
    codice_fiscale      nvarchar(16) not null
)
go

create table Soggetti_modificati_logs
(
    ID_soggetto   int not null,
    ID_variazione int not null,
    ID_query      int not null,
    tabella       nvarchar(100),
    colonna       nvarchar(50),
    query         text,
    done          int not null
)
go

create table Soggetti_modificati_test
(
    codice_fiscale nvarchar(16) not null
        constraint PK_Soggetti_modificati_test
            primary key
                with (fillfactor = 95)
)
go

create table Soggetti_modificati_tp_modifiche
(
    tp_modifica nvarchar(25)  not null,
    tabella     nvarchar(100) not null,
    colonna     nvarchar(50)  not null,
    enabled     int           not null,
    note        nvarchar(255),
    constraint PK_Soggetti_modificati_tp_modifiche
        primary key (tp_modifica, tabella, colonna)
            with (fillfactor = 95)
)
go

create table Sottoscrittori_domande
(
    ID_domanda                  int          not null
        constraint PK_Sottoscrittori_domande
            primary key,
    ID_soggetto                 int          not null,
    ID_tp_motivo_sottoscrizione nvarchar(8)  not null,
    ID_provincia_residenza      nvarchar(2)  not null,
    ID_comune_residenza         nvarchar(6)  not null,
    indirizzo_residenza         nvarchar(50) not null,
    n_civ_residenza             nvarchar(10) not null,
    cap_residenza               nvarchar(10) not null,
    telefono_residenza          nvarchar(20),
    constraint FK_Sottoscrittori_domande_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table System_performace_stat
(
    IdUser          int      not null,
    ActionTimestamp datetime not null,
    Jdk             char(40),
    ClesiusSystem   char(40),
    TimeToStart     int,
    TimeRequest     int,
    TimeDecode      int,
    TimeReadObj     int,
    SizeCoded       int,
    SizeDecoded     int,
    OS              nvarchar(500),
    Browser         nvarchar(500),
    Memory          nvarchar(500),
    JSServletError  nvarchar(500)
)
go

create table System_query_monitor
(
    ActionTimeStamp datetime,
    ExecutionTime   numeric,
    PostParam       varchar(2000),
    RequestQuery    text,
    DecriptQuery    text
)
go

create table TN_tp_iscrizione
(
    ID_tp_iscrizione smallint not null
        constraint PK_TN_tp_iscrizione
            primary key
                with (fillfactor = 95),
    tp_iscrizione    nvarchar(50)
)
go

create table TRIL_interventi
(
    ID_servizio   int          not null,
    tp_intervento nvarchar(50) not null,
    data_apertura datetime,
    data_chiusura datetime,
    attivo        int,
    constraint PK_TRIL_chiusure
        primary key (ID_servizio, tp_intervento)
            with (fillfactor = 95)
)
go

create table TRIL_tp_qualifiche
(
    ID_servizio     int           not null,
    ID_tp_qualifica int           not null,
    qualifica       nvarchar(150) not null,
    eta_min         int,
    eta_max         int,
    monogenitore    int,
    autonomo        int,
    constraint PK_TRIL_tp_qualifiche
        primary key (ID_servizio, ID_tp_qualifica)
)
go

create table TRIL_Dati
(
    ID_domanda            int            not null
        constraint PK_TRIL_Dati
            primary key
                with (fillfactor = 95),
    ID_servizio           int            not null,
    ID_tp_qualifica       int            not null,
    anno_superiori        int            not null,
    studente              int            not null,
    diplomato_disoccupato int            not null,
    laureato_disoccupato  int            not null,
    importo_escluso       numeric(18, 2) not null,
    interventoA           int            not null,
    interventoB           int            not null,
    interventoC           int            not null,
    interventoD           int            not null,
    constraint FK_TRIL_Dati_TRIL_tp_qualifiche
        foreign key (ID_servizio, ID_tp_qualifica) references TRIL_tp_qualifiche
)
go

create table TempObbligoRei
(
    [Id_domanda ]          float,
    [Cognome ]             nvarchar(255),
    [Nome ]                nvarchar(255),
    [Codice_fiscale ]      nvarchar(255),
    [Data_nascita ]        datetime,
    [Assolto_obbligo_rei ] float
)
go

create table UNI_Dati
(
    ID_domanda int          not null,
    pos        int          not null,
    parametro  nvarchar(50) not null,
    valore     nvarchar(50) not null,
    constraint PK_UNI_Dati
        primary key (ID_domanda, pos)
            with (fillfactor = 95)
)
go

create table UNI_Dati_R_Servizi
(
    ID_servizio int          not null,
    posizione   int          not null,
    parametro   nvarchar(50) not null,
    constraint PK_UNI_Dati_R_Servizi
        primary key (ID_servizio, parametro)
            with (fillfactor = 95)
)
go

create table UNI_R_Anni_accademici
(
    ID_anno_accademico int      not null
        constraint PK_UNI_tp_anno_accademico
            primary key
                with (fillfactor = 95),
    anno_accademico    char(10) not null
)
go

create table UNI_R_Corsi_Laurea_2
(
    tp_corso            nvarchar(50)  not null,
    ID_anno_accademico  int           not null,
    ID_servizio         int           not null,
    descrizione         nvarchar(100) not null,
    durata              int           not null,
    aggiuntivo          decimal(5, 2) not null,
    aggiuntivo_alloggio decimal(5, 2) not null,
    aggiuntivo_invalidi decimal(5, 2) not null,
    con_merito          smallint      not null,
    corso_esterno       smallint      not null,
    constraint PK_UNI_R_Corsi_Laurea_2
        primary key (tp_corso, ID_anno_accademico, ID_servizio)
            with (fillfactor = 95)
)
go

create table UNI_R_Merito_2
(
    tp_corso           nvarchar(50) not null,
    immatricolazione   int          not null,
    ID_anno_accademico int          not null,
    ID_servizio        int          not null,
    minimo             int          not null,
    min_premio         int          not null,
    massimo            int          not null,
    constraint PK_UNI_R_Merito_2
        primary key (tp_corso, immatricolazione, ID_anno_accademico, ID_servizio)
            with (fillfactor = 95),
    constraint FK_UNI_R_Merito_2_UNI_R_Corsi_Laurea_2
        foreign key (tp_corso, ID_anno_accademico, ID_servizio) references UNI_R_Corsi_Laurea_2
)
go

create table UNI_R_Sedi_corsi
(
    ID_anno_accademico int         not null,
    ID_sede_corso      nvarchar(6) not null,
    sede_corso         nvarchar(50),
    constraint PK_UNI_E_sedi_corsi
        primary key (ID_anno_accademico, ID_sede_corso)
            with (fillfactor = 95)
)
go

create table UNI_R_Comuni_in_sede
(
    ID_anno_accademico int         not null,
    ID_sede_corso      nvarchar(6) not null,
    ID_comune          nvarchar(6) not null
        constraint FK_UNI_R_comuni_in_sede_R_Luoghi
            references R_Luoghi (ID_luogo),
    constraint PK_UNI_R_Comuni_in_sede
        primary key (ID_anno_accademico, ID_sede_corso, ID_comune)
            with (fillfactor = 95),
    constraint FK_UNI_R_Comuni_in_sede_UNI_R_Sedi_corsi
        foreign key (ID_anno_accademico, ID_sede_corso) references UNI_R_Sedi_corsi
)
go

create table UNI_R_Universita
(
    ID_universita nvarchar(6) not null
        constraint PK_UNI_R_universita
            primary key
                with (fillfactor = 90),
    universita    nvarchar(50)
)
go

create table UNI_R_Contributi
(
    ID_universita      nvarchar(6) not null
        constraint FK_UNI_R_Contributi_UNI_R_universita
            references UNI_R_Universita,
    ID_anno_accademico int         not null
        constraint FK_UNI_R_Contributi_UNI_R_Anni_accademici
            references UNI_R_Anni_accademici,
    ID_tp_facolta      nvarchar(3) not null,
    ID_tp_ordinamento  nvarchar(3) not null,
    importo_matr       int         not null,
    importo_succ       int         not null,
    constraint PK_UNI_R_Contributi
        primary key (ID_universita, ID_anno_accademico, ID_tp_facolta, ID_tp_ordinamento)
            with (fillfactor = 95)
)
go

create table UNI_R_Facolta
(
    ID_universita      nvarchar(6) not null
        constraint FK_UNI_R_Facolta_UNI_R_universita
            references UNI_R_Universita,
    ID_facolta         nvarchar(6) not null,
    ID_anno_accademico int         not null,
    facolta            nvarchar(50),
    codice_facolta_s3  nvarchar(2),
    esterno            smallint,
    ID_tp_facolta      nvarchar(3) not null,
    constraint PK_UNI_R_Facolta
        primary key (ID_universita, ID_facolta, ID_anno_accademico)
            with (fillfactor = 95)
)
go

create table UNI_R_Corsi_laurea
(
    ID_universita            nvarchar(6)  not null,
    ID_facolta               nvarchar(6)  not null,
    ID_corso_laurea          nvarchar(6)  not null,
    ID_anno_accademico       int          not null,
    ID_sede_corso            nvarchar(6)  not null,
    corso_laurea             nvarchar(120),
    facolta                  nvarchar(50) not null,
    codice_corso_laurea_s3   nvarchar(5),
    ID_tp_ordinamento        nvarchar(3)  not null,
    anni_corso               int,
    anni_aggiuntivi          decimal(5, 2),
    anni_aggiuntivi_alloggio decimal(5, 2),
    anni_aggiuntivi_invalidi decimal(5, 2),
    constraint PK_UNI_R_Corsi_laurea
        primary key (ID_universita, ID_facolta, ID_corso_laurea, ID_anno_accademico)
            with (fillfactor = 95),
    constraint FK_UNI_R_Corsi_laurea_UNI_R_Facolta
        foreign key (ID_universita, ID_facolta, ID_anno_accademico) references UNI_R_Facolta,
    constraint FK_UNI_R_Corsi_laurea_UNI_R_Sedi_corsi
        foreign key (ID_anno_accademico, ID_sede_corso) references UNI_R_Sedi_corsi
)
go

create index IX_UNI_R_Facolta
    on UNI_R_Facolta (ID_tp_facolta)
    with (fillfactor = 95)
go

create table UNI_R_Merito
(
    ID_universita      nvarchar(6) not null,
    ID_facolta         nvarchar(6) not null,
    ID_corso_laurea    nvarchar(6) not null,
    ID_anno_accademico int         not null,
    anno_prima_imm     int         not null,
    minimo_deroga      float,
    minimo             float,
    massimo            float,
    minimo_premio      float,
    constraint PK_UNI_R_Merito
        primary key (ID_universita, ID_facolta, ID_corso_laurea, ID_anno_accademico, anno_prima_imm)
            with (fillfactor = 95),
    constraint FK_UNI_R_Merito_UNI_R_Corsi_laurea
        foreign key (ID_universita, ID_facolta, ID_corso_laurea, ID_anno_accademico) references UNI_R_Corsi_laurea
)
go

create table UNI_R_params
(
    id_servizio        int          not null,
    ID_tp_regola_opera int          not null,
    node               nvarchar(50) not null,
    valore             decimal(12, 2),
    constraint PK_UNI_R_params
        primary key (id_servizio, ID_tp_regola_opera, node)
)
go

create table UNI_anni_corso
(
    ID_servizio   int not null,
    ID_anno_corso int not null,
    anno_corso    nvarchar(100),
    constraint PK_UNI_anni_corso
        primary key (ID_servizio, ID_anno_corso)
)
go

create table UNI_sedi
(
    ID_servizio int not null,
    ID_sede     int not null,
    sede        nvarchar(50),
    constraint PK_UNI_sedi
        primary key (ID_servizio, ID_sede)
            with (fillfactor = 95)
)
go

create table UNI_tmp_isee_opera_cf
(
    cf nchar(16) not null
)
go

create table UNI_tp_alloggio
(
    ID_tp_alloggio int not null
        constraint PK_UNI_tp_alloggio
            primary key
                with (fillfactor = 90),
    tp_alloggio    nvarchar(50)
)
go

create table UNI_tp_corsi
(
    ID_tp_corso nchar(10)    not null,
    ID_servizio int          not null,
    tp_corso    nvarchar(50) not null,
    durata      int          not null,
    constraint PK_UNI_tp_corsi
        primary key (ID_tp_corso, ID_servizio)
            with (fillfactor = 95)
)
go

create table UNI_tp_documenti
(
    ID_tp_documento nvarchar(2)  not null
        constraint PK_UNI_tp_documenti
            primary key
                with (fillfactor = 95),
    tp_documento    nvarchar(50) not null
)
go

create table UNI_tp_documento_identita
(
    ID_tp_documento_identita nvarchar(2)   not null
        constraint PK_UNI_tp_documento_identita
            primary key
                with (fillfactor = 95),
    documento_identita       nvarchar(100) not null
)
go

create table UNI_tp_iscrizione
(
    ID_tp_iscrizione int not null
        constraint PK_UNI_tp_iscrizione
            primary key
                with (fillfactor = 90),
    tp_iscrizione    nvarchar(50)
)
go

create table UNI_tp_nucleo
(
    ID_tp_nucleo smallint not null
        constraint PK_UNI_tp_nucleo
            primary key
                with (fillfactor = 95),
    tp_nucleo    char(10)
)
go

create table UNI_tp_politica_opera
(
    ID_tp_politica_opera int          not null,
    tp_politica_opera    nvarchar(50) not null,
    ID_tp_regola_opera   int          not null,
    tp_regola_opera      nvarchar(50) not null,
    ID_servizio          int          not null,
    constraint PK_UNI_tp_politica_opera
        primary key (ID_tp_politica_opera, ID_servizio)
)
go

create table UNI_tp_progetti
(
    ID_tp_progetto int not null
        constraint PK_UNI_tp_progetti
            primary key
                with (fillfactor = 90),
    tp_progetto    nvarchar(50)
)
go

create table UNI_tp_sedi
(
    ID_servizio   int       not null,
    ID_sede_corso nchar(10) not null,
    sede_corso    varchar(50),
    constraint PK_UNI_tp_sedi
        primary key (ID_servizio, ID_sede_corso)
            with (fillfactor = 95)
)
go

create table UNI_tp_tasse
(
    ID_anno_accademico int           not null,
    ID_tp_esonero      nvarchar(50)  not null,
    tp_esonero         nvarchar(100) not null,
    tot_dovuto         float         not null,
    constraint PK_UNI_tp_tasse
        primary key (ID_anno_accademico, ID_tp_esonero)
            with (fillfactor = 90)
)
go

create table UNI_tp_voto_base_maturita
(
    ID_tp_voto_base_maturita int           not null
        constraint PK_UNI_tp_voto_base_maturita
            primary key
                with (fillfactor = 95),
    voto_maturita            nvarchar(100) not null
)
go

create table USER_ALERT_MAP
(
    USER_ID        int not null
        constraint FK_USER_ALERT_MAP_R_Utenti
            references R_Utenti,
    ALERT_ID       int
        constraint FKD83C84520DA4A2D
            references REPORT_ALERT,
    REPORT_ID      int
        constraint FKD83C845AAEF4A13
            references REPORT,
    ALERT_LIMIT    int,
    ALERT_OPERATOR varchar(255),
    MAP_ID         int not null,
    primary key (USER_ID, MAP_ID)
        with (fillfactor = 95)
)
go

create table USER_SECURITY
(
    USER_ID   int not null
        constraint FK_USER_SECURITY_R_Utenti
            references R_Utenti,
    ROLE_NAME varchar(255)
)
go

create table VCF_dati
(
    ID_domanda                    int         not null
        constraint PK_VCF_dati
            primary key
                with (fillfactor = 95),
    ID_family_card                nvarchar(8) not null,
    ID_domanda_associata          int         not null,
    data_ricezione                datetime    not null,
    validazione_domanda_associata int
)
go

create table VCF_familiari
(
    ID_domanda           int      not null,
    ID_dichiarazione     int      not null,
    musica               smallint not null,
    cinema               smallint not null,
    ordine               smallint not null,
    nome_scuola_musicale nvarchar(250),
    importo_retta        decimal(12, 2),
    descrizione          nvarchar(250),
    esclusione           smallint,
    banda                smallint,
    coro                 smallint,
    id_scuola            int,
    teatro               smallint not null,
    primary key (ID_domanda, ID_dichiarazione)
)
go

create table VCF_gestori
(
    id_gestore  int not null
        constraint PK_VCF_gestori
            primary key
                with (fillfactor = 95),
    gestore     nvarchar(100),
    descrizione nvarchar(150),
    indirizzo   nvarchar(250),
    telefono    nvarchar(100),
    email       nvarchar(100),
    id_servizio int
)
go

create table VCF_tickets
(
    ID_domanda       int         not null,
    ID_dichiarazione int         not null,
    code             numeric(20) not null,
    enabled          smallint,
    data_utilizzo    datetime,
    id_gestore       int         not null,
    stampa           smallint,
    progressivo      int,
    constraint PK_VCF_tickets
        primary key (ID_domanda, ID_dichiarazione, code)
            with (fillfactor = 95)
)
go

create table VCF_tickets_teatro
(
    ID_domanda       int         not null,
    ID_dichiarazione int         not null,
    code             numeric(20) not null,
    enabled          smallint,
    data_utilizzo    datetime,
    id_gestore       int         not null,
    stampa           smallint,
    progressivo      int,
    constraint VCF_tickets_teatro_PK
        primary key (ID_domanda, ID_dichiarazione, code)
)
go

create table VCS_dati
(
    ID_domanda                    int         not null
        primary key,
    ID_family_card                nvarchar(8) not null,
    ID_domanda_associata          int         not null,
    data_ricezione                datetime,
    validazione_domanda_associata int,
    numero_protocollo             varchar(20) not null,
    data_ricezione_domanda        datetime    not null
)
go

create table VCS_familiari
(
    ID_domanda        int      not null,
    ID_dichiarazione  int      not null,
    sport             smallint not null,
    ordine            smallint not null,
    nome_associazione nvarchar(250),
    importo_retta     decimal(12, 2),
    descrizione       nvarchar(250),
    esclusione        smallint,
    disciplina        nvarchar(100),
    categoria         nvarchar(100),
    ID_Associazione   int,
    ID_disciplina     int,
    primary key (ID_domanda, ID_dichiarazione)
)
go

create table VDONNE_Dati
(
    ID_domanda                int      not null,
    residenza_tn              smallint not null,
    no_istanza_precedente     smallint not null,
    no_gratuito_patrocinio    smallint not null,
    spese_procedimento_penale smallint not null,
    spese_procedimento_civile smallint not null,
    spesa                     decimal(10, 2),
    separazione               smallint not null,
    divorzio                  smallint not null,
    cond_sep_div              smallint not null,
    affidamento_minori        smallint not null,
    risarcimento_danni        smallint not null,
    altre_procedure           smallint not null,
    ID_tp_grado_giudizio      int      not null,
    ammonimento               smallint not null,
    procedimento_penale       smallint not null,
    ammonimento_581           smallint not null,
    ammonimento_582           smallint not null,
    ammonimento_612           smallint not null,
    art_571                   smallint not null,
    art_572                   smallint not null,
    art_575                   smallint not null,
    art_581                   smallint not null,
    art_582                   smallint not null,
    art_583                   smallint not null,
    art_583_2                 smallint not null,
    art_584                   smallint not null,
    art_609_2                 smallint not null,
    art_609_4                 smallint not null,
    art_609_8                 smallint not null,
    art_612                   smallint not null,
    art_612_2                 smallint not null,
    art_660                   smallint not null,
    min_art_600               smallint not null,
    min_art_600_2             smallint not null,
    min_art_600_3             smallint not null,
    min_art_600_5             smallint not null,
    min_art_601               smallint not null,
    min_art_602               smallint not null,
    min_art_609_5             smallint not null,
    min_art_609_11            smallint not null,
    recapito                  nvarchar(250),
    nominativo_difensore      nvarchar(50),
    autorita_giudiziaria      nvarchar(100),
    autorita_giudiziaria_data datetime,
    violenza_domestica        smallint not null
)
go

create table VD_tp_documento
(
    ID_tp_documento nvarchar(20) not null
        constraint PK_VD_tp_documento
            primary key
                with (fillfactor = 95),
    tipo_documento  nvarchar(50) not null
)
go

create table VD_tp_azioni
(
    ID_azione       int not null
        constraint PK_VD_tp_azioni
            primary key
                with (fillfactor = 95),
    ID_tp_documento nvarchar(20)
        constraint FK_VD_tp_azioni_VD_tp_documento
            references VD_tp_documento,
    descrizione     nvarchar(100)
)
go

create table VD_Azioni
(
    ID          int      not null,
    ID_azione   int      not null
        constraint FK_VD_Azioni_VD_tp_azioni
            references VD_tp_azioni,
    data_azione datetime not null,
    ID_user     int      not null,
    edizione    int
)
go

create table VEICOLI_contributi
(
    codice_fiscale     nchar(16) not null,
    anno_contributo    nchar(4)  not null,
    data_contributo    datetime,
    importo_contributo decimal,
    targa              nchar(10),
    constraint PK_VEICOLI_contributi
        primary key (codice_fiscale, anno_contributo)
            with (fillfactor = 95)
)
go

create table VEICOLI_proprietari
(
    ID_domanda             int not null,
    ID_soggetto            int not null,
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    cap_residenza          nvarchar(10),
    ID_comune_residenza    nvarchar(6),
    ID_provincia_residenza nvarchar(2),
    telefono               nvarchar(20),
    constraint PK_VEICOLI_proprietari
        primary key (ID_domanda, ID_soggetto),
    constraint FK_VEICOLI_proprietari_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table VEICOLI_tp_destinatari
(
    ID_servizio        int           not null,
    ID_tp_destinatario int           not null,
    tp_destinatario    nvarchar(150) not null,
    constraint PK_VEICOLI_tp_destinatari
        primary key (ID_servizio, ID_tp_destinatario)
)
go

create table VEICOLI_tp_documentazione
(
    ID_tp_documento int not null,
    ID_servizio     int not null,
    tp_documento    nvarchar(255),
    note            nvarchar(500),
    constraint PK_VEICOLI_tp_documentazione
        primary key (ID_tp_documento, ID_servizio)
            with (fillfactor = 95)
)
go

create table VEICOLI_tp_interventi
(
    ID_servizio      int          not null,
    ID_tp_intervento int          not null,
    tp_intervento    nvarchar(50) not null,
    constraint PK_VEICOLI_tp_interventi
        primary key (ID_servizio, ID_tp_intervento)
            with (fillfactor = 95)
)
go

create table VEICOLI_tp_iscrizioni
(
    ID_servizio      int           not null,
    ID_tp_iscrizione int           not null,
    tp_iscrizione    nvarchar(150) not null,
    constraint PK_VEICOLI_tp_iscrizione
        primary key (ID_servizio, ID_tp_iscrizione)
            with (fillfactor = 95)
)
go

create table VEICOLI_tp_tipi
(
    ID_servizio int not null,
    ID_tp_tipo  int not null,
    tp_tipo     nvarchar(150),
    constraint PK_VEICOLI_tp_tipi
        primary key (ID_servizio, ID_tp_tipo)
            with (fillfactor = 95)
)
go

create table VEICOLI_tp_usi
(
    ID_servizio int           not null,
    ID_tp_uso   int           not null,
    tp_uso      nvarchar(150) not null,
    ordine      int,
    constraint PK_VEICOLI_tp_usi
        primary key (ID_servizio, ID_tp_uso)
            with (fillfactor = 95)
)
go

create table Variazioni
(
    ID               int         not null,
    data_modifica    datetime    not null,
    enabled          int         not null,
    descrizione      nvarchar(100),
    ID_tp_variazione varchar(20) not null,
    id_ticket        varchar(10)
)
go

create table WorkFlow
(
    id_servizio             int      not null,
    id_periodo              int      not null,
    id_from                 int      not null,
    id_to                   int      not null,
    class_action            nvarchar(100),
    vorder                  smallint not null,
    sql                     nvarchar(200),
    jsp                     nvarchar(10),
    class_ControlliIniziali nvarchar(100)
)
go

grant insert on WorkFlow to [public]
go

create table XXXAC_RSA_20130222
(
    matricola       float,
    nominativo      nvarchar(255),
    codice_fiscale  nvarchar(255),
    data_nascita    datetime,
    id_entita       float,
    entita          nvarchar(255),
    id_servizio     float,
    servizio        nvarchar(255),
    data_ingresso   datetime,
    data_dimissione datetime,
    id_domanda      int,
    inserito        int not null
)
go

create table XXXAI_competenze
(
    ID_domanda    int      not null,
    ID_soggetto   int      not null,
    anno          int      not null,
    da_processare smallint not null,
    constraint PK_AI_competenze
        primary key (ID_domanda, ID_soggetto, anno)
            with (fillfactor = 95)
)
go

create table XXXANF_trasmissioni_dev1
(
    id_trasmissione            int          not null,
    id_domanda                 int          not null,
    periodo                    nvarchar(50),
    data_sottoscrizione        datetime,
    data_trasmissione          datetime     not null,
    nDomandaVar                int          not null,
    sportello                  int          not null,
    ufficio                    int          not null,
    tipoDomanda                int          not null,
    data_presentazione         datetime     not null,
    annoRif                    int          not null,
    cognome                    nvarchar(35) not null,
    nome                       nvarchar(35) not null,
    codice_fiscale             nvarchar(16) not null,
    comuneStatoNascita         nvarchar(35) not null,
    provinciaNascita           nvarchar(2)  not null,
    dataNascita                datetime     not null,
    cap                        nvarchar(50) not null,
    comune                     nvarchar(50) not null,
    provincia                  nvarchar(50) not null,
    frazione                   nvarchar(50),
    indirizzo                  nvarchar(50),
    nCivico                    nvarchar(50) not null,
    telefono                   nvarchar(50),
    tipoPagamento              nvarchar(50),
    numeroConto                nvarchar(12),
    cin                        nvarchar(3),
    cinConf                    smallint     not null,
    banca_pagamento            nvarchar(50),
    ubicazione_banca_pagamento nvarchar(50),
    abi                        nvarchar(5),
    cab                        nvarchar(5),
    a01                        float,
    a02                        float,
    a03                        float,
    a04                        float,
    a05                        float,
    a06                        float,
    a07                        float,
    a08                        float,
    a09                        float,
    a10                        float,
    a11                        float,
    a12                        float,
    a13                        float,
    a14                        float,
    a15                        float,
    a16                        float,
    a17                        float,
    a18                        float,
    tabUltimaRata              nvarchar(50) not null,
    rigaTabUltimaRata          int          not null,
    colTabUltimaRata           int          not null,
    azione                     int          not null,
    crc                        int          not null,
    ibanConf                   smallint,
    iban                       nvarchar(50),
    codiceStato                nvarchar(2),
    edizione                   int          not null,
    cf_altro_percettore        nvarchar(16),
    tp_indirizzo_altro         nvarchar(50),
    cap_altro                  nvarchar(50),
    comune_altro               nvarchar(50),
    provincia_altro            nvarchar(50),
    frazione_altro             nvarchar(50),
    indirizzo_altro            nvarchar(50),
    ncivico_altro              nvarchar(50),
    telefono_altro             nvarchar(50),
    pensplan_rinuncia          smallint,
    ID_codice_covip            int,
    progressivo_anagrafica     int,
    firma_grafometrica         smallint,
    domanda_unica              smallint
)
go

create table XXXID_
(
    ID int not null
        constraint PK_ID_
            primary key
)
go

create table XXXID_2
(
    id1 int not null
        constraint PK_ID_2
            primary key
                with (fillfactor = 95),
    id2 int
)
go

create table XXXanf_trasmissioni_familiari_dev1
(
    id_trasmissione       int          not null,
    id_domanda            int          not null,
    id_soggetto           int          not null,
    codice_fiscale        nvarchar(16) not null,
    pagato                smallint     not null,
    data_modifica_cf      datetime,
    id_domanda_collisione int
)
go

create table XXXbl_id3
(
    id_soggetto int not null
)
go

create table XXXbl_ric1
(
    id_domanda int not null
)
go

create table XXXgea_ic_verificia_cf
(
    codice_fiscale nchar(20),
    cf_corretto    nchar(20),
    nome           nvarchar(50),
    cognome        nvarchar(50),
    data_nascita   nvarchar(50)
)
go

create table XXXgea_ic_verificia_cf2
(
    codice_fiscale nchar(20),
    cf_corretto    nchar(20),
    nome           nvarchar(50),
    cognome        nvarchar(50),
    data_nascita   nvarchar(50)
)
go

create table aaa_dimensioni_indici
(
    data_stat                    datetime not null,
    TableName                    nvarchar(128),
    IndexName                    sysname,
    IndexType                    nvarchar(60),
    avg_fragmentation_in_percent float
)
go

create table ac_prod_c_elain
(
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_ac_prod_c_elain
        primary key (ID_domanda, net, node)
)
go

create table ac_prod_c_elaout
(
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    constraint PK_ac_prod_c_elaout
        primary key (ID_domanda, net, node)
)
go

create table am_aup_inps
(
    data           datetime      not null,
    codice_fiscale nvarchar(16)  not null,
    tariffa        decimal(8, 2) not null,
    constraint PK_am_aup_inps
        primary key (data, codice_fiscale)
            with (fillfactor = 95)
)
go

create table am_comp_monitor
(
    ID_ente_comp        int      not null,
    anno                int      not null,
    mese                int      not null,
    id_tp_stato         smallint not null,
    data_upload         datetime,
    data_elaborazione   datetime,
    data_download       datetime,
    data_conferma       datetime,
    message             text,
    ID_comunicazione_f3 int
)
go

create table am_r_luoghi_estinti
(
    id_luogo      nvarchar(6)   not null
        constraint PK_am_r_luoghi_estinti
            primary key
                with (fillfactor = 95),
    cod_provincia nvarchar(3)   not null,
    id_provincia  nvarchar(2)   not null,
    luogo         nvarchar(100) not null
)
go

create table am_r_province_esistenti
(
    cod_provincia nvarchar(3)   not null
        constraint PK_am_r_province_esistenti
            primary key
                with (fillfactor = 95),
    id_provincia  nvarchar(6)   not null,
    provincia     nvarchar(100) not null
)
go

create table aup_esclusioni_gea
(
    ID_DOMANDA          int not null,
    EDIZIONE            int not null,
    icef                numeric(38),
    icef12              numeric(38),
    soglia              numeric(38),
    soglia12            numeric(38),
    struttura_sociale   numeric(38),
    struttura_sociale12 numeric(38),
    residenza           numeric(38),
    residenza12         numeric(38),
    caso_sociale        numeric(38),
    caso_sociale12      numeric(38),
    ufficio             numeric(38),
    ufficio12           numeric(38),
    blacklist           numeric(38),
    blacklist12         numeric(38),
    periodo             numeric(38),
    periodo12           numeric(38),
    icefB1              numeric(38),
    icef12B1            numeric(38),
    residenzaB1         numeric(38),
    residenza12B1       numeric(38),
    periodoB1           numeric(38),
    periodo12B1         numeric(38),
    ufficioB1           numeric(38),
    ufficio12B1         numeric(38),
    icefB2              numeric(38),
    icef12B2            numeric(38),
    residenzaB2         numeric(38),
    residenza12B2       numeric(38),
    periodoB2           numeric(38),
    periodo12B2         numeric(38),
    ufficioB2           numeric(38),
    ufficio12B2         numeric(38),
    icefB3              numeric(38),
    icef12B3            numeric(38),
    residenzaB3         numeric(38),
    residenza12B3       numeric(38),
    periodoB3           numeric(38),
    periodo12B3         numeric(38),
    ufficioB3           numeric(38),
    ufficio12B3         numeric(38),
    id_esclusione_1     int,
    id_esclusione_2     int,
    id_esclusione_3     int,
    id_esclusione_4     int,
    id_esclusione_5     int,
    id_sospensione_1    int,
    id_sospensione_2    int,
    id_sospensione_3    int,
    id_sospensione_4    int,
    id_sospensione_5    int,
    qASospesa           float,
    qB1Sospesa          float,
    qB2Sospesa          float,
    qB3Sospesa          float,
    ID_SERVIZIO         int not null,
    ANNO                int not null,
    icefC               numeric(38),
    icef12C             numeric(38),
    residenzaC          numeric(38),
    residenza12C        numeric(38),
    periodoC            numeric(38),
    periodo12C          numeric(38),
    ufficioC            numeric(38),
    ufficio12C          numeric(38),
    id_esclusione_7     int,
    id_sospensione_7    int,
    qCSospesa           float
)
go

create table aup_rendicontazione_back
(
    codice_fiscale        nchar(16) not null,
    anno                  int       not null,
    mese                  int       not null,
    id_tp_rendicontazione int       not null,
    importo               decimal(18, 2),
    quantita              decimal(18, 2),
    id_blocco_flusso      int
)
go

create table cf
(
    codice_fiscale nchar(16) not null
)
go

create table corsi_fse
(
    id_corso    int          not null,
    cod_corso   nvarchar(50) not null,
    titolo      nvarchar(255),
    struttura   nvarchar(255),
    durata      char(10),
    ID_servizio int,
    servizio    nvarchar(50)
)
go

create table dich_fonti
(
    ID_dichiarazione int      not null,
    fonte            smallint not null,
    ID_tp_fonte      int,
    descrizione      nvarchar(250),
    ID_dich          int,
    numero_cud       int,
    constraint PK_dich_fonti
        primary key (ID_dichiarazione, fonte)
            with (fillfactor = 95)
)
go

create table dtproperties
(
    id       int identity,
    objectid int,
    property varchar(64) not null,
    value    varchar(255),
    uvalue   nvarchar(255),
    lvalue   image,
    version  int         not null,
    constraint pk_dtproperties
        primary key (id, property)
            with (fillfactor = 95)
)
go

grant delete, insert, references, select, update on dtproperties to [public]
go

create table duplica_id_servizi
(
    anno                      int not null,
    id_servizio_vecchio       int not null,
    id_servizio_nuovo         int,
    tipo                      nvarchar(250),
    net                       nvarchar(250),
    net_old                   nvarchar(250),
    duplicaReportTOT          int not null,
    duplicaReportABILITAZIONI int not null,
    beforeReportReplace       nvarchar(250),
    afterReportReplace        nvarchar(250),
    duplicaReportTODO         int not null,
    abilita                   int not null
)
go

create table duplica_servizi
(
    table_name  sysname not null,
    column_name sysname
)
go

create table esitiSPIL_estrazioneCasiSocialiTN14maggio
(
    codice_fiscale nvarchar(255),
    data_DID       nvarchar(255)
)
go

create table gb1
(
    id_doc         int   not null,
    importo        float not null,
    data_residenza date
)
go

create table gbTMP
(
    ID_domanda    int not null,
    numeric_value float
)
go

create table gb_AA
(
    ID_domanda                  int not null,
    numeric_value               float,
    anno_produzione_redditi_max int,
    id_servizio                 int not null
)
go

create table gb_AC_map_nodi_mesi
(
    anno        int          not null,
    id_servizio int          not null,
    id_periodo  int          not null,
    node        nvarchar(50) not null,
    mese        int,
    constraint [PK_gb_AC_map_nodi-mesi]
        primary key (anno, id_servizio, id_periodo, node)
            with (fillfactor = 95)
)
go

create table gb_GEA_map_out_trasm
(
    tp_beneficio int,
    periodo      int,
    node         nvarchar(50)
)
go

create table gb_GEA_trasm
(
    id_domanda int not null,
    edizione   int
)
go

create table gb_cf
(
    cf    nvarchar(16) not null
        constraint PK_gb_cf
            primary key
                with (fillfactor = 95),
    dic12 int,
    dic13 int,
    dic14 int,
    dic15 int,
    dic16 int,
    dic17 int
)
go

create table gb_ctrlAPAPI
(
    protocollo                         int            not null,
    anno                               int            not null,
    codice_fiscale                     nvarchar(16)   not null,
    ID_dichiarazione                   int            not null,
    anomalo                            smallint       not null,
    colore_anomalia                    smallint       not null,
    note                               text,
    note_anomalia_rilevata             text,
    diff_rilevata_quadro_C             decimal(12, 2) not null,
    diff_rilevata_quadro_C5            decimal(12, 2) not null,
    diff_rilevata_quadro_F             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_C    int            not null,
    ID_tp_descrizione_errore_quadro_C  int            not null,
    ID_tp_tipologia_errore_quadro_C5   int            not null,
    ID_tp_descrizione_errore_quadro_C5 int            not null,
    ID_tp_tipologia_errore_quadro_F    int            not null,
    ID_tp_descrizione_errore_quadro_F  int            not null,
    anomalia_quadro_C2                 smallint       not null,
    diff_rilevata_quadro_C2            decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_C2   int            not null,
    ID_tp_descrizione_errore_quadro_C2 int            not null,
    anomalia_quadro_D                  smallint       not null,
    diff_rilevata_quadro_D             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_D    int            not null,
    ID_tp_descrizione_errore_quadro_D  int            not null,
    anomalia_quadro_E                  smallint       not null,
    diff_rilevata_quadro_E             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_E    int            not null,
    ID_tp_descrizione_errore_quadro_E  int            not null,
    anomalia_quadro_G                  smallint       not null,
    diff_rilevata_quadro_G             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_G    int            not null,
    ID_tp_descrizione_errore_quadro_G  int            not null,
    ID_tp_ente_segnalatore             int            not null,
    ID_tp_altro_ente_segnalatore       int,
    dettagli_atro_ente_segnalatore     nvarchar(200),
    anomalia_quadro_C                  smallint       not null,
    anomalia_quadro_C5                 smallint       not null,
    anomalia_quadro_F                  smallint       not null,
    data_inserimento                   datetime       not null
)
go

create table gb_out_du
(
    ID_domanda        int not null,
    modificataDomanda int not null
)
go

create table gb_rg
(
    ID_servizio          int not null,
    anno                 int,
    ID_domanda           int not null,
    ID_soggetto          int not null,
    mensile              float,
    mesi                 float,
    ID_comunita_di_valle int not null,
    numero_minorenni     int,
    rg                   float
)
go

create table gb_test_in_icef_2016
(
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    ric_value   float
)
go

create table gb_test_in_icef_2016_IC
(
    anno        int          not null,
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    ric_value   float
)
go

create table gb_test_in_icef_2016_IC2
(
    anno        int          not null,
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    ric_value   float
)
go

create table gb_test_in_icef_2016_ITEA
(
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    ric_value   float
)
go

create table gb_test_out_icef_2016
(
    ID_domanda        int          not null,
    net               nvarchar(50) not null,
    node              nvarchar(50) not null,
    belief            real,
    numeric_value     float,
    n_elab            int,
    ric_value         float,
    modificataDomanda int          not null
)
go

create table gb_test_out_icef_2016_IC
(
    anno          int          not null,
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    locked        int          not null,
    ric_value     float
)
go

create table gb_test_out_icef_2016_IC2
(
    anno          int          not null,
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    locked        int          not null,
    ric_value     float
)
go

create table gb_test_out_icef_2016_ITEA
(
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    ric_value     float
)
go

create table gb_tmp_luoghi
(
    id_domanda int not null
)
go

create table gea_casellario_cache_escluse_temporanee
(
    id_domanda int
)
go

create table gea_casellario_cache_matricole_escluse
(
    certificato nvarchar(57)
)
go

create index idx_ncl_gea_casellario
    on gea_casellario_cache_matricole_escluse (certificato)
    with (fillfactor = 95)
go

create table gea_configurazioni2
(
    id_configurazione         int not null,
    id_servizio               int not null,
    id_periodo                int not null,
    id_tp_stato               int,
    sql_domande               nvarchar(4000),
    sql_domande_store         nvarchar(4000),
    sql_soggetti_domande      text,
    sql_beneficio1            nvarchar(4000),
    sql_beneficio2            nvarchar(4000),
    sql_beneficio3            nvarchar(4000),
    sql_beneficio4            nvarchar(4000),
    sql_beneficio5            nvarchar(4000),
    sql_altre_info            text,
    sql_esclusioni            text,
    sql_sospensioni           text,
    sql_periodi_beneficio1    text,
    sql_periodi_beneficio2    text,
    sql_periodi_beneficio3    text,
    sql_periodi_beneficio4    text,
    sql_periodi_beneficio5    text,
    data_ultimo_invio         datetime,
    enabled                   int not null,
    dimensione_blocco         int not null,
    progress                  int,
    solotest                  int,
    inserisci_su_trasmissione int,
    filtra_patrocinate        int,
    filtra_sospese            int
)
go

create table isee159_anno_redditi
(
    anno_reddito          int  not null
        constraint PK_isee159_anno_redditi
            primary key
                with (fillfactor = 95),
    inizio_attestazione   date not null,
    scadenza_attestazione date not null
)
go

create table isee159_richieste_generate
(
    identificatore_richiesta nvarchar(32) not null
        constraint pk_isee159_richieste_generate
            primary key,
    codice_ente              nvarchar(10),
    descrizione_ente         nvarchar(255),
    richiesta                xml,
    data_creazione           datetime,
    filename                 nvarchar(255),
    id_user                  int
)
go

create table isee159_tp_caratteri_psa
(
    ID_tp_carattere_psa int not null
        constraint PK_isee159_tp_caratteri_psa
            primary key
                with (fillfactor = 95),
    tp_carattere_psa    nvarchar(100)
)
go

create table isee159_tp_codici_psa
(
    id_tp_codice_psa nvarchar(10) not null
        constraint pk_isee159_tp_codici_psa
            primary key,
    tp_codice_psa    nvarchar(200)
)
go

create table isee159_tp_isee
(
    id_tp_isee int not null
        primary key
            with (fillfactor = 95),
    tp_isee    nvarchar(255),
    versione   int
)
go

create table isee159_tp_modalita_calcolo
(
    id_tp_modalita_calcolo int not null,
    id_tp_isee             int not null
        constraint fk_isee159_tp_m_c_tp_isee
            references isee159_tp_isee,
    tp_modalita_calcolo    nvarchar(255),
    tp_isee                nvarchar(255),
    cod_modalita_calcolo   nvarchar(50),
    tp_nucleo              nvarchar(3),
    tp_integrazione        nvarchar(20),
    versione               int,
    constraint pk_isee159_tp_modalita_calcolo
        primary key (id_tp_isee, id_tp_modalita_calcolo)
            with (fillfactor = 95)
)
go

create table isee159_tp_rapporti
(
    id_tp_rapporto nvarchar(2) not null
        constraint pk_isee159_tp_rapporti
            primary key,
    tp_rapporto    nvarchar(500)
)
go

create table isee159_tp_relazioni_parentela
(
    id_tp_relazione_parentela nvarchar(3) not null
        constraint PK_isee159_tp_relazioni_parentela
            primary key,
    tp_relazione_parentela    nvarchar(100)
)
go

create table isee159_ws_inps_esiti_richiesta_consultazioni
(
    identificatore_richiesta_inps nvarchar(27) not null
        constraint PK_isee159_ws_inps_esiti_richiesta_consultazioni
            primary key
                with (fillfactor = 95),
    codice_ente                   nvarchar(8),
    descrizione_ente              nvarchar(255),
    identificatore_richiesta      nvarchar(32),
    consultazioni_evase           int,
    consultazioni_errate          int,
    data_elaborazione             datetime,
    versione                      nvarchar(5),
    request                       xml,
    response                      xml,
    data_inserimento              datetime,
    data_elaborazione_clesius     datetime,
    filename                      nvarchar(255),
    messaggi                      nvarchar(max),
    id_user                       int,
    dsu                           bit
)
go

create table isee159_attestazioni
(
    protocollo_dsu             nvarchar(27) not null
        constraint PK_isee159_attestazioni
            primary key
                with (fillfactor = 95),
    codice_fiscale_dichiarante nvarchar(16) not null,
    data_rilascio              datetime,
    data_validita              datetime,
    data_presentazione         datetime,
    attestazione               xml,
    protocollo_mittente        nvarchar(29),
    id_user                    int,
    protocollo_richiesta       nvarchar(27)
        constraint FK_isee159_attestazioni_isee159_ws_inps_esiti_richiesta_consultazioni
            references isee159_ws_inps_esiti_richiesta_consultazioni
)
go

create table isee159_dichiarazioni
(
    protocollo_dsu             nvarchar(27) not null
        constraint PK_isee159_dichiarazioni
            primary key
                with (fillfactor = 95),
    codice_fiscale_dichiarante nvarchar(16) not null,
    data_rilascio              datetime,
    data_validita              datetime,
    data_presentazione         datetime,
    dichiarazione              xml,
    protocollo_mittente        nvarchar(29),
    id_user                    int,
    protocollo_richiesta       nvarchar(27)
        constraint FK_isee159_dichiarazioni_isee159_ws_inps_esiti_richiesta_consultazioni
            references isee159_ws_inps_esiti_richiesta_consultazioni
)
go

create table isee2014_trasparenza_configurazione_nodi
(
    nodo    varchar(50) not null
        constraint pk_isee2014_trasparenza_configurazione_nodi
            primary key
                with (fillfactor = 95),
    tabella varchar(50) not null,
    colonna varchar(50) not null,
    diretto varchar     not null,
    tipo    varchar(15) not null
)
go

create table isee2014_trasparenza_icef_isee
(
    id_gruppo   int identity
        constraint PK_isee2014_trasparenza_icef_isee
            primary key
                with (fillfactor = 95),
    nome_gruppo nchar(100) not null,
    id_user     int        not null
)
go

create table isee2014_trasparenza_icef_isee_domande
(
    id_gruppo    int not null
        constraint FK_isee2014_trasparenza_icef_isee_domande_isee2014_trasparenza_icef_isee
            references isee2014_trasparenza_icef_isee,
    id_domanda   int not null,
    codice_guest int not null,
    codice_test  int not null
)
go

create table isee2014_trasparenza_interventi
(
    codice_guest      int          not null,
    id_tp_prestazione int          not null,
    id_tp_intervento  int          not null,
    tp_intervento     varchar(200) not null,
    constraint isee2014_trasparenza_interventi_pkey
        primary key (codice_guest, id_tp_prestazione, id_tp_intervento)
            with (fillfactor = 95)
)
go

create table isee2014_trasparenza_beneficiari
(
    codice_guest      int         not null,
    id_tp_prestazione int         not null,
    id_tp_intervento  int         not null,
    codice_fiscale    varchar(16) not null,
    cognome           varchar(100),
    nome              varchar(100),
    codice_test       int,
    constraint pk_isee2014_trasparenza_beneficiari
        primary key (codice_guest, id_tp_prestazione, id_tp_intervento, codice_fiscale)
            with (fillfactor = 95),
    constraint FK_isee2014_trasparenza_beneficiari_isee2014_trasparenza_interventi
        foreign key (codice_guest, id_tp_prestazione, id_tp_intervento) references isee2014_trasparenza_interventi
)
go

create table isee2014_trasparenza_nucleo
(
    codice_guest     int      not null,
    codice_test      int      not null,
    n_componenti     int      not null,
    n_figli          int      not null,
    minorenni        int      not null,
    inferiore_a_3    smallint not null,
    monogenitore     smallint not null,
    lavoratori       smallint not null,
    min_disab_media  int      not null,
    min_disab_grave  int      not null,
    min_non_autosuff int      not null,
    n_disab_media    int      not null,
    n_disab_grave    int      not null,
    n_non_autosuff   int      not null,
    affitto          float    not null,
    residenza        float    not null,
    mutuo_residuo    float    not null,
    n_disabili       int      not null,
    constraint pk_isee2014_trasparenza_nucleo
        primary key (codice_guest, codice_test)
            with (fillfactor = 95)
)
go

create table isee2014_trasparenza_componenti
(
    codice_guest                         int   not null,
    codice_test                          int   not null,
    id_componente                        int   not null,
    redd_complessivo                     float not null,
    redd_dip                             float not null,
    detraz_dip                           float not null,
    redd_pens                            float not null,
    detraz_pens                          float not null,
    detraz_dippens                       float not null,
    redd_imposta                         float not null,
    redd_esente                          float not null,
    redd_agricolo                        float not null,
    assegni_percepiti                    float not null,
    tratt_assistenziale                  float not null,
    tratt_disabili                       float not null,
    redd_fondiari                        float not null,
    redd_aire                            float not null,
    assegno_coniuge                      float not null,
    assegno_figli                        float not null,
    spese_san_disabili                   float not null,
    redd_agrari                          float not null,
    spese_badanti                        float not null,
    spese_nonauto                        float not null,
    rette_rsa                            float not null,
    mobiliare                            float not null,
    depositi                             float not null,
    imm_oltre                            float not null,
    debito_oltre                         float not null,
    titoli_obbligazioni                  float not null,
    azioni                               float not null,
    partecipazioni_quotate               float not null,
    partecipazioni_non_quotate           float not null,
    patrimoni_gestione                   float not null,
    altri_strumenti                      float not null,
    patrimonio_netto_imprese_individuali float not null,
    imm_estero                           float not null,
    debito_estero                        float not null,
    imm_oltre130                         float not null,
    mutui130                             float not null,
    mobiliare130                         float not null,
    max_spese_disabili                   float not null,
    constraint pk_isee2014_trasparenza_componenti
        primary key (codice_guest, codice_test, id_componente)
            with (fillfactor = 95),
    constraint fk_isee2014_trasparenza_componenti_isee2014_trasparenza_nucleo
        foreign key (codice_guest, codice_test) references isee2014_trasparenza_nucleo
)
go

create table isee2014_trasparenza_parametri
(
    coefficiente              float not null,
    franchigia_max_mob        float not null,
    franchigia_mob            float not null,
    franchigia_res            float not null,
    grafici                   float not null,
    max_aff                   float not null,
    tasso                     float not null,
    franchigia_mob130         float not null,
    franchigia_res130         float not null,
    prepara130                float not null,
    trattamenti_assistenziali float not null,
    id_tp_versione_calcolo    int   not null
)
go

create table isee2014_trasparenza_parametri_disabili
(
    reddito_min numeric(8, 2) not null,
    reddito_max numeric(8, 2) not null,
    spese       numeric(6)    not null
)
go

create table isee2014_trasparenza_tp_immobili
(
    id_tp_immobile     varchar(15)     not null
        constraint pk_isee2014_trasparenza_tp_immobili
            primary key
                with (fillfactor = 95),
    tp_immobile        varchar(100)    not null,
    imu_ici            numeric(12, 10) not null,
    id_tp_immobile_old varchar(10)
)
go

create table isee2014_trasparenza_tp_prestazioni
(
    id_tp_prestazione int          not null
        constraint pk_isee2014_trasparenza_tp_prestazione
            primary key
                with (fillfactor = 95),
    tp_prestazione    varchar(100) not null
)
go

create table isee2014_trasparenza_test
(
    codice_guest      int not null,
    codice_test       int not null,
    descrizione       varchar(2000),
    id_tp_prestazione int not null
        constraint FK_isee2014_trasparenza_test_isee2014_trasparenza_tp_prestazioni
            references isee2014_trasparenza_tp_prestazioni,
    id_dich_iseenet   int,
    errori            smallint,
    isee_dl130        float,
    isee_confronto    float,
    constraint pk_isee2014_trasparenza_parametri
        primary key (codice_guest, codice_test)
)
go

create table isee2014_trasparenza_calcolo
(
    codice_guest int         not null,
    codice_test  int         not null,
    nodo         varchar(50) not null,
    valore       float,
    constraint pk_isee2014_trasparenza
        primary key (codice_guest, codice_test, nodo)
            with (fillfactor = 95),
    constraint FK_isee2014_trasparenza_calcolo_isee2014_trasparenza_test
        foreign key (codice_guest, codice_test) references isee2014_trasparenza_test
)
go

create table isee2014_trasparenza_calcolo_elaout
(
    codice_guest int         not null,
    codice_test  int         not null,
    nodo         varchar(50) not null,
    valore       float,
    constraint pk_isee2014_trasparenza_calcolo_elaout
        primary key (codice_guest, codice_test, nodo)
            with (fillfactor = 95),
    constraint FK_isee2014_trasparenza_calcolo_elaout_isee2014_trasparenza_test
        foreign key (codice_guest, codice_test) references isee2014_trasparenza_test
)
go

create table isee2014_trasparenza_calcolo_immobili
(
    codice_guest   int            not null,
    codice_test    int            not null,
    id_componente  int            not null,
    immobile       int            not null,
    percentuale    numeric(5, 2)  not null,
    valore_imu     numeric(10, 2) not null,
    valore_mutuo   numeric(10, 2) not null,
    id_tp_immobile varchar(15)    not null,
    constraint pk_isee2014_trasparenza_calcolo_immobili
        primary key (codice_guest, codice_test, id_componente, immobile)
            with (fillfactor = 95),
    constraint FK_isee2014_trasparenza_calcolo_immobili_isee2014_trasparenza_test
        foreign key (codice_guest, codice_test) references isee2014_trasparenza_test
)
go

create table isee2014_trasparenza_tp_versione_calcolo
(
    id_tp_versione_calcolo int        not null
        constraint PK_isee2014_trasparenza_tp_versione_calcolo
            primary key
                with (fillfactor = 95),
    tp_versione_calcolo    nchar(100) not null
)
go

create table isee_sia_richieste
(
    id_richiesta   int identity
        constraint PK_isee_sia_richieste
            primary key,
    id_user        int not null,
    data_creazione datetime,
    nome_file      varchar(50),
    xml            xml
)
go

create table isee_sia_richieste_domande
(
    id_richiesta        int not null
        constraint FK_isee_sia_richieste_domande_isee_sia_richieste
            references isee_sia_richieste,
    id_domanda          int not null,
    id_doc              int not null,
    id_tp_comunicazione int,
    constraint PK_isee_sia_richieste_domande_1
        primary key (id_richiesta, id_domanda)
            with (fillfactor = 95)
)
go

create table lav_temp_enti
(
    id_ente int,
    ente    nvarchar(150),
    luogo   nvarchar(150),
    ENABLED int
)
go

create table lb_AUP_rendicontazione15042019
(
    codice_fiscale        nchar(16) not null,
    anno                  int       not null,
    mese                  int       not null,
    id_tp_rendicontazione int       not null,
    importo               decimal(18, 2),
    quantita              decimal(18, 2),
    id_blocco_flusso      int
)
go

create table lb_STUD_R_tariffe
(
    ID_servizio       int          not null,
    ID_luogo          nvarchar(6)  not null,
    ID_scuola         smallint     not null,
    ID_tp_scuola      smallint     not null,
    ID_tp_beneficio   nvarchar(50) not null,
    ICEF_inf          float,
    ICEF_sup          float,
    importo_min       float,
    importo_max       float,
    sca               float,
    ICEF_lim          float,
    importo_0         float,
    ID_tp_tariffa     smallint     not null,
    solo_icef         smallint     not null,
    note_elabora      nvarchar(1100),
    scuola_mask       smallint     not null,
    ID_ente           int,
    luogo             nvarchar(40),
    per_1             float        not null,
    per_2             float        not null,
    per_3             float        not null,
    per_4             float        not null,
    per_5             float        not null,
    per_6             float        not null,
    ente_destinatario nvarchar(50)
)
go

create table lb_aup_13042019
(
    ID_domanda int not null
)
go

create table lb_aup_13042019_2
(
    ID_domanda int not null
)
go

create table lb_aup_14032019
(
    id_domanda int not null
)
go

create table lb_aup_17042019_1
(
    ID_domanda int not null
)
go

create table lb_aup_17042019_2
(
    ID_domanda int not null
)
go

create table lb_aup_21072019
(
    ID_domanda int not null
)
go

create table lb_aup_fix4
(
    ID_domanda int not null
)
go

create table lb_aup_flussi
(
    id_rendicontazione    int identity,
    id_entita             int          not null,
    anno                  int          not null,
    id_blocco_flusso      int,
    codice_fiscale        nvarchar(16) not null,
    idPersona             decimal(12),
    nome                  nvarchar(100),
    cognome               nvarchar(100),
    id_tp_rendicontazione int          not null,
    tariffa               decimal(8, 2),
    quantita              decimal(4),
    dal                   datetime,
    al                    datetime,
    codice_fiscale_flusso nvarchar(16),
    ID_file               int,
    pagato                smallint
)
go

create table lb_aup_flussi_20072019
(
    id_rendicontazione    int identity,
    id_entita             int          not null,
    anno                  int          not null,
    id_blocco_flusso      int,
    codice_fiscale        nvarchar(16) not null,
    idPersona             decimal(12),
    nome                  nvarchar(100),
    cognome               nvarchar(100),
    id_tp_rendicontazione int          not null,
    tariffa               decimal(8, 2),
    quantita              decimal(4),
    dal                   datetime,
    al                    datetime,
    codice_fiscale_flusso nvarchar(16),
    ID_file               int,
    pagato                smallint
)
go

create table lb_aup_rendicontazione
(
    codice_fiscale        nchar(16) not null,
    anno                  int       not null,
    mese                  int       not null,
    id_tp_rendicontazione int       not null,
    importo               decimal(18, 2),
    quantita              decimal(18, 2),
    id_blocco_flusso      int
)
go

create table lb_cf
(
    codice_fiscale nvarchar(50) not null
        constraint PK_lb_cf
            primary key
)
go

create table lb_da_controllare
(
    ID                    int not null,
    ID_user               int not null,
    protocollo            nvarchar(32),
    protocollo_ente       nvarchar(50),
    data_presentazione    datetime,
    luogo_presentazione   nvarchar(50),
    anno                  smallint,
    ID_tp_stato_doc       int,
    errors                nvarchar(500),
    annotazioni           nvarchar(200),
    ID_tp_stato           int,
    data_stato            datetime,
    data_sottoscrizione   datetime,
    luogo_sottoscrizione  nvarchar(50),
    da_ricalcolare        smallint,
    ID_user_original      int,
    ID_tp_accesso         int,
    info_tp_accesso       nvarchar(250),
    ente                  nvarchar(70),
    nome_addetto          nvarchar(50),
    ufficio               nvarchar(50),
    luogo_attestazione    nvarchar(50),
    data_attestazione     datetime,
    presenta_front_office smallint,
    bloccante             int,
    crc                   int,
    pagata_CAAF           int,
    tp_sblocco            int not null,
    anno_cud              int,
    comunicazione_email   smallint,
    validata              smallint,
    token                 nvarchar(60),
    ID_tp_stato_fam       int
)
go

create table lb_fix_aup
(
    ID_domanda int not null
)
go

create table lb_ic_062019
(
    ID_domanda int not null
)
go

create table lb_sostituzione_iban
(
    codice_fiscale nvarchar(50) not null,
    iban_errato    nvarchar(50) not null,
    iban_corretto  nvarchar(50) not null,
    primary key (codice_fiscale, iban_errato)
        with (fillfactor = 95)
)
go

create table lb_sostituzione_iban_domande
(
    id_domanda int not null
)
go

create table lb_test_aup
(
    ID_domanda int not null
)
go

create table legge6_stat
(
    ente                         nvarchar(50),
    ID_domanda                   int           not null,
    cognome                      nvarchar(35),
    nome                         nvarchar(35),
    data_presentazione           datetime,
    tp_fascia_assegno            varchar(80)   not null,
    ID_ente                      int,
    tp_situazione_dom            nvarchar(20)  not null,
    data_situazione_dom          datetime,
    servizio                     nvarchar(100) not null,
    tp_variazione_situazione_dom nvarchar(20)  not null,
    tipo                         varchar(5)    not null,
    rendicontata                 varchar(2)    not null,
    importo                      float
)
go

create table lista_documenti
(
    percorso nvarchar(31),
    nome     nvarchar(80)
)
go

create table lv_b3
(
    id_domanda int not null
)
go

create table mail_smtp_config
(
    id_smtp                   int identity
        constraint pk_mail_smtp_config
            primary key
                with (fillfactor = 95),
    smtphost                  nvarchar(50) not null,
    mailfrom                  nvarchar(50),
    smtpport                  nvarchar(200),
    smtpconnectiontimeout     nvarchar(200),
    smtplocalport             nvarchar(200),
    smtplocaladdress          nvarchar(200),
    smtplocalhost             nvarchar(200),
    smtptimeout               nvarchar(200),
    smtpehlo                  nvarchar(200),
    smtpauth                  nvarchar(200),
    smtpsubmitter             nvarchar(200),
    smtpsendpartial           nvarchar(200),
    smtpallow8bitmime         nvarchar(200),
    smtpdsnret                nvarchar(200),
    smtpdsnnotify             nvarchar(200),
    smtpsaslrealm             nvarchar(200),
    smtpquitwait              nvarchar(200),
    smtpreportsuccess         nvarchar(200),
    smtpstarttlsenable        nvarchar(200),
    smtpmailextension         nvarchar(200),
    smtpsocketfactoryport     nvarchar(200),
    smtpsocketfactoryfallback nvarchar(200),
    smtpuser                  nvarchar(200),
    smtpuserset               nvarchar(200),
    smtpsocketfactoryclass    nvarchar(200),
    password                  nvarchar(200),
    cer                       int
)
go

create table mail_template
(
    id_template    int identity
        constraint pk_mail_template
            primary key
                with (fillfactor = 95),
    id_smtp        int,
    id_smtpcer     int,
    descrizione    nvarchar(4000),
    obj            nvarchar(4000),
    body           text,
    attach         text,
    req_param      text,
    sql_to         text,
    time_creation  datetime,
    last_time_send datetime,
    last_sended    int,
    last_error     int
)
go

create table r_componenti
(
    id_component          int          not null,
    id_parent             int          not null,
    context               nvarchar(50) not null,
    plugin_id             nvarchar(50) not null,
    id_zk                 nvarchar(255),
    plugin_index_url      nvarchar(255),
    plugin_url_parameters text,
    role_priority         int          not null,
    role_class            nvarchar(255),
    role_parameters       nvarchar(max),
    label                 nvarchar(50),
    selected              int,
    icon                  nvarchar(50),
    plugin_singolo        int          not null
)
go

create table r_luoghi_istat
(
    id_luogo        nvarchar(6)   not null
        constraint PK_am_r_luoghi_esistenti
            primary key
                with (fillfactor = 95),
    cod_provincia   nvarchar(3)   not null,
    id_provincia    nvarchar(2),
    luogo_istat     nvarchar(100) not null,
    cod_catastale   nvarchar(4),
    estinto         smallint      not null,
    data_variazione datetime
)
go

create table ristampe_doc_edizioni
(
    id_doc       int not null,
    edizione_doc int not null,
    enabled      int,
    email        nvarchar(50),
    constraint PK_temp_doc_edizioni_da_ristampare
        primary key (id_doc, edizione_doc)
            with (fillfactor = 95)
)
go

create table [sanitest.L6_tp_fascia_assegno]
(
    id_tp_fascia_assegno smallint    not null
        constraint [PK_sanitest.L6_tp_fascia_assegno]
            primary key
                with (fillfactor = 95),
    tp_fascia_assegno    varchar(80) not null
)
go

create table sia_esiti
(
    ID_RICH_NUCLEO     int,
    DATA_PRES_MODU     datetime,
    DESC_STATO_DOM     nvarchar(50),
    COD_BIMESTRE       nchar(10),
    NOME_RICH          nvarchar(50),
    COGNOME_RICH       nvarchar(50),
    COD_FISC_RICH      nvarchar(16),
    DOMICILIO_RICH     nvarchar(50),
    TELEFONO_RICH      nvarchar(50),
    CONTROLLI_PRELI    nchar(10),
    DESC_CONTROLLI_    nvarchar(250),
    ESITO_ISEE         nchar(10),
    ESITO_ASDI         nchar(10),
    ESITO_NASPI        nchar(10),
    ESITO_DISCOLL      nchar(10),
    ESITO_COMUNE       nchar(10),
    ESITO_TRATTAME     nchar(10),
    ESITO_PUNTEGGIO    nchar(10),
    VAL_ISEE           decimal(18, 2),
    PUNTEGGIO          decimal(18, 2),
    IMPORTO_DISPOST    decimal(18, 2),
    ID_domanda         int,
    DATA_ULTIMA_ELAB   datetime,
    DATA_INVIO_POSTE   datetime,
    DATA_REND_POSTE    datetime,
    ID_ENTE_INSERITORE int,
    DATA_INSERIMENTO   datetime
)
go

create table sia_punteggi
(
    id_servizio  int       not null,
    id_punteggio int       not null,
    tipo         nchar(50) not null,
    punteggio    int       not null,
    constraint PK_sia_punteggi
        primary key (id_servizio, id_punteggio)
            with (fillfactor = 95)
)
go

create table sia_tp_carichi_familiari
(
    id_tp_carico_familiare int          not null
        constraint pk_sia_tp_carichi_familiari
            primary key
                with (fillfactor = 95),
    tp_carico_familiare    varchar(100) not null
)
go

create table sia_tp_comunicazioni
(
    id_tp_comunicazione nchar(3) not null
        constraint PK_sia_tp_comunicazioni
            primary key,
    comunicazione       nvarchar(100)
)
go

create table sia_tp_esiti_controlli_invio_inps
(
    id_tp_esito int          not null
        constraint pk_sia_tp_esiti_controlli_invio_inps
            primary key
                with (fillfactor = 95),
    tp_esito    varchar(100) not null,
    id_servizio int          not null
)
go

create table sia_tp_verifiche
(
    id_verifica    nchar(2)     not null
        constraint PK_sia_verifiche
            primary key
                with (fillfactor = 95),
    tipo_controllo nvarchar(50) not null,
    obbligatorio   smallint     not null
)
go

create table sott_non_sogg
(
    ID_dichiarazione     int not null,
    cognome              nvarchar(35),
    nome                 nvarchar(35),
    ID_provincia_nascita nvarchar(2),
    cod_catastale        nvarchar(4),
    data_nascita         datetime,
    ID_tp_sex            nvarchar,
    codice_fiscale       nvarchar(16),
    cod_fisc_calcolato   nvarchar(20),
    ID_soggetto          int
)
go

create table stat_du
(
    ID_domanda                  int           not null,
    ID_servizio                 int           not null,
    ID_ente                     int           not null,
    ente                        nvarchar(70),
    ufficio                     nvarchar(50),
    data_presentazione          varchar(10),
    icef                        float,
    cittadinanza                nvarchar(50)  not null,
    gruppo                      nchar(2),
    richiede_anf                int,
    ANF                         float,
    mensile                     float,
    tabella                     float,
    riga                        float,
    colonna                     float,
    richiede_fnum               int,
    FNUM                        float,
    richiede_contr_sost_consumi int,
    tp_pagamento                nvarchar(120) not null,
    luogo                       nvarchar(60)  not null,
    codice_fiscale              nvarchar(16)  not null,
    annotazioni                 nvarchar(200)
)
go

create table stat_du_anf
(
    ID_domanda int          not null,
    ANF        float,
    tabella    float,
    riga       float,
    colonna    float,
    mensile    float,
    node       nvarchar(50) not null
)
go

create table stat_du_fnum
(
    ID_domanda int not null,
    FNUM       float
)
go

create table stat_legge6
(
    ente                         nvarchar(50),
    ID_domanda                   int           not null,
    cognome                      nvarchar(35),
    nome                         nvarchar(35),
    data_presentazione           datetime,
    tp_fascia_assegno            varchar(80)   not null,
    ID_ente                      int,
    tp_situazione_dom            nvarchar(20)  not null,
    data_situazione_dom          datetime,
    servizio                     nvarchar(100) not null,
    tp_variazione_situazione_dom nvarchar(20)  not null,
    tipo                         varchar(5),
    rendicontata                 varchar(2)    not null,
    importo                      float
)
go

create table sysdiagrams
(
    name         sysname not null,
    principal_id int     not null,
    diagram_id   int identity
        primary key
            with (fillfactor = 95),
    version      int,
    definition   varbinary(max),
    constraint UK_principal_name
        unique (principal_id, name)
            with (fillfactor = 95)
)
go

exec sp_addextendedproperty 'microsoft_database_tools_support', 1, 'SCHEMA', 'dbo', 'TABLE', 'sysdiagrams'
go

create table tabella_da_cancellare
(
    id_domanda int,
    notinDocEd bit not null
)
go

create table tabella_prova
(
    nome    varchar(100),
    cognome varchar(100)
)
go

create table temp_AUP_OBBLIGO_REI
(
    ID_domanda           int          not null,
    data_presentazione   datetime,
    mesidaPres           int,
    giornidaPres         int,
    luogo                nvarchar(60) not null,
    ID_comunita_di_valle int          not null,
    comunita_di_valle    nvarchar(70) not null,
    inizio               float,
    anno                 int          not null,
    mese_reale           int          not null,
    icef                 float,
    cognome              nvarchar(35),
    nome                 nvarchar(35),
    codice_fiscale       nvarchar(16) not null,
    domandaREI           int,
    forzaREI             int,
    forza_rei            varchar(14)  not null,
    ASSOLTO              int          not null,
    importo              float
)
go

create table temp_AUP_SPIL
(
    ID_dichiarazione int       not null,
    ID_domanda       int       not null,
    codice_fiscale   nchar(16) not null
)
go

create table temp_AUP_datadid
(
    codice_fiscale nvarchar(16),
    data_did       datetime
)
go

create table temp_BS_ed
(
    id_doc                                           int      not null,
    edizione_doc                                     int      not null,
    data_edizione                                    datetime not null,
    ID_doc_edizioni_tipologia                        int      not null,
    hashcode                                         int,
    archiviato_pat                                   smallint not null,
    id_pat                                           int,
    data_archiviazione                               datetime,
    print_backup_file_name                           nvarchar(50),
    elab_backup_file_name                            nvarchar(50),
    id_user                                          int,
    consegnata_in_copia_a_gdf                        smallint not null,
    id_tp_stato_firma                                int      not null,
    token_id_server_conservazione_firma_grafometrica nvarchar(200),
    token_id_invio_doc_firma_grafometrica            nvarchar(200),
    session_key                                      nvarchar(200),
    task_id                                          nvarchar(20)
)
go

create table temp_DU_da_inviare
(
    ID_domanda   int not null
        constraint PK_temp_DU_da_inviare
            primary key
                with (fillfactor = 95),
    doc_edizione int not null
)
go

create table temp_GR_blacklist_doppioni_AP
(
    Expr1              int,
    id_domanda         int,
    data_inizio_blocco datetime  not null,
    data_fine_blocco   datetime  not null,
    id_tp_blacklist    int       not null,
    codice_fiscale     nchar(16) not null
)
go

create table temp_GR_blacklist_toltodoppio_AP
(
    codice_fiscale            nchar(16) not null,
    id_domanda                int,
    data_inizio_blocco        datetime  not null,
    data_fine_blocco          datetime  not null,
    id_tp_blacklist           int       not null,
    blocca_solo_domande_apapi smallint  not null,
    n_determinazione          nchar(20) not null
)
go

create table temp_GR_blacklist_updatate31_12_2017
(
    codice_fiscale            nchar(16) not null,
    id_domanda                int       not null,
    data_inizio_blocco        datetime  not null,
    data_fine_blocco          datetime  not null,
    id_tp_blacklist           int       not null,
    blocca_solo_domande_apapi smallint  not null,
    n_determinazione          nchar(20) not null,
    data_inserimento_blocco   datetime,
    id_ente_inseritore        int
)
go

create table temp_ITEA
(
    ID_domanda     int,
    edzione_doc    int,
    nodo           nvarchar(50),
    value          float,
    id_servizio    nchar(10),
    da_ricalcolare int not null
)
go

create table temp_IVSdaRicalcolare
(
    ID_domanda int not null
)
go

create table temp_REI_assolto
(
    ID_domanda int not null
)
go

create table temp_REI_controlli
(
    ID_domanda int not null
)
go

create table temp_REI_copia_esiti
(
    ID_INPS                 int,
    ID_domanda              int,
    CODICE_ENTE             nvarchar(4),
    DATA_MODULO             datetime,
    COGNOME_RICH            nvarchar(35),
    NOME_RICH               nvarchar(35),
    COD_FISCALE_RICH        nvarchar(16),
    STATO_DOMANDA           nvarchar(255),
    NOTE                    nvarchar(255),
    ANNO_MESE_COMP          nvarchar(50) not null,
    DSU_PROTOCOLLO_DOM      nvarchar(255),
    DSU_DATA_SOTT           nvarchar(255),
    ISEE_ESITO              nvarchar(255),
    ISEE_COD_ESITO          nvarchar(255),
    ISEE_DESC               nvarchar(255),
    DSU_PROTOCOLLO_PERIODO  nvarchar(255),
    DSU_DATA_SOTT_PERIODO   datetime,
    DSU_DATA_SCAD_PERIODO   datetime,
    AMMORTIZ_COMPATIBILITA  nvarchar(255),
    COND_LAV                nvarchar(255),
    COND_LAV_DESC           nvarchar(255),
    IMPORTO_CALC_BASE_ANNUA float,
    IMPORTO_DISP            float,
    DATA_POSTE              datetime,
    ESITO_POSTE             nvarchar(255),
    NOTE_ISEE               nvarchar(255),
    xml                     xml
)
go

create table temp_REI_datiINPS
(
    id_insp            int,
    data_presentazione datetime,
    stato              nvarchar(150),
    codice_fiscale     nvarchar(50),
    descrizione        nvarchar(50)
)
go

create table temp_REI_esiti
(
    [ID INPS]                 int,
    ID_domanda                int,
    [CODICE ENTE]             nvarchar(4),
    [DATA MODULO]             datetime,
    [COGNOME RICH]            nvarchar(35),
    [NOME RICH]               nvarchar(35),
    [COD FISCALE RICH]        nvarchar(16),
    [STATO DOMANDA]           nvarchar(255),
    NOTE                      nvarchar(255),
    [ANNO MESE COMP]          nvarchar(255),
    [DSU PROTOCOLLO DOM]      nvarchar(255),
    [DSU DATA SOTT]           nvarchar(255),
    [ISEE ESITO]              nvarchar(255),
    [ISEE COD ESITO]          nvarchar(255),
    [ISEE DESC]               nvarchar(255),
    [DSU PROTOCOLLO PERIODO]  nvarchar(255),
    [DSU DATA SOTT PERIODO]   datetime,
    [DSU DATA SCAD PERIODO]   datetime,
    [AMMORTIZ COMPATIBILITA]  nvarchar(255),
    [COND LAV]                nvarchar(255),
    [COND LAV DESC]           nvarchar(255),
    [IMPORTO CALC BASE ANNUA] float,
    [IMPORTO DISP]            float,
    [DATA POSTE]              datetime,
    ESITO_POSTE               nvarchar(255),
    NOTE_ISEE                 nvarchar(255)
)
go

create table temp_REI_richieste_domande
(
    ID_richiesta        int not null,
    ID_domanda          int not null,
    edizione_doc        int,
    id_tp_comunicazione nvarchar(3),
    creazioneFileINPS   int
)
go

create table temp_RG_controllaINVALIDI
(
    ID_domanda                                int            not null,
    ID_servizio                               int            not null,
    ID_dichiarazione                          int            not null,
    ID_dichiarazione_ext                      int            not null,
    anno_attualizzato                         int,
    mese                                      smallint,
    percezione_altri_redditi                  smallint       not null,
    perc_redd_mensilita_antecedente_invalidi  decimal(12, 2) not null,
    perc_redd_mensilita_2antecedente_invalidi decimal(12, 2) not null
)
go

create table temp_STUD_R_TariffeEstintoDimaroMonclassico
(
    ID_servizio       int          not null,
    ID_luogo          nvarchar(6)  not null,
    ID_scuola         smallint     not null,
    ID_tp_scuola      smallint     not null,
    ID_tp_beneficio   nvarchar(50) not null,
    ICEF_inf          float,
    ICEF_sup          float,
    importo_min       float,
    importo_max       float,
    sca               float,
    ICEF_lim          float,
    importo_0         float,
    ID_tp_tariffa     smallint     not null,
    solo_icef         smallint     not null,
    note_elabora      nvarchar(1100),
    scuola_mask       smallint     not null,
    ID_ente           int,
    luogo             nvarchar(40),
    ente_destinatario nvarchar(50)
)
go

create table temp_TRILAP2019
(
    id_domanda  int          not null,
    id_blocco   int          not null,
    input_value float,
    node        nvarchar(50) not null,
    ed_max      int,
    ed_max_prec int
)
go

create table temp_ad_pre_patrocinio
(
    id               int      not null,
    doc_id_user      int      not null,
    ID_user_original int,
    data_salvataggio datetime not null
)
go

create table temp_ad_pre_patrocinio_automatico
(
    id               int      not null,
    id_user_original int      not null,
    data_salvataggio datetime not null
)
go

create table temp_aggiorna_luoghi
(
    newidluogo     nvarchar(6),
    newidprovincia nvarchar(2),
    oldidluogo     nvarchar(6),
    oldidprovincia nvarchar(2)
)
go

create table temp_aggiorna_luoghi_bak
(
    newidluogo     nvarchar(6),
    newidprovincia nvarchar(2),
    oldidluogo     nvarchar(6),
    oldidprovincia nvarchar(2)
)
go

create table temp_ale
(
    id_doc         int,
    cognome        nvarchar(50),
    nome           nvarchar(50),
    codice_fiscale nvarchar(50)
)
go

create table temp_altri_genitoriAUP
(
    ID_domanda             int          not null,
    cognome                nvarchar(35) not null,
    nome                   nvarchar(35) not null,
    data_nascita           datetime,
    ID_provincia_residenza nvarchar(2),
    ID_comune_residenza    nvarchar(6),
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    cap_residenza          nvarchar(10),
    irreperibile           smallint
)
go

create table temp_benefici
(
    net               nvarchar(50) not null,
    node              nvarchar(50) not null,
    class             nvarchar(50),
    descrizione       nvarchar(200),
    stat              smallint     not null,
    ID_ente_erogatore int          not null
)
go

create table temp_canc_ed
(
    id_domanda                int           not null,
    edizione_doc              int           not null,
    tipologia                 nvarchar(100) not null,
    ID_doc_edizioni_tipologia int           not null,
    data_edizione             datetime      not null
)
go

create table temp_cf_all
(
    cf nvarchar(16) not null
        constraint PK_temp_cf_all
            primary key
                with (fillfactor = 95)
)
go

create table temp_cf_done
(
    cf nvarchar(16) not null
        constraint PK_temp_cf_done
            primary key
                with (fillfactor = 95)
)
go

create table temp_cisl_ultima_residenza
(
    data_presentazione datetime,
    ID_soggetto        int not null,
    dom                int not null,
    documento          int not null
)
go

create table temp_cisl_ultima_residenza2
(
    data_presentazione datetime,
    ID_soggetto        int not null,
    dom                int not null,
    documento          int not null
)
go

create table temp_cisl_ultima_residenza_soggetti
(
    ID_soggetto        int not null,
    data_presentazione datetime,
    id                 int not null,
    dom                int not null
)
go

create table temp_comp_cambiaenterogatore
(
    ID_servizio       int not null,
    id_superservizio  int not null,
    ID_domanda        int not null,
    ID_ente_erogatore int not null,
    ID_ente           int not null,
    da_ricalcolare    smallint
)
go

create table temp_controlla
(
    ID_dichiarazione int not null
)
go

create table temp_controlli2013
(
    codice_fiscale nchar(16) not null
)
go

create table temp_ctrlVC
(
    id int not null
)
go

create table temp_da_cancellare
(
    codice nvarchar(50),
    uno    nvarchar(50),
    due    nvarchar(50),
    tre    nvarchar(50)
)
go

create table temp_dati_famigliaAUP
(
    ID_domanda                     int not null,
    impegno_riconoscimento_assegno smallint,
    no_assegno_in_c5               smallint,
    giudiziali                     nvarchar(max),
    non_giudiziali                 nvarchar(max),
    assegno_extra                  decimal(10, 2),
    ID_tp_obbligo_mantenimento     int not null
)
go

create table temp_doc_edizioni_da_ristampare
(
    id_doc       int,
    edizione_doc int,
    enabled      int,
    email        nvarchar(50)
)
go

create table temp_doppi_dich
(
    ID_dich                   int          not null,
    ID_soggetto               int          not null,
    Expr1                     int,
    anno_produzione_redditi   smallint     not null,
    anno_produzione_patrimoni smallint,
    cognome                   nvarchar(35) not null,
    nome                      nvarchar(35) not null,
    dich_1                    int          not null,
    dich_2                    int          not null
)
go

create table temp_du
(
    id       int,
    cod_fisc nvarchar(50)
)
go

create table temp_ed_BS
(
    id_domanda                int           not null,
    edizione_doc              int           not null,
    tipologia                 nvarchar(100) not null,
    ID_doc_edizioni_tipologia int           not null,
    data_edizione             datetime      not null,
    id_doc                    int           not null,
    Expr1                     int           not null,
    node                      nvarchar(50)  not null,
    importo                   float         not null
)
go

create table temp_ed_esiti_BS
(
    id_doc  int          not null,
    Expr1   int          not null,
    node    nvarchar(50) not null,
    importo float        not null
)
go

create table temp_edizioni_mancantiAUP_1
(
    edizione int,
    id_doc   int not null
)
go

create table temp_estrazione21112016_APAPI
(
    id_domanda         int,
    id_edizione        int,
    patrocinata        int,
    ente_patrocinio    nvarchar(50),
    ufficio_patrocinio nvarchar(50),
    utente_patrocinio  nvarchar(50)
)
go

create table temp_estrazioneReddGar
(
    cocice_fiscale nvarchar(16)
)
go

create table temp_forzatura_aup
(
    ID_domanda int not null
)
go

create table temp_iban
(
    id_domanda     int not null,
    id_soggetto    int not null,
    codice_fiscale nvarchar(16),
    codice_stato   nvarchar(2),
    cin_pagamento  nvarchar(3),
    abi_pagamento  nvarchar(5),
    cab_pagamento  nvarchar(5),
    cc_pagamento   nvarchar(12),
    constraint PK_temp_iban
        primary key (id_domanda, id_soggetto)
            with (fillfactor = 95)
)
go

create table temp_iban_old
(
    id_domanda     int not null,
    id_soggetto    int not null,
    codice_fiscale nvarchar(16),
    codice_stato   nvarchar(2),
    cin_pagamento  nvarchar(3),
    abi_pagamento  nvarchar(5),
    cab_pagamento  nvarchar(5),
    cc_pagamento   nvarchar(12),
    constraint PK_temp_iban_old
        primary key (id_domanda, id_soggetto)
            with (fillfactor = 95)
)
go

create table temp_icef_per_isee
(
    id_gruppo      int          not null,
    id_domanda     int          not null,
    codice_fiscale nvarchar(16) not null,
    id_Servizio    int          not null
)
go

create table temp_id_domanda_unica_gestione_associata
(
    id             int       not null,
    codice_fiscale nchar(16) not null,
    importo        float     not null
)
go

create table temp_isee_sia_richieste_domande
(
    id_richiesta        int not null,
    id_domanda          int not null,
    id_doc              int not null,
    id_tp_comunicazione int
)
go

create table temp_isee_sia_richieste_domande_NEW
(
    id_richiesta        int not null,
    id_domanda          int not null,
    id_doc              int not null,
    id_tp_comunicazione int
)
go

create table temp_ivs
(
    ID_domanda int
)
go

create table temp_monogenitoreAUP
(
    ID_domanda int not null
)
go

create table temp_reccred_35
(
    codice_fiscale             nvarchar(16)   not null
        constraint PK_temp_reccred_35
            primary key
                with (fillfactor = 95),
    importo                    decimal(12, 2) not null,
    id_domanda                 int            not null,
    codice_fiscale_richiedente nvarchar(16)   not null
)
go

create table temp_reccred_flussi_aggregati_old
(
    codice_fiscale_richiedente nvarchar(16)   not null
        constraint PK_temp_reccred_flussi_aggregati_old
            primary key
                with (fillfactor = 95),
    id_domanda                 int            not null,
    da_recuperare              decimal(12, 2) not null,
    da_erogare                 decimal(12, 2) not null
)
go

create table temp_reccred_recupero
(
    codice_fiscale_richiedente nchar(16)      not null
        constraint PK_temp_reccred_recupero
            primary key
                with (fillfactor = 95),
    da_recuperare              decimal(12, 2) not null,
    recuperato                 decimal(12, 2) not null
)
go

create table temp_reddGar_ricalcoloInvalidiAtt
(
    ID_domanda                                int            not null,
    ID_servizio                               int            not null,
    ID_dichiarazione                          int            not null,
    ID_dichiarazione_ext                      int            not null,
    anno_attualizzato                         int,
    mese                                      smallint,
    percezione_altri_redditi                  smallint       not null,
    perc_redd_mensilita_antecedente_invalidi  decimal(12, 2) not null,
    perc_redd_mensilita_2antecedente_invalidi decimal(12, 2) not null,
    numeric_value                             float
)
go

create table temp_rei_com_aggiornaMODULOMV57
(
    ID_domanda               int          not null,
    data_presentazione       datetime,
    anno_reiCom              int,
    ID_soggetto              int          not null,
    cognome                  nvarchar(35) not null,
    nome                     nvarchar(35) not null,
    data_reiCom              datetime     not null,
    dataInizioLavoro         datetime     not null,
    ID_ente_erogatore        int          not null,
    d_no_lavoro_dopoAnnoRedd smallint     not null,
    reicom                   int          not null
)
go

create table temp_rei_rovereto
(
    ID_richiesta        int      not null,
    ID_domanda          int      not null,
    edizione_doc        int,
    id_tp_comunicazione nvarchar(3),
    c_gravidanza        smallint not null
)
go

create table temp_ric_meterne
(
    id int not null
)
go

create table temp_ricalcolo_REDDGAR
(
    ID_domanda                  int       not null,
    anno_produzione_redditi_min int,
    data_inizio_blocco          datetime  not null,
    data_fine_blocco            datetime  not null,
    codice_fiscale              nchar(16) not null,
    dataInizioContributo        date,
    dataFineContributo          date,
    inizio_blocco               date,
    fine_blocco                 date
)
go

create table temp_ricalcolo_pensplan
(
    id       int,
    edizione int
)
go

create table temp_sia_reinvio
(
    ID_domanda         int,
    ID_ENTE_INSERITORE int,
    ente               nvarchar(70)
)
go

create table temp_sport
(
    codice_fiscale nvarchar(20),
    serv           nchar(10),
    posizione      int,
    enabled        smallint,
    id_domanda     int,
    icef           numeric,
    comune         nvarchar(25),
    servizio       nvarchar(50)
)
go

create table temp_trento_domande_tolte_invio
(
    ID_richiesta        int not null,
    ID_domanda          int not null,
    edizione_doc        int,
    id_tp_comunicazione nvarchar(3)
)
go

create table temp_ws_fam_invio_forzato_domande_energia
(
    id_domanda int not null
)
go

create table temp_ws_fam_invio_forzato_domande_pv
(
    id_domanda int not null
)
go

create table temp_ws_fam_invio_forzato_domande_redd_gar
(
    id_domanda int not null
)
go

create table temp_ws_icef_trasmissioni_da_reinviare_publisher
(
    id_domanda int not null
)
go

create table test_generazione
(
    id_generazione int         not null,
    done           int         not null,
    id_operazione  varchar(89) not null,
    data           datetime    not null
)
go

create table titolarita_cat
(
    ID_titolarita int not null
        constraint titolarita_cat_PK
            primary key
)
go

create table tmp_UNITN_SIMULAZIONI_ISEE_2016_17
(
    codice_fiscale nchar(16) not null,
    id_domanda     int
)
go

create table tmp_aup_per_simulatore_isee
(
    ID_domanda int not null
)
go

create table tmp_cf
(
    cf varchar(16) not null
)
go

create table tmp_vp_unitn_simulatore
(
    id_domanda int
)
go

create table tp_accesso
(
    ID_tp_accesso      int not null
        constraint PK_tp_accesso
            primary key
                with (fillfactor = 95),
    tp_accesso         nvarchar(50),
    in_dichiarazione   int,
    note_dichiarazione nvarchar(250),
    in_domanda         int,
    note_domanda       nvarchar(250)
)
go

create table tp_accesso_pratiche
(
    ID_tp_accesso_pratiche smallint      not null
        constraint PK_tp_accesso_pratiche
            primary key
                with (fillfactor = 95),
    tp_accesso             nvarchar(250) not null,
    note                   nvarchar(500)
)
go

create table tp_agricolo
(
    ID_tp_agricolo  nvarchar(50)  not null,
    coltivazione_SN smallint      not null,
    tp_agricolo     nvarchar(100) not null,
    ID_dich         int           not null,
    constraint PK_tp_agricolo
        primary key (ID_dich, ID_tp_agricolo)
            with (fillfactor = 95)
)
go

create table R_Importi_agricoli
(
    ID_zona_agricola int            not null,
    ID_tp_agricolo   nvarchar(50)   not null,
    importo          decimal(12, 2) not null,
    ID_dich          int            not null,
    constraint PK_R_Importi_agricoli
        primary key (ID_dich, ID_zona_agricola, ID_tp_agricolo)
            with (fillfactor = 95),
    constraint FK_R_Importi_agricoli_R_Zone_agricole
        foreign key (ID_dich, ID_zona_agricola) references R_Zone_agricole,
    constraint FK_R_Importi_agricoli_tp_agricolo
        foreign key (ID_dich, ID_tp_agricolo) references tp_agricolo
)
go

create table tp_allegati
(
    ID_tp_allegato smallint      not null
        constraint PK_tp_allegati
            primary key
                with (fillfactor = 95),
    tp_allegato    nvarchar(100) not null,
    descr          nvarchar(100)
)
go

create table Doc_allegati
(
    ID_doc              int           not null,
    ID_allegato         smallint      not null,
    ID_tp_allegato      smallint      not null
        constraint FK_Doc_Allegati_Tp_Allegati
            references tp_allegati,
    nome_file_originale nvarchar(255) not null,
    nome_file_clesius   nvarchar(255) not null,
    data_upload         datetime      not null,
    nr_modifiche        smallint      not null,
    edizione_doc        int,
    constraint PK_Doc_Allegati
        primary key (ID_doc, ID_allegato)
            with (fillfactor = 95)
)
go

exec sp_addextendedproperty 'MS_Description', 'Numero progressivo allegato (1,2,3,...)', 'SCHEMA', 'dbo', 'TABLE',
     'Doc_allegati'
go

create table R_Allegati
(
    ID_Servizio        int      not null,
    ID_Periodo         int      not null,
    ID_tp_allegato     smallint not null
        constraint FK_R_Allegati_tp_allegati
            references tp_allegati,
    posizione_allegato smallint not null,
    ID_allegato_padre  int,
    constraint PK_R_Allegati
        primary key (ID_Servizio, ID_Periodo, ID_tp_allegato, posizione_allegato)
            with (fillfactor = 95)
)
go

create table tp_altre_funzioniZK
(
    ID_fuzione  nchar(10) not null
        constraint PK_tp_altre_funzioniZK
            primary key
                with (fillfactor = 95),
    descrizione nvarchar(250),
    application nchar(10)
)
go

create table tp_anni
(
    ID_tp_anno int not null
        constraint PK_tp_anni
            primary key
                with (fillfactor = 95),
    tp_anno    nvarchar(4)
)
go

create table tp_aperture_maschere
(
    id_tp_apertura smallint     not null
        constraint PK_tp_aperture_maschere
            primary key,
    tp_apertura    nvarchar(50) not null
)
go

create table R_Dich
(
    ID_dich                                   int      not null
        constraint PK_R_Dich
            primary key nonclustered
                with (fillfactor = 90),
    dich                                      nvarchar(50),
    obbligatoria_elaborazione                 smallint,
    abilitazione_procedura_firma_grafometrica int      not null,
    anno_produzione_redditi                   int      not null,
    ID_tp_apertura                            smallint not null
        constraint FK_R_Dich_tp_aperture
            references tp_aperture_maschere,
    enabled                                   smallint not null
)
go

create table R_Params_Dich
(
    ID_ente     int          not null
        constraint FK_R_Params_Dich_R_Enti
            references R_Enti,
    ID_dich     int          not null
        constraint FK_R_Params_Dich_R_Dich
            references R_Dich,
    param       nvarchar(30) not null,
    param_value text,
    enabled     smallint     not null,
    constraint PK_R_Params_Dich
        primary key nonclustered (ID_ente, ID_dich, param)
            with (fillfactor = 95)
)
go

create table tp_attivita
(
    ID_tp_attivita nchar(4)      not null
        constraint PK_tp_attivita
            primary key nonclustered
                with (fillfactor = 95),
    tp_attivita    nvarchar(200) not null,
    ordine         int
)
go

create table tp_categorie_catastali
(
    ID_tp_cat_catastale nvarchar(4)  not null
        constraint PK_tp_cat_catastale
            primary key nonclustered
                with (fillfactor = 90),
    tp_cat_catastale    nvarchar(10) not null
)
go

create table tp_cittadinanza
(
    ID_tp_cittadinanza nvarchar(4)  not null
        constraint PK_tp_cittadinanza
            primary key
                with (fillfactor = 95),
    tp_cittadinanza    nvarchar(50) not null,
    tipo               nvarchar(3),
    codice_enco        nvarchar(3),
    note               nvarchar(50),
    gruppo             nchar(2),
    stato              nvarchar(60)
)
go

create table tp_cittadinanze_inps
(
    id_tp_cittadinanza_inps varchar(4)  not null
        constraint pk_tp_cittadinanze_inps
            primary key
                with (fillfactor = 95),
    tp_cittadinanza_inps    varchar(50) not null,
    gruppo                  varchar(2),
    codice_stato_istat      varchar(3),
    codice_enco             varchar(3),
    estinto                 smallint    not null,
    codice_iso_3166         varchar(3),
    stato                   varchar(60) not null
)
go

create table tp_detrazioni
(
    ID_tp_detrazione char(3)       not null,
    ID_dich          int           not null,
    tp_detrazione    nvarchar(100) not null,
    ordine           int,
    constraint PK_tp_detrazioni
        primary key (ID_dich, ID_tp_detrazione)
)
go

create table Detrazioni
(
    ID_dichiarazione int            not null,
    detrazione       smallint       not null,
    ID_tp_detrazione char(3)        not null,
    importo          decimal(12, 2) not null,
    descrizione      nvarchar(250),
    ID_dich          int            not null,
    cointestatari    int,
    contributo       decimal(12, 2),
    constraint PK_Detrazioni
        primary key (ID_dichiarazione, detrazione)
            with (fillfactor = 95),
    constraint FK_Detrazioni_tp_detrazioni
        foreign key (ID_dich, ID_tp_detrazione) references tp_detrazioni
)
go

create table tp_diritti
(
    ID_tp_diritto char(2)       not null
        constraint PK_tp_diritti
            primary key
                with (fillfactor = 95),
    tp_diritto    nvarchar(100) not null,
    ordine        int
)
go

create table tp_doc_warning
(
    id_tp_warning numeric not null
        constraint PK_tp_doc_warning
            primary key
                with (fillfactor = 95),
    tp_warning    nvarchar(500)
)
go

create table tp_entrate
(
    ID_tp_entrata char(3)       not null
        constraint PK_tp_entrate
            primary key
                with (fillfactor = 95),
    tp_entrata    nvarchar(100) not null,
    ordine        int
)
go

create table tp_fonti
(
    ID_dich     int not null,
    ID_tp_fonte int not null,
    tp_fonte    nvarchar(200),
    constraint PK_tp_fonti
        primary key (ID_dich, ID_tp_fonte)
)
go

create table tp_gradi_giudizio
(
    ID_tp_grado_giudizio int          not null,
    tp_grado_giudizio    nvarchar(50) not null
)
go

create table tp_immobili
(
    ID_tp_immobile char(2)       not null
        constraint PK_tp_immobili
            primary key nonclustered
                with (fillfactor = 90),
    tp_immobile    nvarchar(100) not null,
    ordine         int
)
go

create table tp_imprese
(
    ID_tp_impresa char(3)       not null,
    tp_impresa    nvarchar(100) not null,
    ordine        int,
    ID_dich       int           not null,
    constraint PK_tp_imprese
        primary key (ID_dich, ID_tp_impresa)
            with (fillfactor = 95)
)
go

create table tp_intestatari_pagamento
(
    ID_tp_intestatario_pagamento int not null
        constraint PK_tp_intestatari_pagamento
            primary key,
    tp_intestatario_pagamento    nvarchar(50)
)
go

create table tp_invalidita
(
    ID_tp_invalidita     smallint      not null
        constraint PK_tp_invalidita
            primary key,
    tp_invalidita        nvarchar(100) not null,
    coeff_invalidita     float         not null,
    limite_min           int           not null,
    limite_max           int           not null,
    ordine               int           not null,
    coeff_invalidita2016 float
)
go

create table tp_investimenti
(
    ID_tp_investimento char(4)       not null,
    tp_investimento    nvarchar(100) not null,
    ordine             int,
    ID_dich            int           not null,
    constraint PK_tp_investimenti
        primary key (ID_dich, ID_tp_investimento)
)
go

create table Patrimoni_finanziari
(
    ID_dichiarazione          int            not null,
    finanziario               smallint       not null,
    ID_tp_investimento        char(4)        not null,
    codice_intermediario      nvarchar(10)   not null,
    descrizione_intermediario nvarchar(150)  not null,
    interessi                 decimal(12, 2) not null,
    consistenza               decimal(16, 2) not null,
    descrizione_investimento  nvarchar(200),
    ID_dich                   int            not null,
    consistenza_30_09         decimal(16, 2),
    consistenza_30_06         decimal(16, 2),
    consistenza_31_03         decimal(16, 2),
    importo_calcolo_31_12     decimal(16, 2),
    importo_calcolo_30_09     decimal(16, 2),
    importo_calcolo_30_06     decimal(16, 2),
    importo_calcolo_31_03     decimal(16, 2),
    numeri_calcolo            nvarchar(500),
    tipo_calcolo_giacenza     nvarchar(150),
    constraint PK_Patrimoni_finanziari
        primary key (ID_dichiarazione, finanziario)
            with (fillfactor = 95),
    constraint FK_Patrimoni_finanziari_tp_investimenti
        foreign key (ID_dich, ID_tp_investimento) references tp_investimenti
)
go

create table tp_isee_richiesti
(
    id_tp_isee_richiesto int not null
        constraint PK_tp_isee_richiesti
            primary key,
    tp_isee_richiesto    nvarchar(200)
)
go

create table tp_mesi
(
    id_mese smallint not null
        constraint PK_tp_mesi
            primary key
                with (fillfactor = 95),
    mese    nchar(10)
)
go

create table IC_anomalie_mesi
(
    ID_domanda      int      not null,
    anno            int      not null,
    ID_mese         smallint not null
        constraint FK_IC_anomalie_mesi_tp_mesi
            references tp_mesi,
    ricovero_rsa    bit,
    residenza       bit,
    cittadinanza    bit,
    incompatibilita bit,
    note            nvarchar(200),
    altro           bit,
    constraint PK_IC_anomalie_mesi
        primary key (ID_domanda, anno, ID_mese)
            with (fillfactor = 95)
)
go

create table tp_monogenitore
(
    ID_tp_monogenitore int           not null,
    ID_servizio        int           not null,
    ID_periodo         int           not null,
    tp_monogenitore    nvarchar(200) not null,
    descrizione        nvarchar(500) not null,
    ordine             int           not null,
    constraint PK_tp_monogenitore
        primary key (ID_tp_monogenitore, ID_servizio, ID_periodo)
            with (fillfactor = 95)
)
go

create table tp_motivo_sottoscrizione
(
    ID_tp_motivo_sottoscrizione nvarchar(8) not null
        constraint PK_tp_motivo_sottoscrizione
            primary key nonclustered
                with (fillfactor = 95),
    tp_motivo_sottoscrizione    text        not null,
    ordine                      smallint
)
go

create table tp_operazioni_diff
(
    ID_tp_operazione int           not null
        constraint PK_tp_operazioni_diff
            primary key
                with (fillfactor = 95),
    tp_operazione    nvarchar(50)  not null,
    class            nvarchar(100) not null,
    method           nvarchar(100) not null
)
go

create table R_Operazioni_Differite
(
    ID_utente        int            not null,
    data             datetime       not null,
    ID_tp_operazione int            not null
        constraint FK_R_Operazioni_Differite_tp_operazioni_diff
            references tp_operazioni_diff,
    priorita         int            not null,
    attiva           smallint       not null,
    email            nvarchar(100)  not null,
    parametri        nvarchar(1000) not null,
    data_esecuzione  datetime,
    constraint PK_R_Operazioni_Differite
        primary key (ID_utente, data, ID_tp_operazione)
            with (fillfactor = 95)
)
go

create table tp_pagamento
(
    ID_tp_pagamento      smallint      not null
        constraint PK_Tp_pagamento
            primary key nonclustered
                with (fillfactor = 90),
    tp_pagamento         nvarchar(120) not null,
    codice_tesoreria_pat nvarchar(5),
    aggiornaApapi        smallint
)
go

create table GEA_trasmissioni_anagrafica
(
    id_blocco          int          not null,
    id_soggetto        int          not null,
    data_variazione    datetime     not null,
    data_trasmissione  datetime     not null,
    id_ente            int          not null,
    id_ufficio         int          not null,
    id_protocollo      int,
    n_protocollo       nvarchar(255),
    data_protocollo    datetime,
    id_tp_stato_firma  int          not null,
    cognome            nvarchar(55),
    nome               nvarchar(35) not null,
    codice_fiscale     nvarchar(16) not null,
    id_luogo_nascita   nvarchar(6)
        constraint FK_GEA_trasmissioni_anagrafica_R_luoghi1
            references R_Luoghi (ID_luogo),
    data_nascita       datetime,
    sesso              nvarchar,
    cap_residenza      nvarchar(5),
    id_luogo_residenza nvarchar(6)
        constraint FK_GEA_trasmissioni_anagrafica_R_luoghi2
            references R_Luoghi (ID_luogo),
    frazione           nvarchar(50),
    indirizzo          nvarchar(50),
    n_civico           nvarchar(10),
    telefono           nvarchar(20),
    email              nvarchar(50),
    id_tp_pagamento    smallint
        constraint FK_GEA_trasmissioni_anagrafica_tp_pagamento
            references tp_pagamento,
    iban_OK            int,
    iban               nvarchar(50),
    id_provincia_altro nvarchar(20),
    id_comune_altro    nvarchar(20),
    indirizzo_altro    nvarchar(255),
    frazione_altro     nvarchar(255),
    n_civ_altro        nvarchar(10),
    cap_altro          nvarchar(10),
    telefono_altro     nvarchar(50),
    presso_altro       nvarchar(255),
    edizione           int,
    id_domanda_sogg    int,
    tipo_soggetto      int,
    constraint PKGEA_trasmissioni_anagrafica
        primary key (id_blocco, id_soggetto)
            with (fillfactor = 95)
)
go

create table GEA_esito_blocchi_soggetti
(
    id_blocco       int not null,
    id_soggetto     int not null,
    id_tp_esito     int not null
        constraint FK_GEA_esito_blocchi_soggetti_GEA_tp_esiti
            references GEA_tp_esiti,
    nota            nvarchar(2255),
    edizione        int,
    id_domanda_sogg int,
    constraint PK_GEA_esito_blocchi_soggetti
        primary key (id_blocco, id_soggetto)
            with (fillfactor = 95),
    constraint FK_GEA_esito_blocchi_soggetti_GEA_trasmissioni_anagrafica
        foreign key (id_blocco, id_soggetto) references GEA_trasmissioni_anagrafica
)
go

create index _dta_index_GEA_trasmissioni_anagrafica_7_469068907__K2_K1
    on GEA_trasmissioni_anagrafica (id_soggetto, id_blocco)
    with (fillfactor = 95)
go

create table ModPag
(
    ID_beneficiario              int not null,
    ID_intestatario_pagamento    int not null,
    ID_tp_intestatario_pagamento int not null
        constraint FK_ModPag_tp_intestatari_pagamento
            references tp_intestatari_pagamento,
    ID_tp_pagamento              smallint
        constraint FK_ModPag_tp_pagamenti
            references tp_pagamento,
    CIN_pagamento                nvarchar(3),
    ABI_pagamento                nvarchar(5),
    CAB_pagamento                nvarchar(5),
    Cc_pagamento                 nvarchar(12),
    banca_pagamento              nvarchar(50),
    ubicazione_banca_pagamento   nvarchar(50),
    BBAN_corretto                smallint,
    IBAN                         nvarchar(50),
    Codice_stato                 nvarchar(2),
    IBAN_corretto                smallint,
    ID_comune_catastale          nvarchar(4),
    ID_sportello                 smallint,
    crc                          int,
    ID_tp_delega                 smallint
        constraint FK_ModPag_Soggetti_apapi_tp_delega
            references Soggetti_apapi_tp_delega,
    constraint PK_ModPag
        primary key (ID_beneficiario, ID_intestatario_pagamento, ID_tp_intestatario_pagamento)
            with (fillfactor = 95),
    constraint FK_ModPag_R_Banche_Sportelli
        foreign key (ID_comune_catastale, ID_sportello) references R_Banche_Sportelli
)
go

create table tp_partecipazione
(
    ID_tp_partecipazione char          not null
        constraint PK_tp_partecipazione
            primary key nonclustered
                with (fillfactor = 95),
    tp_partecipazione    nvarchar(100) not null
)
go

create table tp_permessi_soggiorno
(
    id_tp_permesso int          not null,
    id_servizio    int          not null,
    tp_permesso    varchar(200) not null,
    ID_INPS        nvarchar(2),
    constraint pk_tp_permessi_soggiorno
        primary key (id_tp_permesso, id_servizio)
)
go

create table tp_redditi
(
    ID_tp_reddito char(3)       not null,
    tp_reddito    nvarchar(100) not null,
    ordine        int,
    ID_dich       int           not null,
    constraint PK_tp_redditi
        primary key (ID_dich, ID_tp_reddito)
            with (fillfactor = 95)
)
go

create table tp_redditi_attualizzati
(
    ID_tp_reddito_attualizzato int           not null,
    tp_reddito_attualizzato    nvarchar(200) not null,
    ID_dich                    int           not null,
    constraint PK_tp_redditi_attualizzati
        primary key (ID_tp_reddito_attualizzato, ID_dich)
            with (fillfactor = 95)
)
go

create table tp_relazioni
(
    id_tp_relazione int        not null
        constraint PK_tp_relazioni
            primary key
                with (fillfactor = 95),
    tp_relazione    nchar(100) not null
)
go

create table tp_relazioni_parentela
(
    ID_tp_relazione_parentela int not null
        constraint PK_tp_relazioni_parentela
            primary key
                with (fillfactor = 95),
    tp_relazione_parentela    nvarchar(50)
)
go

create table tp_report
(
    ID_tp_report smallint not null
        constraint PK_tp_report
            primary key
                with (fillfactor = 95),
    tp_report    nvarchar(50)
)
go

create table R_Reports
(
    ID_report        int      not null
        constraint PK_R_Reports
            primary key nonclustered
                with (fillfactor = 95),
    ID_ente          int,
    ID_ufficio       int,
    sql              nvarchar(2000),
    intestazione     nvarchar(255),
    header           nvarchar(100),
    footer           nvarchar(100),
    groupKey         int,
    caption          nvarchar(1000),
    ID_query         int,
    ID_servizio      int,
    ID_tp_report     smallint not null
        constraint FK_R_Reports_tp_report
            references tp_report,
    tp_ente          smallint,
    enabled          smallint not null,
    per_responsabili smallint
)
go

exec sp_addextendedproperty 'MS_Description', '0= report per tutti; 1=report per i responsabili del servizio', 'SCHEMA',
     'dbo', 'TABLE', 'R_Reports', 'COLUMN', 'per_responsabili'
go

create table R_Reports_campi
(
    ID_report    int not null
        constraint FK_R_reports_campi_R_Reports
            references R_Reports,
    ID_campo     int not null,
    campo        nvarchar(50),
    tp_campo     nvarchar(50),
    editable     int,
    table_filtro nvarchar(50),
    constraint PK_R_reports_campi
        primary key (ID_report, ID_campo)
            with (fillfactor = 95)
)
go

create table tp_richiedente
(
    ID_tp_richiedente int not null
        constraint PK_tp_richiedente
            primary key nonclustered
                with (fillfactor = 95),
    tp_richiedente    char(255)
)
go

create table tp_sblocchi
(
    sblocco                      int not null
        constraint PK_tp_sblocchi
            primary key
                with (fillfactor = 95),
    tipo_sblocco                 nvarchar(50),
    descrizione                  nvarchar(150),
    richiesta_sblocco            nvarchar(100),
    esecutore_sblocco            nvarchar(150),
    prenotazione                 smallint,
    invio_email_richiesta        nvarchar(150),
    invio_email_sblocco_avvenuto nvarchar(150),
    sistema_utilizzato           nvarchar(50)
)
go

create table tp_scelte
(
    ID_tp_scelta smallint     not null
        constraint PK_tp_scelte
            primary key nonclustered
                with (fillfactor = 95),
    tp_scelta    nvarchar(50) not null
)
go

create table tp_servizi
(
    ID_tp_servizio       int not null
        constraint PK_tp_servizi_1
            primary key,
    domanda              int,
    beneficiario         char(3),
    eta_ben_monogenitore int,
    eta_ben_autonomo     int,
    richiedente          int,
    monogenitore         int,
    nucleo_autonomo      int,
    descrizione          nvarchar(255)
)
go

create table R_Servizi
(
    ID_servizio                               int           not null
        constraint PK_R_Servizi
            primary key nonclustered
                with (fillfactor = 90),
    servizio                                  nvarchar(100) not null,
    solo_percentuale_SN                       smallint      not null,
    ordine                                    smallint,
    enabled                                   smallint      not null,
    gestione_abilitazione                     smallint      not null,
    abilitazione_modulo_trasp                 smallint      not null,
    url_modulo_trasp                          nvarchar(100),
    anno_produzione_redditi_min               int,
    anno_produzione_redditi_max               int,
    anno_produzione_patrimoni_min             int,
    anno_produzione_patrimoni_max             int,
    con_elaborazione                          smallint      not null,
    descrizione                               nvarchar(2000),
    classe_controllo_duplicati                nvarchar(200),
    tp_stato_trasmessa                        int,
    url_servlet_pubblicazione                 nvarchar(200),
    sblocco                                   int           not null
        constraint FK_R_Servizi_tp_sblocchi
            references tp_sblocchi,
    classe_definizione_componenti_dich_ICEF   varchar(200),
    data_fine_trasmissione                    datetime,
    servizio_esteso                           nvarchar(200),
    generazione                               smallint      not null,
    classe_post_ripristino_domanda            varchar(200),
    classe_post_ripristino                    varchar(200),
    stat_aderenza_nucleo_anagrafico           int           not null,
    classe_gestione_benefici_domanda          varchar(200),
    abilitazione_procedura_firma_grafometrica int           not null,
    abilitazione_soggetti_apapi               int           not null,
    id_tp_servizio                            int
        constraint FK_R_Servizi_tp_servizi
            references tp_servizi,
    data_inizio_trasmissione                  datetime,
    variazione_componenti_post_trasmissione   int,
    id_tp_apertura                            smallint      not null
        constraint FK_R_Servizi_tp_aperture_maschere
            references tp_aperture_maschere,
    plugin_id                                 nvarchar(50),
    class_gestione_validita_dom               varchar(200),
    aggiorna_data_presentazione               smallint      not null,
    data_fine_presentazione                   datetime      not null,
    class_filtro_publisher                    varchar(200),
    id_superservizio                          int           not null
        constraint FK_R_Servizi_R_Superservizi
            references R_Superservizi,
    id_tp_isee_richiesto                      int           not null
        constraint FK_R_Servizi_Tp_isee_richiesti
            references tp_isee_richiesti,
    id_tp_isee_default                        int
        constraint FK_R_Servizi_isee159_tp_isee
            references isee159_tp_isee,
    suffisso_ricalcolo                        nvarchar(10),
    data_inizio_trasparenza                   datetime,
    data_fine_trasparenza                     datetime
)
go

exec sp_addextendedproperty 'MS_Description',
     'Classe per la definizione di eventuali componenti e quadri da prendere in considerazione per la dichiarazione ICEF per l''elaborazione della domanda per questo servizio. Lasciare a NULL se si vogliono prendere tutti i componenti per tutti i quadri.',
     'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN', 'classe_definizione_componenti_dich_ICEF'
go

declare @MS_DefaultView tinyint = 2
exec sp_addextendedproperty 'MS_DefaultView', @MS_DefaultView, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi'
go

exec sp_addextendedproperty 'MS_Filter', N'((R_Servizi.ID_servizio=13020 Or R_Servizi.ID_servizio=13220))', 'SCHEMA',
     'dbo', 'TABLE', 'R_Servizi'
go

declare @MS_OrderByOn bit = '0'
exec sp_addextendedproperty 'MS_OrderByOn', @MS_OrderByOn, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi'
go

declare @MS_Orientation tinyint = 0
exec sp_addextendedproperty 'MS_Orientation', @MS_Orientation, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi'
go

declare @MS_RowHeight smallint = 6540
exec sp_addextendedproperty 'MS_RowHeight', @MS_RowHeight, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi'
go

exec sp_addextendedproperty 'MS_TableMaxRecords', 10000, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'ID_servizio'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'ID_servizio'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'ID_servizio'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'servizio'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'servizio'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'servizio'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'solo_percentuale_SN'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'solo_percentuale_SN'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'solo_percentuale_SN'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'ordine'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN', 'ordine'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN', 'ordine'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'enabled'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'enabled'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'enabled'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'gestione_abilitazione'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'gestione_abilitazione'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'gestione_abilitazione'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'abilitazione_modulo_trasp'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'abilitazione_modulo_trasp'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'abilitazione_modulo_trasp'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'url_modulo_trasp'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'url_modulo_trasp'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'url_modulo_trasp'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_redditi_min'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_redditi_min'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_redditi_min'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_redditi_max'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_redditi_max'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_redditi_max'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_patrimoni_min'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_patrimoni_min'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_patrimoni_min'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_patrimoni_max'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_patrimoni_max'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'anno_produzione_patrimoni_max'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'con_elaborazione'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'con_elaborazione'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'con_elaborazione'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'descrizione'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'descrizione'
go

declare @MS_ColumnWidth smallint = 9030
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'descrizione'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'classe_controllo_duplicati'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'classe_controllo_duplicati'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'classe_controllo_duplicati'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'tp_stato_trasmessa'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'tp_stato_trasmessa'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'tp_stato_trasmessa'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'url_servlet_pubblicazione'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'url_servlet_pubblicazione'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'url_servlet_pubblicazione'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'sblocco'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'sblocco'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'sblocco'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'classe_definizione_componenti_dich_ICEF'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'classe_definizione_componenti_dich_ICEF'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'classe_definizione_componenti_dich_ICEF'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'data_fine_trasmissione'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'data_fine_trasmissione'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'data_fine_trasmissione'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'servizio_esteso'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'servizio_esteso'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'servizio_esteso'
go

declare @MS_ColumnHidden bit = '0'
exec sp_addextendedproperty 'MS_ColumnHidden', @MS_ColumnHidden, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'generazione'
go

declare @MS_ColumnOrder smallint = 0
exec sp_addextendedproperty 'MS_ColumnOrder', @MS_ColumnOrder, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'generazione'
go

declare @MS_ColumnWidth smallint = -1
exec sp_addextendedproperty 'MS_ColumnWidth', @MS_ColumnWidth, 'SCHEMA', 'dbo', 'TABLE', 'R_Servizi', 'COLUMN',
     'generazione'
go

create table C_DefaultIn
(
    ID_servizio   int          not null
        constraint FK_C_DefaultIn_R_Servizi
            references R_Servizi,
    ID_periodo    int          not null,
    ID_ente       int          not null
        constraint FK_C_DefaultIn_R_Enti
            references R_Enti,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    default_value nvarchar(50) not null,
    modificabile  smallint,
    constraint PK_C_DefaultIn
        primary key (ID_servizio, ID_periodo, ID_ente, net, node)
            with (fillfactor = 95),
    constraint FK_C_DefaultIn_C_NodIN
        foreign key (net, node) references C_NodIN
)
go

create table DU_R_caf_patronati
(
    ID_servizio          int           not null
        constraint FK_DU_R_caf_patronati_R_Servizi
            references R_Servizi,
    ID_ente_caf          int           not null
        constraint FK_DU_R_caf_patronati_R_Enti
            references R_Enti,
    ID_ufficio_caf       int           not null,
    ID_ente_patronato    int           not null
        constraint FK_DU_R_caf_patronati_R_Enti1
            references R_Enti,
    ID_ufficio_patronato int           not null,
    descrizione          nvarchar(200) not null,
    patronato            nvarchar(200),
    constraint PK_DU_R_caf_patronati
        primary key (ID_servizio, ID_ente_caf, ID_ufficio_caf, ID_ente_patronato, ID_ufficio_patronato)
            with (fillfactor = 95),
    constraint FK_DU_R_caf_patronati_R_Uffici
        foreign key (ID_ente_caf, ID_ufficio_caf) references R_Uffici,
    constraint FK_DU_R_caf_patronati_R_Uffici1
        foreign key (ID_ente_patronato, ID_ufficio_patronato) references R_Uffici
)
go

create table Doc_edizioni_utenti_servizi
(
    id_utente   int not null
        constraint FK_Doc_edizioni_utenti_servizi_Doc_edizioni_utenti
            references Doc_edizioni_utenti,
    id_servizio int not null
        constraint FK_Doc_edizioni_utenti_servizi_R_servizi
            references R_Servizi,
    constraint PK_Doc_edizioni_utenti_servizi
        primary key (id_utente, id_servizio)
)
go

create table EPU_tp_intestatari
(
    ID_tp_intestatario int not null,
    tp_intestatario    nvarchar(50),
    ID_servizio        int not null
        constraint FK_EPU_tp_intestatari_R_Servizi
            references R_Servizi,
    constraint EPU_tp_intestatari_PK
        primary key (ID_tp_intestatario, ID_servizio)
)
go

create table EPU_tp_servizi_igienici
(
    ID_tp_servizio_igienico int not null,
    tp_servizio_igienico    nvarchar(50),
    ID_servizio             int not null
        constraint FK_EPU_tp_servizi_igienici_R_servizi
            references R_Servizi,
    constraint EPU_tp_servizi_igienici_PK
        primary key (ID_tp_servizio_igienico, ID_servizio)
)
go

create table EPU_tp_uscite_nucleo
(
    id_tp_uscita_nucleo int not null,
    tp_uscita_nucleo    nvarchar(50),
    id_servizio         int not null
        constraint FK_EPU_tp_uscite_nucleo_R_Servizi
            references R_Servizi,
    constraint EPU_tp_uscite_nucleo_PK
        primary key (id_tp_uscita_nucleo, id_servizio)
)
go

create table ER_R_Servizi
(
    ID_servizio             int not null
        constraint FK_ER_R_Servizi_R_Servizi1
            references R_Servizi
        constraint FK_ER_R_Servizi_R_Servizi2
            references R_Servizi,
    ID_servizio_connessione int not null,
    constraint PK_ER_R_Servizi
        primary key (ID_servizio, ID_servizio_connessione)
            with (fillfactor = 95)
)
go

create table ER_tp_imposte
(
    ID_tp_imposta int not null
        constraint PK_ER_tp_imposte
            primary key
                with (fillfactor = 95),
    tp_imposta    nvarchar(200),
    ID_servizio   int
        constraint FK_ER_tp_imposte_R_servizi
            references R_Servizi
)
go

create table ER_tp_prestazioni
(
    ID_tp_prestazione int not null
        constraint PK_ER_tp_prestazioni
            primary key
                with (fillfactor = 95),
    tp_prestazione    nvarchar(200),
    ID_servizio       int
        constraint FK_ER_tp_prestazioni_R_servizi
            references R_Servizi
)
go

create table ER_tp_relazioni_parentela
(
    ID_tp_relazione_parentela int not null
        constraint PK_ER_tp_relazioni_parentela
            primary key
                with (fillfactor = 95),
    tp_relazione_parentela    nvarchar(200),
    ID_servizio               int
        constraint FK_ER_tp_relazioni_parentela_R_servizi
            references R_Servizi
)
go

create table ER_tp_stati_testamento
(
    ID_tp_stato_testamento int not null
        constraint PK_ER_tp_stati_testamento
            primary key,
    tp_stato_testamento    nvarchar(500),
    ID_servizio            int
        constraint FK_ER_tp_stati_testamento_R_servizi
            references R_Servizi
)
go

create table GEA_configurazioni
(
    id_configurazione         int not null
        constraint PK_GEA_configurazioni
            primary key
                with (fillfactor = 95),
    id_servizio               int not null
        constraint FK_GEA_configurazioni_R_servizi
            references R_Servizi,
    id_periodo                int not null,
    id_tp_stato               int,
    sql_domande               nvarchar(4000),
    sql_domande_store         nvarchar(4000),
    sql_soggetti_domande      text,
    sql_beneficio1            nvarchar(4000),
    sql_beneficio2            nvarchar(4000),
    sql_beneficio3            nvarchar(4000),
    sql_beneficio4            nvarchar(4000),
    sql_beneficio5            nvarchar(4000),
    sql_beneficio6            nvarchar(4000),
    sql_altre_info            text,
    sql_esclusioni            text,
    sql_sospensioni           text,
    sql_periodi_beneficio1    text,
    sql_periodi_beneficio2    text,
    sql_periodi_beneficio3    text,
    sql_periodi_beneficio4    text,
    sql_periodi_beneficio5    text,
    sql_periodi_beneficio6    text,
    data_ultimo_invio         datetime,
    enabled                   int not null,
    dimensione_blocco         int not null,
    progress                  int,
    solotest                  int,
    inserisci_su_trasmissione int,
    filtra_patrocinate        int,
    filtra_sospese            int
)
go

create table IC_C_DefaultIn
(
    anno          int          not null,
    ID_servizio   int          not null
        constraint FK_IC_C_DefaultIn_R_Servizi
            references R_Servizi,
    ID_periodo    int          not null,
    ID_ente       int          not null
        constraint FK_IC_C_DefaultIn_R_Enti
            references R_Enti,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    default_value nvarchar(50) not null,
    modificabile  smallint,
    constraint PK_IC_C_DefaultIn
        primary key (anno, ID_servizio, ID_periodo, ID_ente, net, node)
            with (fillfactor = 95),
    constraint FK_IC_C_DefaultIn_C_NodIN
        foreign key (net, node) references C_NodIN
)
go

create table IC_tp_conferme_anomalie
(
    ID_tp_conferma_anomalia int not null
        constraint PK_IC_tp_conferme_anomalie
            primary key,
    tp_conferma_anomalia    nvarchar(100),
    ID_servizio             int
        constraint FK_IC_tp_conferme_anomalie_R_servizi
            references R_Servizi
)
go

create table IC_anomalie_conferme
(
    ID_domanda              int not null
        constraint PK_IC_anomalie_conferme
            primary key
                with (fillfactor = 95),
    ID_tp_conferma_anomalia int
        constraint FK_IC_anomalie_conferme_IC_tp_conferme_anomalie
            references IC_tp_conferme_anomalie,
    da_istruttoria          bit
)
go

create table IC_tp_documenti
(
    ID_tp_documento int not null
        constraint PK_IC_tp_documenti
            primary key
                with (fillfactor = 95),
    tp_documento    nvarchar(50),
    ID_servizio     int
        constraint FK_IC_tp_documenti_R_Servizi
            references R_Servizi
)
go

create table IC_tp_domande
(
    ID_tp_domanda              int not null
        constraint PK_IC_tp_domande
            primary key
                with (fillfactor = 95),
    tp_domanda                 nvarchar(200),
    ID_servizio                int
        constraint FK_IC_tp_domande_R_Servizi
            references R_Servizi,
    regola_presunti_definitivi bit
)
go

create table IC_tp_esclusioni
(
    ID_tp_esclusione int not null
        constraint PK_IC_tp_esclusioni
            primary key
                with (fillfactor = 95),
    tp_esclusione    nvarchar(200),
    ID_servizio      int
        constraint FK_IC_tp_esclusioni_R_Servizi
            references R_Servizi
)
go

create table IC_tp_indennita
(
    ID_tp_indennita int not null
        constraint PK_IC_tp_indennita
            primary key
                with (fillfactor = 95),
    tp_indennita    nvarchar(100),
    ID_servizio     int
        constraint FK_IC_tp_indennita_R_Servizi
            references R_Servizi
)
go

create table IC_tp_invalidita
(
    ID_tp_invalidita    int         not null
        constraint PK_IC_tp_invalidita
            primary key
                with (fillfactor = 95),
    tp_invalidita       nvarchar(150),
    minore              smallint,
    standard            smallint,
    ultrasessantacinque smallint,
    ordine              int,
    ID_servizio         int
        constraint FK_IC_tp_invalidita_R_Servizi
            references R_Servizi,
    fascia_duplicati    nvarchar(3) not null,
    redditi             bit,
    indennita           bit         not null,
    integrativo         bit         not null,
    pensione            bit         not null,
    maggiorazione       bit         not null,
    codice              nvarchar(50),
    ai_nodo             nvarchar(100)
)
go

create table IC_tp_istanze_pensione
(
    ID_tp_istanza_pensione int not null
        constraint PK_IC_tp_istanze_pensione
            primary key
                with (fillfactor = 95),
    tp_istanza_pensione    nvarchar(200),
    ID_servizio            int
        constraint FK_IC_tp_istanze_pensione_R_Servizi
            references R_Servizi
)
go

create table IC_tp_motivi_esclusione
(
    ID_tp_motivo_esclusione int not null
        constraint PK_IC_tp_motivi_esclusione
            primary key
                with (fillfactor = 95),
    tp_motivo_esclusione    nvarchar(200),
    ID_servizio             int
        constraint FK_IC_tp_motivi_esclusione_R_Servizi
            references R_Servizi,
    da_istruttoria          bit,
    ID_tp_esclusione        int
        constraint FK_IC_tp_motivi_esclusione_IC_tp_esclusioni
            references IC_tp_esclusioni
)
go

create table IC_esclusioni
(
    ID_domanda              int not null,
    ID_tp_motivo_esclusione int not null
        constraint FK_IC_esclusioni_IC_tp_motivi_esclusione
            references IC_tp_motivi_esclusione,
    data                    datetime,
    note                    nvarchar(500),
    ID_tp_esclusione        int not null
        constraint FK_IC_esclusioni_IC_tp_esclusioni
            references IC_tp_esclusioni,
    deceduto_mai_pagato     bit,
    constraint PK_IC_esclusioni
        primary key (ID_domanda, ID_tp_motivo_esclusione)
            with (fillfactor = 95)
)
go

create table IC_tp_presenze_redd_irpef
(
    ID_tp_presenza_redd_irpef int not null
        constraint PK_IC_tp_presenze_redd_irper
            primary key
                with (fillfactor = 95),
    tp_presenza_redd_irpef    nvarchar(100),
    ID_servizio               int
        constraint FK_IC_tp_presenze_redd_irper_R_Servizi
            references R_Servizi
)
go

create table IC_tp_redditi_definitivi
(
    ID_tp_reddito_definitivo int not null
        constraint PK_IC_tp_redditi_definitivi
            primary key
                with (fillfactor = 95),
    tp_reddito_definitivo    nvarchar(100),
    ID_servizio              int
        constraint FK_IC_tp_redditi_definitivi_R_Servizi
            references R_Servizi
)
go

create table IC_tp_relazioni_ue
(
    ID_tp_relazione_ue int not null
        constraint PK_IC_tp_relazioni_ue
            primary key
                with (fillfactor = 95),
    tp_relazione_ue    nvarchar(100),
    ID_servizio        int
        constraint FK_IC_tp_relazioni_ue_R_Servizi
            references R_Servizi
)
go

create table IC_tp_rette_rsa
(
    ID_tp_retta_rsa int not null
        constraint PK_IC_tp_rette_rsa
            primary key
                with (fillfactor = 95),
    tp_retta_rsa    nvarchar(50),
    ID_servizio     int
        constraint FK_IC_tp_rette_rsa_R_Servizi
            references R_Servizi
)
go

create table IC_tp_ricoveri_rsa
(
    ID_tp_ricovero_rsa int not null
        constraint PK_IC_tp_ricoveri_rsa
            primary key
                with (fillfactor = 95),
    tp_ricovero_rsa    nvarchar(200),
    ID_servizio        int
        constraint FK_IC_tp_ricoveri_rsa_R_Servizi
            references R_Servizi
)
go

create table IC_tp_stati_anomalie
(
    ID_tp_stato_anomalia int not null
        constraint PK_IC_tp_stati_anomalie
            primary key,
    tp_stato_anomalia    nvarchar(100),
    ID_servizio          int
        constraint FK_IC_tp_stati_anomalie_R_servizi
            references R_Servizi
)
go

create table IC_tp_titolare_redditi
(
    ID_tp_titolare_reddito int not null
        constraint PK_IC_tp_titolare_redditi
            primary key
                with (fillfactor = 95),
    tp_titolare_reddito    nvarchar(200),
    ID_servizio            int
        constraint FK_IC_tp_titolare_redditi_R_Servizi
            references R_Servizi
)
go

create table INTMIL_tp_presenze_redd
(
    ID_tp_presenza_redd int not null
        constraint PK_INTMIL_tp_presenze_redd
            primary key,
    tp_presenza_redd    nvarchar(200),
    ID_servizio         int
        constraint FK_INTMIL_tp_presenze_redd_R_Servizi
            references R_Servizi
)
go

create table INTMIL_tp_redditi_definitivi
(
    ID_tp_reddito_definitivo int not null
        constraint PK_INTMIL_tp_redditi_definitivi
            primary key,
    tp_reddito_definitivo    nvarchar(50),
    ID_servizio              int
        constraint FK_INTMIL_tp_redditi_definitivi_R_Servizi
            references R_Servizi
)
go

create table INTMIL_tp_stati_coniuge
(
    ID_tp_stato_coniuge int not null
        constraint PK_INTMIL_tp_stati_coniuge
            primary key,
    tp_stato_coniuge    nvarchar(200),
    ID_servizio         int
        constraint FK_INTMIL_tp_stati_coniuge_R_Servizi
            references R_Servizi
)
go

create table RPAF_tp_disponibilita
(
    ID_tp_disponibilita int not null
        constraint PK_RPAF_tp_disponibilita
            primary key,
    tp_disponibilita    nvarchar(50),
    ID_servizio         int
        constraint FK_RPAF_tp_disponibilita_RServizi
            references R_Servizi
)
go

create table RPAF_tp_documenti
(
    ID_tp_documento int not null
        constraint PK_RPAF_tp_documenti
            primary key,
    tp_documento    nvarchar(100),
    ID_servizio     int
        constraint FK_RPAF_tp_documenti_RServizi
            references R_Servizi
)
go

create table RPAF_tp_istituti
(
    ID_tp_istituto int not null
        constraint PK_RPAF_tp_istituti
            primary key,
    tp_istituto    nvarchar(50),
    ID_servizio    int
        constraint FK_RPAF_ID_servizio_RServizi
            references R_Servizi
)
go

create table RPAF_tp_lingue
(
    ID_tp_lingua int not null
        constraint PK_RPAF_tp_lingue
            primary key,
    tp_lingua    nvarchar(20),
    ID_servizio  int
        constraint FK_RPAF_tp_lingue_R_Servizi
            references R_Servizi
)
go

create table RPAF_tp_orari
(
    ID_tp_orario int not null
        constraint PK_RPAF_tp_orari
            primary key,
    tp_orario    nvarchar(50),
    ID_servizio  int
        constraint FK_RPAF_tp_orari_R_Servizi
            references R_Servizi
)
go

create table R_Abilitazioni
(
    ID_ente                int      not null,
    ID_ufficio             int      not null,
    ID_servizio            int      not null
        constraint FK_R_Abilitazioni_R_Servizi
            references R_Servizi,
    ID_ente_erogatore      int      not null
        constraint FK_R_Abilitazioni_R_Enti1
            references R_Enti,
    ID_ambito              nvarchar(50),
    enabled                smallint not null,
    ID_tp_accesso_pratiche smallint not null
        constraint FK_R_Abilitazioni_ID_tp_accesso_pratiche
            references tp_accesso_pratiche,
    constraint PK_R_Abilitazioni
        primary key nonclustered (ID_ente, ID_ufficio, ID_servizio, ID_ente_erogatore)
            with (fillfactor = 95),
    constraint FK_R_Abilitazioni_R_Uffici
        foreign key (ID_ente, ID_ufficio) references R_Uffici
)
go

create index ID_ente_eroga_R_Abilit
    on R_Abilitazioni (ID_ente_erogatore)
    with (fillfactor = 95)
go

create index [<Name of Missing Index, sysname,>]
    on R_Abilitazioni (ID_ufficio, enabled) include (ID_ente, ID_servizio, ID_ente_erogatore)
    with (fillfactor = 95)
go

create table R_Benefici
(
    ID_servizio      int      not null
        constraint FK_R_Benefici_R_Servizi
            references R_Servizi,
    ID_periodo       int      not null,
    ID_beneficio     int      not null,
    beneficio        nvarchar(100),
    importo_min      float,
    importo_max      float,
    scaglione        float,
    nodo_risultato   nvarchar(50),
    coeff_altro_comp real,
    is_risultato     smallint not null,
    constraint PK_R_Benefici
        primary key nonclustered (ID_servizio, ID_periodo, ID_beneficio)
            with (fillfactor = 95)
)
go

create table R_Fasce
(
    ID_servizio  int not null
        constraint FK_R_Fasce_R_Servizi
            references R_Servizi,
    ID_periodo   int not null,
    fascia       int not null,
    a_partire_da decimal(12, 2),
    importo      decimal(12, 2),
    constraint PK_R_Fasce
        primary key (ID_servizio, ID_periodo, fascia)
            with (fillfactor = 95)
)
go

create table R_Params
(
    ID_ente     int          not null
        constraint FK_R_Params_R_Enti
            references R_Enti,
    ID_servizio int          not null
        constraint FK_R_Params_R_Servizi
            references R_Servizi,
    ID_periodo  int          not null,
    param       nvarchar(30) not null,
    param_value text,
    enabled     smallint     not null,
    constraint PK_R_Params
        primary key (ID_ente, ID_servizio, ID_periodo, param)
            with (fillfactor = 95)
)
go

create table R_Periodi
(
    ID_periodo               int      not null,
    ID_servizio              int      not null
        constraint FK_R_Periodi_R_Servizi
            references R_Servizi,
    enabled                  smallint not null,
    data_inizio_trasmissione datetime,
    data_fine_trasmissione   datetime,
    constraint PK_R_Periodi
        primary key nonclustered (ID_periodo, ID_servizio)
            with (fillfactor = 95)
)
go

create table PROTO_Pitre_clesiusConfigurazione
(
    ID_servizio        int not null
        constraint FK_PROTO_Pitre_Configurazione_R_Servizi
            references R_Servizi,
    ID_periodo         int not null,
    ID_ente            int not null
        constraint FK_PROTO_Pitre_Configurazione_R_Enti
            references R_Enti,
    Proto_AbilitaDa    datetime,
    Proto_CodUfficio   nvarchar(255),
    Proto_Titolario    nvarchar(255),
    Proto_Filtro_Sql   nvarchar(max),
    Proto_AbilitaFinoA datetime,
    constraint PK_PROTO_Pitre_Configurazione_1
        primary key (ID_servizio, ID_periodo, ID_ente)
            with (fillfactor = 95),
    constraint FK_PROTO_Pitre_Configurazione_R_Periodi
        foreign key (ID_periodo, ID_servizio) references R_Periodi
)
go

create table R_Relazioni_parentela
(
    ID_relazione_parentela    int          not null
        constraint PK_R_Relazioni_parentela
            primary key
                with (fillfactor = 95),
    ID_servizio               int          not null
        constraint FK_R_relazioni_parentela_R_Servizi
            references R_Servizi,
    ID_periodo                int          not null,
    parentela                 nvarchar(70) not null,
    peso_reddito              numeric(8, 2),
    peso_patrimonio           numeric(8, 2),
    peso_componente           numeric(8, 2),
    ruolo                     smallint     not null,
    ordine                    smallint     not null,
    id_tp_relazione           int          not null
        constraint FK_R_Relazioni_parentela_tp_relazioni
            references tp_relazioni,
    ID_tp_relazione_parentela int,
    parentela_postprocessor   nvarchar(70),
    constraint FK_R_Relazioni_parentela_R_Periodi
        foreign key (ID_periodo, ID_servizio) references R_Periodi
)
go

create table R_Responsabili
(
    ID_user     int not null
        constraint FK_R_responsabili_R_Utenti
            references R_Utenti,
    ID_servizio int not null
        constraint FK_R_responsabili_R_Servizi
            references R_Servizi,
    constraint PK_R_responsabili
        primary key (ID_user, ID_servizio)
            with (fillfactor = 95)
)
go

create table R_Strutture
(
    ID_struttura int      not null
        constraint PK_R_Strutture
            primary key nonclustered
                with (fillfactor = 95),
    ID_servizio  int
        constraint FK_R_Strutture_R_Servizi1
            references R_Servizi,
    ID_ente      int,
    struttura    nvarchar(80),
    indirizzo    nvarchar(200),
    servizio     nvarchar(50),
    enabled      smallint not null
)
go

create index ID_servizio_R_Strutture
    on R_Strutture (ID_servizio)
    with (fillfactor = 95)
go

create index IX_R_Strutture
    on R_Strutture (ID_ente)
    with (fillfactor = 95)
go

create table SCUOLE_R_Scuole
(
    ID_scuola   int          not null,
    ID_servizio int          not null
        constraint FK_SCUOLE_R_Scuole_R_Servizi
            references R_Servizi,
    scuola      nvarchar(50) not null,
    servizio    nvarchar(50),
    constraint PK_SCUOLE_R_Scuole
        primary key (ID_scuola, ID_servizio)
            with (fillfactor = 95)
)
go

create table SCUOLE_R_Corsi
(
    ID_corso    int           not null,
    ID_scuola   int           not null,
    ID_servizio int           not null,
    corso       nvarchar(100) not null,
    scuola      nvarchar(50)  not null,
    servizio    nvarchar(50)  not null,
    tipo        nvarchar(10)  not null,
    borsa_max   int           not null,
    tp_classe   nvarchar(10),
    constraint PK_SCUOLE_R_Corsi
        primary key (ID_corso, ID_scuola)
            with (fillfactor = 95),
    constraint FK_SCUOLE_R_Corsi_SCUOLE_R_Corsi
        foreign key (ID_corso, ID_scuola) references SCUOLE_R_Corsi,
    constraint FK_SCUOLE_R_Corsi_SCUOLE_R_Scuole
        foreign key (ID_scuola, ID_servizio) references SCUOLE_R_Scuole
)
go

create table STUD_R_tariffe
(
    ID_servizio       int          not null
        constraint FK_STUD_R_tariffe_R_Servizi
            references R_Servizi,
    ID_luogo          nvarchar(6)  not null
        constraint FK_STUD_R_tariffe_R_Luoghi
            references R_Luoghi (ID_luogo),
    ID_scuola         smallint     not null
        constraint FK_STUD_R_tariffe_STUD_R_scuole
            references STUD_R_scuole,
    ID_tp_scuola      smallint     not null
        constraint FK_STUD_R_tariffe_STUD_tp_scuola
            references STUD_tp_scuola,
    ID_tp_beneficio   nvarchar(50) not null,
    ICEF_inf          float,
    ICEF_sup          float,
    importo_min       float,
    importo_max       float,
    sca               float,
    ICEF_lim          float,
    importo_0         float,
    ID_tp_tariffa     smallint     not null
        constraint FK_STUD_R_tariffe_STUD_tp_tariffe
            references STUD_tp_tariffe,
    solo_icef         smallint     not null,
    note_elabora      nvarchar(1100),
    scuola_mask       smallint     not null,
    ID_ente           int,
    luogo             nvarchar(40),
    per_1             float        not null,
    per_2             float        not null,
    per_3             float        not null,
    per_4             float        not null,
    per_5             float        not null,
    per_6             float        not null,
    ente_destinatario nvarchar(50),
    constraint PK_STUD_R_tariffe_1
        primary key (ID_servizio, ID_luogo, ID_scuola, ID_tp_scuola, ID_tp_beneficio),
    constraint FK_STUD_R_tariffe_STUD_tp_beneficio
        foreign key (ID_tp_beneficio, ID_tp_tariffa) references STUD_tp_beneficio
)
go

create index _dta_index_STUD_R_tariffe_8_2136498790__K2_K5_K4_K3_K1_6_7_8_9_10_11_12_13_19_20_21_22_23_24
    on STUD_R_tariffe (ID_luogo, ID_tp_beneficio, ID_tp_scuola, ID_scuola, ID_servizio) include (ICEF_inf, ICEF_sup,
                                                                                                 importo_min,
                                                                                                 importo_max, sca,
                                                                                                 ICEF_lim, importo_0,
                                                                                                 ID_tp_tariffa, per_1,
                                                                                                 per_2, per_3, per_4,
                                                                                                 per_5, per_6)
go

create table STUD_R_tariffe_2
(
    ID_servizio       int          not null
        constraint FK_STUD_R_tariffe_R_Servizi_2
            references R_Servizi,
    ID_luogo          nvarchar(6)  not null
        constraint FK_STUD_R_tariffe_R_Luoghi_2
            references R_Luoghi (ID_luogo),
    ID_scuola         smallint     not null
        constraint FK_STUD_R_tariffe_STUD_R_scuole_2
            references STUD_R_scuole,
    ID_tp_scuola      smallint     not null
        constraint FK_STUD_R_tariffe_STUD_tp_scuola_2
            references STUD_tp_scuola,
    ID_tp_beneficio   nvarchar(50) not null,
    ICEF_inf          float,
    ICEF_sup          float,
    importo_min       float,
    importo_max       float,
    sca               float,
    ICEF_lim          float,
    importo_0         float,
    ID_tp_tariffa     smallint     not null
        constraint FK_STUD_R_tariffe_STUD_tp_tariffe_2
            references STUD_tp_tariffe,
    solo_icef         smallint     not null,
    note_elabora      nvarchar(1100),
    scuola_mask       smallint     not null,
    ID_ente           int,
    luogo             nvarchar(40),
    per_1             float        not null,
    per_2             float        not null,
    per_3             float        not null,
    per_4             float        not null,
    per_5             float        not null,
    per_6             float        not null,
    ente_destinatario nvarchar(50),
    constraint PK_STUD_R_tariffe_2
        primary key (ID_servizio, ID_luogo, ID_scuola, ID_tp_scuola, ID_tp_beneficio),
    constraint FK_STUD_R_tariffe_STUD_tp_beneficio_2
        foreign key (ID_tp_beneficio, ID_tp_tariffa) references STUD_tp_beneficio
)
go

create table S_C_DefaultIn
(
    ID_scenario   int          not null,
    ID_servizio   int          not null
        constraint FK_S_C_DefaultIn_R_Servizi
            references R_Servizi,
    ID_periodo    int          not null,
    ID_ente       int          not null
        constraint FK_S_C_DefaultIn_R_Enti
            references R_Enti,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    default_value nvarchar(50) not null,
    modificabile  smallint,
    constraint PK_S_C_DefaultIn
        primary key (ID_scenario, ID_servizio, ID_periodo, ID_ente, net, node)
            with (fillfactor = 95),
    constraint FK_S_C_DefaultIn_C_NodIN
        foreign key (net, node) references C_NodIN
)
go

create table Siuss_r_conf
(
    ID_conf                     int           not null
        constraint PK_Siuss_r_conf
            primary key,
    ID_superservizio            int,
    ID_servizio                 int
        constraint FK_Siuss_r_conf_R_Servizi
            references R_Servizi,
    ID_periodo                  int,
    ID_beneficio                int           not null,
    ID_ente_erogatore           int           not null,
    classe_generazione_psa      nvarchar(200) not null,
    ID_tp_invio_dati            int           not null
        constraint FK_Siuss_r_conf_Siuss_tp_invii_dati
            references Siuss_tp_invii_dati,
    ID_tp_carattere_prestazione int           not null
        constraint FK_Siuss_r_conf_isee159_tp_caratteri_psa
            references isee159_tp_caratteri_psa,
    ID_tp_codice_prestazione    nvarchar(10)  not null
        constraint FK_Siuss_r_conf_isee159_tp_codici_psa
            references isee159_tp_codici_psa,
    ID_tp_presenza_prova_mezzi  int           not null
        constraint FK_Siuss_r_conf_Siuss_tp_presenze_prova_mezzi
            references Siuss_tp_presenze_prova_mezzi,
    ID_tp_presa_carico          int           not null
        constraint FK_Siuss_r_conf_Siuss_tp_prese_carico
            references Siuss_tp_prese_carico,
    cf_operatore                nvarchar(16)  not null,
    codice_ente                 nvarchar(32)  not null,
    denominazione_ente          nvarchar(100) not null,
    indirizzo_ente              nvarchar(4)   not null,
    enabled                     bit           not null,
    sql                         text
)
go

create table UNI_R_Scuole
(
    ID_servizio int           not null
        constraint FK_UNI_R_Scuole_R_Servizi
            references R_Servizi,
    ID_scuola   nvarchar(100) not null,
    scuola      nvarchar(200) not null,
    constraint PK_UNI_R_Scuole
        primary key (ID_servizio, ID_scuola)
            with (fillfactor = 95)
)
go

create table UNI_R_Corsi
(
    ID_servizio        int           not null
        constraint FK_UNI_R_Corsi_R_Servizi
            references R_Servizi,
    ID_scuola          nvarchar(100) not null,
    ID_corso           nvarchar(100) not null,
    codice_corso_unitn nvarchar(100),
    corso              nvarchar(200) not null,
    scuola             nvarchar(200),
    ID_tp_corso        nchar(10),
    tp_corso           nvarchar(50),
    ID_anno_corso      int,
    anno_corso         nvarchar(100),
    constraint PK_UNI_R_Corsi
        primary key (ID_servizio, ID_scuola, ID_corso)
            with (fillfactor = 95),
    constraint FK_UNI_R_Corsi_UNI_R_Scuole
        foreign key (ID_servizio, ID_scuola) references UNI_R_Scuole
)
go

create table VCS_associazioni
(
    ID_servizio                int           not null
        constraint VCS_associazioni_FK
            references R_Servizi,
    ID_associazione            int           not null,
    denominazione_ass_sportiva nvarchar(250) not null,
    sede                       nvarchar(250),
    CF_Partita_IVA             nvarchar(11),
    constraint VCS_associazioni_PK
        primary key (ID_servizio, ID_associazione)
)
go

create table VCS_discipline
(
    ID_servizio     int          not null,
    ID_associazione int          not null,
    ID_disciplina   int          not null,
    disciplina      varchar(250) not null,
    constraint VCS_discipline_PK
        primary key (ID_servizio, ID_associazione, ID_disciplina),
    constraint VCS_discipline_FK
        foreign key (ID_servizio, ID_associazione) references VCS_associazioni
)
go

create table isee159_r_conf_connessione
(
    ID_servizio int not null
        constraint PK_isee159_r_conf_connessione
            primary key
                with (fillfactor = 95)
        constraint FK_isee159_r_conf_connessione_R_Servizi
            references R_Servizi,
    difformi    bit not null,
    corrente    bit not null
)
go

create table isee159_tp_isee_servizi
(
    id_servizio            int not null
        constraint fk_isee159_tp_isee_servizi_r_servizi
            references R_Servizi,
    id_tp_isee             int not null,
    id_tp_modalita_calcolo int not null,
    constraint pk_isee159_tp_isee_servizi
        primary key (id_servizio, id_tp_isee, id_tp_modalita_calcolo)
            with (fillfactor = 95),
    constraint fk_isee159_tp_isee_servizi_tp_mod_cal
        foreign key (id_tp_isee, id_tp_modalita_calcolo) references isee159_tp_modalita_calcolo
)
go

create table s_c_gruppi
(
    id_gruppo   int          not null
        constraint PK_s_c_gruppi
            primary key
                with (fillfactor = 95),
    ID_servizio int          not null
        constraint FK_s_c_gruppi_R_Servizi
            references R_Servizi,
    nome        nvarchar(50) not null,
    descrizione nvarchar(200),
    data        datetime     not null
)
go

create table s_c_scenari
(
    id_scenario int          not null
        constraint PK_s_c_scenari
            primary key
                with (fillfactor = 95),
    id_gruppo   int          not null
        constraint FK_s_c_scenari_s_c_gruppi
            references s_c_gruppi,
    nome        nvarchar(50) not null,
    descrizione nvarchar(200),
    data        datetime     not null
)
go

create table tp_obbligo_mantenimento
(
    ID_tp_obbligo_mantenimento int           not null,
    tp_obbligo_mantenimento    nvarchar(200) not null,
    ID_servizio                int           not null
        constraint FK_tp_obbligo_mantenimento_R_Servizi
            references R_Servizi,
    ID_periodo                 int           not null,
    constraint PK_tp_obbligo_mantenimento
        primary key (ID_tp_obbligo_mantenimento, ID_servizio, ID_periodo)
            with (fillfactor = 95)
)
go

create table tp_pagamenti_servizi
(
    ID_servizio     int      not null
        constraint FK_tp_pagamenti_servizi_tp_pagamento
            references R_Servizi,
    ID_periodo      int      not null,
    ID_tp_pagamento smallint not null,
    constraint PK_Table_1
        primary key (ID_servizio, ID_periodo, ID_tp_pagamento)
            with (fillfactor = 95)
)
go

create table tp_servizi_psa
(
    ID_tp_servizio_psa int not null
        constraint PK_tp_servizi_psa
            primary key
                with (fillfactor = 95),
    tp_servizio_psa    nvarchar(200)
)
go

create table tp_sex
(
    ID_tp_sex nvarchar not null
        constraint PK_tp_sex
            primary key nonclustered
                with (fillfactor = 95),
    tp_sex    nvarchar not null
)
go

create table Soggetti
(
    ID_soggetto                                        int          not null
        constraint PK_Soggetti
            primary key
                with (fillfactor = 95),
    ID_user                                            int          not null
        constraint FK_Soggetti_R_Utenti
            references R_Utenti,
    cognome                                            nvarchar(55) not null,
    nome                                               nvarchar(35) not null,
    ID_provincia_nascita                               nvarchar(2)  not null,
    ID_luogo_nascita                                   nvarchar(6)  not null,
    data_nascita                                       datetime     not null,
    ID_tp_sex                                          nvarchar     not null
        constraint FK_Soggetti_tp_sex
            references tp_sex,
    codice_fiscale                                     nchar(16)    not null
        constraint IX_Soggetti
            unique
                with (fillfactor = 95),
    codice_fiscale_2                                   nchar(16),
    codice_fiscale_3                                   nchar(16),
    ID_tp_stato_soggetto                               int          not null,
    annotazioni                                        nvarchar(2000),
    comune_estero                                      nvarchar(35),
    proposto_documento_accettazione_firma_grafometrica int          not null,
    validazione_apss                                   int,
    conome_apss                                        nvarchar(135),
    nome_apss                                          nvarchar(135),
    deceduto_apss                                      int,
    id_assistito_apss                                  nvarchar(50),
    import_s1p                                         int,
    data_decesso                                       datetime,
    codice_fiscale_apss                                nchar(16),
    codice_fiscale_sogei                               nchar(16),
    data_validazione_sogei                             datetime,
    codice_cessazione_apss                             nvarchar(50),
    data_cessazione_apss                               datetime,
    constraint FK_Soggetti_R_Luoghi
        foreign key (ID_luogo_nascita, ID_provincia_nascita) references R_Luoghi
)
go

create table AMS_Soggetti_Determine
(
    ID_soggetto   int not null
        constraint FK_AMS_Soggetti_Determine_AMS_Registro
            references Soggetti,
    ID_Determina  int not null
        constraint FK_AMS_Soggetti_Determine_AMS_Determine
            references AMS_Determine,
    Motivazione   nvarchar(400),
    DataPreavviso datetime,
    ID_domanda    int,
    constraint PK_AMS_Soggetti_Determine
        primary key (ID_soggetto, ID_Determina)
            with (fillfactor = 95)
)
go

create table Dati_documento_accettazione_firma_grafometrica
(
    ID_soggetto            int          not null
        constraint PK_Dati_documento_accettazione_firma_grafometrica
            primary key
                with (fillfactor = 95)
        constraint FK_Dati_documento_accettazione_firma_grafometrica_icef_Soggetti
            references Soggetti,
    ID_user                int          not null
        constraint FK_Dati_documento_accettazione_firma_grafometrica_R_Utenti
            references R_Utenti,
    data_sottoscrizione    datetime     not null,
    luogo_sottoscrizione   nvarchar(50) not null,
    print_backup_file_name nvarchar(50),
    archiviato_pat         smallint,
    id_pat                 int,
    data_archiviazione     datetime,
    note                   char(200)
)
go

create table Dati_documento_accettazione_firma_grafometrica_storico
(
    data_storico           datetime     not null,
    ID_soggetto            int          not null
        constraint FK_Dati_documento_accettazione_firma_grafometrica_storico_icef_Soggetti
            references Soggetti,
    ID_user                int          not null
        constraint FK_Dati_documento_accettazione_firma_grafometrica_storico_R_Utenti
            references R_Utenti,
    data_sottoscrizione    datetime     not null,
    luogo_sottoscrizione   nvarchar(50) not null,
    print_backup_file_name nvarchar(50),
    constraint PK_Dati_documento_accettazione_firma_grafometrica_storico
        primary key (data_storico, ID_soggetto)
            with (fillfactor = 95)
)
go

create table Doc_edizioni_progressivo_soggetti
(
    id_gruppo      int not null
        constraint FK_Doc_edizioni_progressivo_soggetti_Doc_edizioni_progressivo_gruppi
            references Doc_edizioni_progressivo_gruppi,
    id_soggetto    int not null
        constraint FK_Doc_edizioni_progressivo_soggetti_Soggetti
            references Soggetti,
    id_progressivo int not null
        constraint IX_Doc_edizioni_progressivo_soggetti
            unique,
    constraint PK_Doc_edizioni_progressivo_soggetti
        primary key (id_gruppo, id_soggetto)
)
go

create table ER_eredi_istruttoria
(
    ID_soggetto_deceduto      int not null
        constraint FK_ER_eredi_istruttoria_Soggetti_d
            references Soggetti,
    ID_soggetto               int not null
        constraint FK_ER_eredi_istruttoria_Soggetti
            references Soggetti,
    ID_erede                  int,
    ID_tp_relazione_parentela int
        constraint FK_ER_eredi_istruttoria_ER_tp_relazioni_parentela
            references ER_tp_relazioni_parentela,
    incapace                  bit,
    rinuncia                  bit,
    tribunale_autorizzazione  nvarchar(200),
    data_autorizzazione       datetime,
    numero_autorizzazione     nvarchar(100),
    estremi_rinuncia          nvarchar(100),
    quota_num                 int,
    quota_den                 int,
    constraint PK_ER_eredi_istruttoria
        primary key (ID_soggetto_deceduto, ID_soggetto)
            with (fillfactor = 95)
)
go

create table ER_prestazioni_istruttoria
(
    ID_soggetto_deceduto int not null
        constraint FK_ER_prestazioni_istruttoria_Soggetti
            references Soggetti,
    ID_prestazione       int not null,
    ID_tp_prestazione    int not null
        constraint FK_ER_prestazioni_istruttoria_ER_tp_prestazioni
            references ER_tp_prestazioni,
    importo              numeric(12, 2),
    constraint PK_ER_prestazioni_istruttoria_1
        primary key (ID_soggetto_deceduto, ID_prestazione)
            with (fillfactor = 95)
)
go

create table RPAF_Soggetti_Determine
(
    ID_soggetto   int           not null
        constraint FK_RPAF_Soggetti_Determine_Soggetti
            references Soggetti,
    ID_Determina  int           not null
        constraint FK_RPAF_Soggetti_Determine_RPAF_Determine
            references RPAF_Determine,
    Motivazione   nvarchar(400) not null,
    DataPreavviso datetime,
    ID_domanda    int,
    constraint PK_RPAF_Soggetti_Determine
        primary key (ID_soggetto, ID_Determina)
            with (fillfactor = 95)
)
go

create index IX_cognome_Soggetti
    on Soggetti (cognome)
    with (fillfactor = 95)
go

create index [<Name of Missing Index, sysname,>]
    on Soggetti (cognome, nome) include (ID_soggetto, ID_user, ID_provincia_nascita, ID_luogo_nascita, data_nascita,
                                         ID_tp_sex, codice_fiscale)
    with (fillfactor = 95)
go

create table Soggetti_apapi
(
    ID_soggetto                int not null
        constraint PK_Soggetti_apapi
            primary key
                with (fillfactor = 95)
        constraint FK_Soggetti_apapi_Soggetti
            references Soggetti,
    ID_provincia               nvarchar(2),
    ID_comune                  nvarchar(6),
    indirizzo                  nvarchar(50),
    n_civ                      nvarchar(10),
    cap                        nvarchar(10),
    frazione                   nvarchar(50),
    ID_tp_cittadinanza         nvarchar(4),
    telefono                   nvarchar(20),
    fax                        nvarchar(20),
    e_mail                     nvarchar(50),
    ID_tp_pagamento            smallint,
    CIN_pagamento              nvarchar(3),
    ABI_pagamento              nvarchar(5),
    CAB_pagamento              nvarchar(5),
    Cc_pagamento               nvarchar(12),
    BBAN_corretto              smallint,
    IBAN                       nvarchar(50),
    Codice_stato               nvarchar(2),
    IBAN_corretto              smallint,
    ID_comune_catastale        nvarchar(4),
    ID_sportello               smallint,
    ID_tp_indirizzo            smallint
        constraint FK_Soggetti_apapi_Soggetti_apapi_tp_indirizzi
            references Soggetti_apapi_tp_indirizzi,
    indirizzo_principale       smallint,
    ID_provincia_altro         nvarchar(2),
    ID_comune_altro            nvarchar(6),
    indirizzo_altro            nvarchar(50),
    frazione_altro             nvarchar(50),
    n_civ_altro                nvarchar(10),
    cap_altro                  nvarchar(10),
    telefono_altro             nvarchar(20),
    altro_recapito_no          smallint,
    banca_pagamento            nvarchar(50),
    ubicazione_banca_pagamento nvarchar(50),
    note                       nvarchar(250),
    constraint FK_Soggetti_apapi_R_Luoghi
        foreign key (ID_comune, ID_provincia) references R_Luoghi
)
go

create table Soggetti_apapi_deleghe
(
    ID_soggetto                    int not null
        constraint FK_Soggetti_apapi_deleghe_Soggetti
            references Soggetti,
    ID_soggetto_delegato           int not null
        constraint FK_Soggetti_apapi_deleghe_Soggetti_giuridici
            references Soggetti_giuridici,
    ID_soggetto_giuridico_delegato int not null,
    constraint PK_Soggetti_apapi_deleghe
        primary key (ID_soggetto, ID_soggetto_delegato, ID_soggetto_giuridico_delegato)
            with (fillfactor = 95)
)
go

create table Soggetti_modificati
(
    ID_soggetto          int          not null
        constraint FK_Soggetti_modificati_Soggetti
            references Soggetti,
    ID_soggetto_old      int,
    ID_variazione        int          not null,
    cognome              nvarchar(35) not null,
    nome                 nvarchar(50) not null,
    ID_provincia_nascita nvarchar(2)  not null,
    ID_luogo_nascita     nvarchar(6)  not null,
    data_nascita         datetime     not null,
    ID_tp_sex            nvarchar     not null,
    codice_fiscale       nchar(16)    not null,
    data_modifica        datetime,
    ID_user              int,
    data_prenota         datetime,
    url                  varchar(250),
    confermato_apss      int,
    constraint PK_Soggetti_modificati
        primary key (ID_soggetto, ID_variazione)
            with (fillfactor = 95),
    constraint FK_Soggetti_modificati_R_Luoghi
        foreign key (ID_luogo_nascita, ID_provincia_nascita) references R_Luoghi
)
go

create table tp_soggetti
(
    id_tp_soggetto int not null
        constraint PK_tp_soggetti
            primary key
                with (fillfactor = 95),
    tp_soggetto    nvarchar(max)
)
go

create table GEA_soggetti_domanda
(
    id_blocco              int          not null,
    id_domanda             int          not null,
    edizione               int          not null,
    id_soggetto            int          not null,
    id_tp_soggetto         int          not null
        constraint FK_GEA_soggetti_domanda_tp_soggetti
            references tp_soggetti,
    cognome                nvarchar(60),
    nome                   nvarchar(35) not null,
    codice_fiscale         nvarchar(16) not null,
    id_luogo_nascita       nvarchar(6)
        constraint FK_GEA_soggetti_domanda_R_luoghi1
            references R_Luoghi (ID_luogo),
    data_nascita           datetime,
    sesso                  nvarchar,
    cap_residenza          nvarchar(5),
    id_luogo_residenza     nvarchar(6)
        constraint FK_GEA_soggetti_domanda_R_luoghi2
            references R_Luoghi (ID_luogo),
    frazione               nvarchar(50),
    indirizzo              nvarchar(50),
    n_civico               nvarchar(10),
    telefono               nvarchar(20),
    email                  nvarchar(50),
    id_tp_pagamento        smallint
        constraint FK_GEA_soggetti_domanda_tp_pagamento
            references tp_pagamento,
    iban_OK                int,
    iban                   nvarchar(50),
    intestatario_pagamento int          not null,
    id_provincia_altro     nvarchar(20),
    id_comune_altro        nvarchar(20),
    indirizzo_altro        nvarchar(255),
    frazione_altro         nvarchar(255),
    n_civ_altro            nvarchar(10),
    cap_altro              nvarchar(10),
    telefono_altro         nvarchar(50),
    presso_altro           nvarchar(255),
    constraint PK_GEA_soggetti_domanda
        primary key (id_blocco, id_domanda, edizione, id_soggetto, id_tp_soggetto)
            with (fillfactor = 95)
)
go

create index _dta_index_GEA_soggetti_domanda_8_1157071358__K2_K3_K5_1_4_6_7_8_9_10_11_12_13_14_15_16_17_18_19_20_21_22_23_24_25_26_27_28_29_
    on GEA_soggetti_domanda (id_domanda, edizione, id_tp_soggetto) include (id_blocco, id_soggetto, cognome, nome,
                                                                            codice_fiscale, id_luogo_nascita,
                                                                            data_nascita, sesso, cap_residenza,
                                                                            id_luogo_residenza, frazione, indirizzo,
                                                                            n_civico, telefono, email, id_tp_pagamento,
                                                                            iban_OK, iban, intestatario_pagamento,
                                                                            id_provincia_altro, id_comune_altro,
                                                                            indirizzo_altro, frazione_altro,
                                                                            n_civ_altro, cap_altro, telefono_altro,
                                                                            presso_altro)
    with (fillfactor = 95)
go

create index _dta_index_GEA_soggetti_domanda_7_1157071358__K22
    on GEA_soggetti_domanda (intestatario_pagamento)
    with (fillfactor = 95)
go

create index _dta_index_GEA_soggetti_domanda_7_1157071358__K2
    on GEA_soggetti_domanda (id_domanda)
    with (fillfactor = 95)
go

create index _dta_index_GEA_soggetti_domanda_7_1157071358__K2_K3_K5_8
    on GEA_soggetti_domanda (id_domanda, edizione, id_tp_soggetto) include (codice_fiscale)
    with (fillfactor = 95)
go

create index _dta_index_GEA_soggetti_domanda_7_1157071358__K2_K4_K3_K5_K22_K1_K21_19
    on GEA_soggetti_domanda (id_domanda, id_soggetto, edizione, id_tp_soggetto, intestatario_pagamento, id_blocco,
                             iban) include (id_tp_pagamento)
    with (fillfactor = 95)
go

create table tp_sperimentazione_uniTN
(
    ID_tp_sperimentazione_uniTN int not null
        constraint PK_tp_sperimentazione_uniTN
            primary key
                with (fillfactor = 95),
    tp_sperimentazione_uniTN    nvarchar(50)
)
go

create table tp_stati
(
    ID_tp_stato        int not null
        constraint PK_tp_stati
            primary key
                with (fillfactor = 90),
    tp_stato           nvarchar(250),
    in_dichiarazione   int,
    note_dichiarazione nvarchar(250),
    in_domanda         int,
    note_domanda       nvarchar(250)
)
go

create table tp_stati_civili
(
    ID_tp_stato_civile char         not null
        constraint PK_tp_stati_civili
            primary key
                with (fillfactor = 95),
    tp_stato_civile    nvarchar(50) not null
)
go

create table tp_stati_controllo
(
    id_tp_stato_controllo                                      int           not null
        constraint PK_tp_stati_controllo
            primary key
                with (fillfactor = 95),
    descrizione                                                nvarchar(200) not null,
    connettibile                                               smallint      not null,
    modificabile                                               smallint      not null,
    modificabile_da_nucleo_controllo                           smallint      not null,
    stato_impostabile_da_nucleo_controllo                      smallint      not null,
    stato_modificabile_da_nucleo_controllo                     smallint      not null,
    id_tp_stato_controllo_successivo_modifica_nucleo_controllo int
        constraint FK_tp_stati_controllo_tp_stati_controllo
            references tp_stati_controllo,
    modificata_da_nucleo_controllo                             smallint      not null
)
go

create table CTRL_tp_enti_segnalatori
(
    ID_tp_ente_segnalatore      int           not null
        constraint PK_CTRL_tp_enti_segnalatori
            primary key,
    descrizione                 nvarchar(200) not null,
    id_tp_primo_stato_controllo int           not null
        constraint FK_CTRL_tp_enti_segnalatori_tp_stati_controllo
            references tp_stati_controllo,
    descrizione_per_mail        nvarchar(200)
)
go

create table CTRL_dichiarazioni_anomale
(
    protocollo                         int            not null
        constraint PK_CTRL_2008_dichiarazioni_anomale
            primary key
                with (fillfactor = 95),
    anno                               int            not null,
    codice_fiscale                     nvarchar(16)   not null,
    ID_dichiarazione                   int            not null,
    anomalo                            smallint       not null,
    colore_anomalia                    smallint       not null,
    note                               text,
    note_anomalia_rilevata             text,
    diff_rilevata_quadro_C             decimal(12, 2) not null,
    diff_rilevata_quadro_C5            decimal(12, 2) not null,
    diff_rilevata_quadro_F             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_C    int            not null
        constraint FK_CTRL_2008_dichiarazioni_anomale_CTRL_2008_tp_tipologia_errore_quadro_C
            references CTRL_tp_tipologia_errore_quadro,
    ID_tp_descrizione_errore_quadro_C  int            not null
        constraint FK_CTRL_2008_dichiarazioni_anomale_CTRL_2008_tp_descrizione_errore_quadro_C
            references CTRL_tp_descrizione_errore_quadro,
    ID_tp_tipologia_errore_quadro_C5   int            not null
        constraint FK_CTRL_2008_dichiarazioni_anomale_CTRL_2008_tp_tipologia_errore_quadro_C5
            references CTRL_tp_tipologia_errore_quadro,
    ID_tp_descrizione_errore_quadro_C5 int            not null
        constraint FK_CTRL_2008_dichiarazioni_anomale_CTRL_2008_tp_descrizione_errore_quadro_C5
            references CTRL_tp_descrizione_errore_quadro,
    ID_tp_tipologia_errore_quadro_F    int            not null
        constraint FK_CTRL_2008_dichiarazioni_anomale_CTRL_2008_tp_tipologia_errore_quadro_F
            references CTRL_tp_tipologia_errore_quadro,
    ID_tp_descrizione_errore_quadro_F  int            not null
        constraint FK_CTRL_2008_dichiarazioni_anomale_CTRL_2008_tp_descrizione_errore_quadro_F
            references CTRL_tp_descrizione_errore_quadro,
    anomalia_quadro_C2                 smallint       not null,
    diff_rilevata_quadro_C2            decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_C2   int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_tipologia_errore_quadro_C2
            references CTRL_tp_tipologia_errore_quadro,
    ID_tp_descrizione_errore_quadro_C2 int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_descrizione_errore_quadro_C2
            references CTRL_tp_descrizione_errore_quadro,
    anomalia_quadro_D                  smallint       not null,
    diff_rilevata_quadro_D             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_D    int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_tipologia_errore_quadro_D
            references CTRL_tp_tipologia_errore_quadro,
    ID_tp_descrizione_errore_quadro_D  int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_descrizione_errore_quadro_D
            references CTRL_tp_descrizione_errore_quadro,
    anomalia_quadro_E                  smallint       not null,
    diff_rilevata_quadro_E             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_E    int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_tipologia_errore_quadro_E
            references CTRL_tp_tipologia_errore_quadro,
    ID_tp_descrizione_errore_quadro_E  int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_descrizione_errore_quadro_E
            references CTRL_tp_descrizione_errore_quadro,
    anomalia_quadro_G                  smallint       not null,
    diff_rilevata_quadro_G             decimal(12, 2) not null,
    ID_tp_tipologia_errore_quadro_G    int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_tipologia_errore_quadro_G
            references CTRL_tp_tipologia_errore_quadro,
    ID_tp_descrizione_errore_quadro_G  int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_descrizione_errore_quadro_G
            references CTRL_tp_descrizione_errore_quadro,
    ID_tp_ente_segnalatore             int            not null
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_enti_segnalatori
            references CTRL_tp_enti_segnalatori,
    ID_tp_altro_ente_segnalatore       int
        constraint FK_CTRL_dichiarazioni_anomale_CTRL_tp_altri_enti_segnalatori
            references CTRL_tp_altri_enti_segnalatori,
    dettagli_atro_ente_segnalatore     nvarchar(200),
    anomalia_quadro_C                  smallint       not null,
    anomalia_quadro_C5                 smallint       not null,
    anomalia_quadro_F                  smallint       not null,
    data_inserimento                   datetime       not null
)
go

create unique index IX_CTRL_dichiarazioni_anomale
    on CTRL_dichiarazioni_anomale (ID_dichiarazione)
    with (fillfactor = 95)
go

create table Dich_icef_azioni_controllo
(
    id_dichiarazione      int      not null,
    id_tp_stato_controllo int      not null
        constraint FK_Dich_icef_azioni_controllo_tp_stati_controllo
            references tp_stati_controllo,
    data_azione           datetime not null
)
go

create table tp_stati_doc
(
    ID_tp_stato_doc int          not null
        constraint PK_tp_stati_doc
            primary key nonclustered
                with (fillfactor = 95),
    tp_stato_doc    nvarchar(50) not null
)
go

create table Doc
(
    ID                     int not null
        constraint PK_Doc
            primary key nonclustered
                with (fillfactor = 90),
    ID_user                int not null
        constraint FK_Doc_R_Utenti
            references R_Utenti,
    protocollo             nvarchar(32),
    protocollo_ente        nvarchar(50),
    data_presentazione     datetime,
    luogo_presentazione    nvarchar(50),
    anno                   smallint,
    ID_tp_stato_doc        int
        constraint FK_Doc_tp_stati_doc
            references tp_stati_doc,
    errors                 nvarchar(500),
    annotazioni            nvarchar(200),
    ID_tp_stato            int
        constraint FK_Doc_tp_stati
            references tp_stati,
    data_stato             datetime,
    data_sottoscrizione    datetime,
    luogo_sottoscrizione   nvarchar(50),
    da_ricalcolare         smallint,
    ID_user_original       int,
    ID_tp_accesso          int
        constraint FK_Doc_tp_accesso
            references tp_accesso,
    info_tp_accesso        nvarchar(250),
    ente                   nvarchar(70),
    nome_addetto           nvarchar(50),
    ufficio                nvarchar(50),
    luogo_attestazione     nvarchar(50),
    data_attestazione      datetime,
    presenta_front_office  smallint,
    bloccante              int,
    crc                    int,
    pagata_CAAF            int,
    tp_sblocco             int not null,
    anno_cud               int,
    comunicazione_email    smallint,
    validata               smallint,
    token                  nvarchar(60),
    ID_tp_stato_fam        int,
    collaboratore_validata smallint,
    ID_collaboratore       int
)
go

create table C_Elab
(
    id_domanda  int          not null
        constraint FK_C_Elab_Doc
            references Doc,
    net         nvarchar(50) not null,
    elab_date   smalldatetime,
    fingerprint nvarchar(50),
    checkfp     smallint,
    n_elab      int,
    constraint PK_C_Elab
        primary key nonclustered (id_domanda, net)
            with (fillfactor = 90)
)
go

create table C_ElaIN
(
    ID_domanda  int          not null,
    net         nvarchar(50) not null,
    node        nvarchar(50) not null,
    input_value float,
    constraint PK_C_ElaIN
        primary key nonclustered (ID_domanda, net, node)
            with (fillfactor = 90),
    constraint FK_C_ElaIN_C_Elab
        foreign key (ID_domanda, net) references C_Elab,
    constraint FK_C_ElaIN_C_NodIN
        foreign key (net, node) references C_NodIN
)
go

create index IDX_C_elain_id_domanda_node
    on C_ElaIN (ID_domanda, node) include (input_value)
go

create index ID_domanda_C_ElaIN
    on C_ElaIN (ID_domanda)
    with (fillfactor = 90)
go

create table C_ElaOUT
(
    ID_domanda    int          not null,
    net           nvarchar(50) not null,
    node          nvarchar(50) not null,
    belief        real,
    numeric_value float,
    n_elab        int,
    constraint PK_C_ElaOUT
        primary key nonclustered (ID_domanda, net, node)
            with (fillfactor = 90),
    constraint FK_C_ElaOUT_C_Elab
        foreign key (ID_domanda, net) references C_Elab,
    constraint FK_C_ElaOUT_C_NodOUT
        foreign key (net, node) references C_NodOUT
)
go

create index ID_domanda_C_ElaOUT
    on C_ElaOUT (ID_domanda)
    with (fillfactor = 90)
go

create index IDX_C_ELaout_Id_Domanda_node
    on C_ElaOUT (ID_domanda, node) include (numeric_value)
go

create index idx_c_elab_id_Domanda
    on C_Elab (id_domanda)
go

create table Dich_icef
(
    ID_dichiarazione            int            not null
        constraint PK_Dich_icef
            primary key
                with (fillfactor = 95)
        constraint FK_Dichirazioni_icef_Doc
            references Doc,
    ID_soggetto                 int            not null
        constraint FK_Dichirazioni_icef_Soggetti
            references Soggetti,
    edizione                    smallint       not null,
    ID_dich                     int            not null
        constraint FK_Dich_icef_R_Dich
            references R_Dich,
    ID_provincia_residenza      nvarchar(2)    not null,
    ID_comune_residenza         nvarchar(6)    not null,
    indirizzo_residenza         nvarchar(50)   not null,
    n_civ_residenza             nvarchar(10)   not null,
    cap_residenza               nvarchar(10)   not null,
    telefono_residenza          nvarchar(20),
    ID_provincia_domicilio      nvarchar(2),
    ID_comune_domicilio         nvarchar(6),
    indirizzo_domicilio         nvarchar(50),
    n_civ_domicilio             nvarchar(10),
    cap_domicilio               nvarchar(10),
    reddito_irpef               decimal(12, 2) not null,
    anno_produzione_redditi     smallint       not null,
    anno_produzione_patrimoni   smallint,
    cittadinanza                nvarchar(50),
    ID_tp_cittadinanza          nvarchar(4)
        constraint FK_Dich_icef_tp_cittadinanza
            references tp_cittadinanza,
    quadro_A                    int            not null,
    quadro_B                    int            not null,
    quadro_C                    int            not null,
    quadro_D                    int            not null,
    quadro_E                    int            not null,
    quadro_F                    int            not null,
    minore_nullatenente         smallint       not null,
    ID_regione                  nvarchar(3)
        constraint FK_Dich_icef_R_Regioni
            references R_Regioni,
    codice_USL                  nvarchar(6)
        constraint FK_Dichirazioni_icef_R_ASL
            references R_ASL,
    ID_tp_attivita              nchar(4)
        constraint FK_Dichirazioni_icef_tp_attivita
            references tp_attivita,
    mq_complessivi              int,
    reddito_agrario_irap        decimal(12, 2),
    telefono_domicilio          nvarchar(20),
    modifica_utente             smallint       not null,
    quadro_C1                   int,
    quadro_C2                   int,
    quadro_C3                   int,
    quadro_C4                   int,
    quadro_C5                   int,
    mese                        smallint,
    anno_attualizzato           int,
    autoveicoli                 int,
    quadro_G                    int,
    id_tp_stato_controllo       int            not null
        constraint FK_Dich_icef_tp_stati_controllo
            references tp_stati_controllo,
    creata_da_domanda           smallint       not null,
    ID_tp_sperimentazione_uniTN int,
    motoveicoli                 int,
    navi                        int,
    e_mail                      nvarchar(50),
    constraint IX_Dichirazioni_icef
        unique (ID_soggetto, edizione)
            with (fillfactor = 95),
    constraint IX_Dichirazioni_icef_1
        unique (ID_soggetto, edizione)
            with (fillfactor = 95),
    constraint FK_Dichirazioni_icef_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi,
    constraint FK_Dichirazioni_icef_R_Luoghi1
        foreign key (ID_comune_domicilio, ID_provincia_domicilio) references R_Luoghi
)
go

create index idx_dichicef_anooredditi
    on Dich_icef (anno_produzione_redditi, anno_produzione_patrimoni) include (ID_dichiarazione, ID_soggetto,
                                                                               ID_provincia_residenza,
                                                                               ID_comune_residenza)
    with (fillfactor = 95)
go

create clustered index ID_USER_ID_ACCESSO
    on Doc (ID_user, ID_tp_accesso)
go

create index IX_Doc
    on Doc (info_tp_accesso)
    with (fillfactor = 90)
go

create index _dta_index_Doc_5_1952166150__K15_K1
    on Doc (da_ricalcolare, ID)
go

create index IDX_DOC_ID_Tp_stato
    on Doc (ID_tp_stato) include (ID)
go

create table Domande
(
    ID_domanda                    int            not null
        constraint PK_Domande
            primary key nonclustered
                with (fillfactor = 95)
        constraint FK_Domande_Doc
            references Doc,
    ID_ente_erogatore             int            not null
        constraint FK_Domande_R_Enti
            references R_Enti,
    ID_servizio                   int            not null
        constraint FK_Domande_R_Servizi
            references R_Servizi,
    ID_periodo                    int            not null,
    ID_soggetto                   int            not null
        constraint FK_Domande_Soggetti
            references Soggetti,
    cognome                       nvarchar(55),
    nome                          nvarchar(35),
    ID_tp_sex                     nvarchar
        constraint FK_Domande_tp_sex
            references tp_sex,
    ID_provincia_nascita          nvarchar(2)    not null,
    ID_luogo_nascita              nvarchar(6)    not null,
    data_nascita                  datetime,
    codice_fiscale                nvarchar(16)   not null,
    ID_provincia_residenza        nvarchar(2)    not null,
    ID_comune_residenza           nvarchar(6)    not null,
    indirizzo_residenza           nvarchar(50)   not null,
    n_civ_residenza               nvarchar(10)   not null,
    cap_residenza                 nvarchar(10)   not null,
    telefono_residenza            nvarchar(20),
    un_genitore                   smallint       not null,
    genitori_lavoratori           smallint       not null,
    n_invalidi_66_75              smallint       not null,
    n_invalidi_75                 smallint       not null,
    handicap_66_75_certificato_da nvarchar(50),
    handicap_75_certificato_da    nvarchar(50),
    proprietario_residenza        smallint       not null,
    in_locazione                  smallint       not null,
    cognome_intestatario          nvarchar(30),
    nome_intestatario             nvarchar(30),
    estremi_contratto_locazione   nvarchar(150),
    canone_locazione              decimal(12, 2) not null,
    proprieta_non_occupata        smallint       not null,
    sottoscritta_in_presenza      smallint       not null,
    sottoscritta_con_documento    smallint       not null,
    ID_tp_pagamento               smallint
        constraint FK_Domande_tp_pagamento
            references tp_pagamento,
    intestatario_pagamento        nvarchar(50),
    CIN_pagamento                 nvarchar(3),
    ABI_pagamento                 nvarchar(5),
    CAB_pagamento                 nvarchar(5),
    Cc_pagamento                  nvarchar(12),
    banca_pagamento               nvarchar(50),
    ubicazione_banca_pagamento    nvarchar(50),
    BBAN_corretto                 smallint       not null,
    e_mail                        nvarchar(50),
    cellulare                     nvarchar(50),
    ID_tp_cittadinanza            nvarchar(4)
        constraint FK_Domande_tp_cittadinanza
            references tp_cittadinanza,
    frazione_residenza            nvarchar(50),
    IBAN                          nvarchar(50),
    Codice_stato                  nvarchar(2),
    IBAN_corretto                 smallint,
    recapito_postale              nvarchar(50),
    fax                           nvarchar(20),
    ID_comune_catastale           nvarchar(4),
    ID_sportello                  smallint,
    escludi_ufficio               smallint,
    escludi_motivazione           nvarchar(200),
    mq                            int,
    lavoro_femminile              int,
    ID_tp_monogenitore            int,
    sottoscrizione_verbale        smallint       not null,
    ID_provincia_altro            nvarchar(2),
    ID_comune_altro               nvarchar(6),
    indirizzo_altro               nvarchar(50),
    frazione_altro                nvarchar(50),
    n_civ_altro                   nvarchar(10),
    cap_altro                     nvarchar(10),
    telefono_altro                nvarchar(20),
    presso_altro                  nvarchar(100),
    altro_recapito_no             smallint,
    ID_tp_indirizzo               smallint,
    indirizzo_principale          smallint,
    con_nucleo_ristretto          smallint,
    codice_fiscale_altro          nvarchar(16),
    ID_intestatario_pagamento     int,
    tp_soggetto_apapi             nvarchar(2),
    IBAN_forzato_domanda          int,
    ID_delegato                   int,
    ID_tp_delegato                int,
    inizio_validita               datetime,
    fine_validita                 datetime,
    ID_tp_intestatario_pagamento  int
        constraint FK_Domande_tp_intestatari_pagamento
            references tp_intestatari_pagamento,
    send_to_publisher             bit,
    send_to_publisher_OLD         bit,
    id_dichiarazione              int,
    protocollo_mittente_isee      nvarchar(32),
    non_presenta_isee             int            not null,
    isee_non_conforme             int            not null,
    escludi_ufficio_data          datetime,
    pec                           nvarchar(50),
    domicilio_digitale            int,
    marca_bollo_n                 nvarchar(14),
    marca_bollo_data              datetime,
    constraint FK_Domande_R_Luoghi
        foreign key (ID_luogo_nascita, ID_provincia_nascita) references R_Luoghi,
    constraint FK_Domande_R_Luoghi1
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi,
    constraint FK_Domande_R_Luoghi_altro
        foreign key (ID_comune_altro, ID_provincia_altro) references R_Luoghi,
    constraint FK_Domande_R_Periodi1
        foreign key (ID_periodo, ID_servizio) references R_Periodi
)
go

exec sp_addextendedproperty 'MS_Description', N'se BBAN è corretto allora 1, altrimenti 0', 'SCHEMA', 'dbo', 'TABLE',
     'Domande', 'COLUMN', 'BBAN_corretto'
go

create table AC_dati
(
    ID_domanda                          int            not null
        constraint PK_AC_dati
            primary key nonclustered
                with (fillfactor = 95)
        constraint FK_AC_dati_ID_domanda
            references Domande,
    data_inizio_residenza               datetime,
    nominativo_genitore                 nvarchar(60),
    ID_tp_residenza                     smallint       not null
        constraint FK_AC_dati_ID_tp_residenza
            references AC_tp_residenze,
    indennita_lp_7_98                   smallint       not null,
    assegni_dpr_1124_65                 smallint       not null,
    importo_assegni_dpr_1124_65         decimal(12, 2) not null,
    assegni_dpr_915_78                  smallint       not null,
    importo_assegni_dpr_915_78          decimal(12, 2) not null,
    assegni_dpr_1092_73                 smallint       not null,
    importo_assegni_dpr_1092_73         decimal(12, 2) not null,
    presentata_domanda_lp_7_98          smallint       not null,
    data_presentata_domanda_lp_7_98     datetime,
    presenta_contenstualmente_lp_7_98   smallint       not null,
    prestazioni_concesse_invalidita     smallint       not null,
    iscritto_aire                       smallint       not null,
    invalido_civile                     smallint       not null,
    cieco_civile                        smallint       not null,
    sordomuto                           smallint       not null,
    dichiarazione_MMG_intrasportabilita smallint       not null,
    dottore_MMG                         nvarchar(60),
    data_MMG                            datetime,
    ID_tp_assistenza_attuale            smallint       not null
        constraint FK_AC_dati_ID_tp_assistenza_attuale
            references AC_tp_assistenze_attuali,
    ID_tp_stato_civile_ass              smallint       not null
        constraint FK_AC_dati_ID_tp_stato_civile
            references AC_tp_stati_civili_ass,
    ID_tp_titolo_studio_ass             smallint       not null
        constraint FK_AC_dati_ID_tp_titolo_studio
            references AC_tp_titoli_studio_ass,
    domicilio_uguale_residenza          smallint       not null,
    ID_provincia_domicilio              nvarchar(2)    not null,
    ID_comune_domicilio                 nvarchar(6)    not null,
    cap_domicilio                       nvarchar(5),
    frazione_domicilio                  nvarchar(50),
    indirizzo_domicilio                 nvarchar(50),
    n_civ_domicilio                     nvarchar(10),
    telefono_domicilio                  nvarchar(20),
    incomp_prest_agg_27                 smallint       not null,
    incomp_vita_indip                   smallint       not null,
    incomp_cong_retr                    smallint       not null,
    nome_cong_retr                      nvarchar(60),
    incomp_cong_retr_no_ben             smallint       not null,
    nome_cong_retr_no_ben               nvarchar(60),
    incomp_lp_6                         smallint       not null,
    nome_lp_6                           nvarchar(60),
    incomp_lp_11                        smallint       not null,
    incomp_rsa                          smallint       not null,
    acconsente_trattamento_dati         smallint       not null,
    cf_requisito                        smallint       not null,
    cf_certificatore                    smallint       not null,
    residenza_requisito                 smallint       not null,
    residenza_certificatore             smallint       not null,
    indennita_requisito                 smallint       not null,
    indennita_certificatore             smallint       not null,
    compatibilita_requisito             smallint       not null,
    compatibilita_certificatore         smallint       not null,
    ID_tp_cessazione                    smallint       not null,
    data_cessazione                     datetime,
    data_inizio_pagamento               datetime,
    mail_apapi_residenza                smallint       not null,
    posizione_workflow                  smallint       not null,
    atlante_locked                      smallint       not null,
    data_decorrenza_ia                  datetime,
    progressivo_elab                    smallint,
    ICEF_prec                           float,
    constraint FK_AC_dati_R_Luoghi
        foreign key (ID_comune_domicilio, ID_provincia_domicilio) references R_Luoghi
)
go

create table AC_referenti
(
    ID_domanda             int         not null
        constraint PK_AC_referenti
            primary key nonclustered
                with (fillfactor = 95)
        constraint FK_AC_referenti_Domande
            references Domande,
    ID_provincia_residenza nvarchar(2) not null,
    ID_comune_residenza    nvarchar(6) not null,
    cap_residenza          nvarchar(5),
    frazione_residenza     nvarchar(50),
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    telefono1              nvarchar(20),
    telefono2              nvarchar(20),
    ID_soggetto            int         not null,
    ID_tp_legame_rif       smallint    not null
        constraint FK_AC_referenti_AC_tp_legami_rif
            references AC_tp_legami_rif,
    ID_tp_professione_rif  smallint    not null
        constraint FK_AC_referenti_AC_tp_professioni_rif
            references AC_tp_professioni_rif,
    tenuto_pagamento       smallint    not null,
    referente_contattare   smallint    not null,
    rappresentante_legale  smallint    not null,
    familiare_adeguato     smallint    not null,
    convivente             smallint    not null,
    amministatore_sostegno smallint    not null,
    tutore                 smallint    not null,
    curatore               smallint    not null,
    affidatario            smallint    not null,
    constraint FK_AC_referenti_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table AC_riaccertamento_economico
(
    ID_domanda                  int      not null
        constraint PK_AC_riaccertamento_economico
            primary key
                with (fillfactor = 95)
        constraint FK_AC_riaccertamento_economico_Domande
            references Domande,
    spedita_apss                smallint not null,
    atlante_locked              smallint not null,
    acconsente_trattamento_dati smallint not null
)
go

create table AC_riaccertamento_sanitario
(
    ID_domanda                          int      not null
        constraint PK_AC_riaccertamento_sanitario
            primary key
                with (fillfactor = 95)
        constraint FK_AC_riaccertamento_sanitario_Domande
            references Domande,
    spedita_apss                        smallint not null,
    certificato_medico                  smallint not null,
    medico                              nvarchar(100),
    data_certificato                    datetime,
    acconsente_trattamento_dati         smallint not null,
    dichiarazione_MMG_intrasportabilita smallint not null,
    dottore_MMG                         nvarchar(60),
    data_MMG                            datetime
)
go

create table ALV_Dati
(
    ID_domanda            int         not null
        constraint PK_ALV_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_ALV_Dati_Domande
            references Domande,
    ID_luogo              nvarchar(6) not null,
    ID_zona_svantaggiata  smallint    not null,
    denominazione_azienda nvarchar(50),
    titolare              smallint,
    unita_attiva          smallint,
    bovini                smallint,
    suini                 smallint,
    ovini                 smallint,
    caprini               smallint,
    equini                smallint,
    fondo_pensione        nvarchar(50),
    importo_versamento    decimal(10, 2),
    fondo_pensione_iban   nvarchar(27),
    ID_fondo              int,
    ID_servizio           int,
    constraint FK_ALV_Dati_IVS_R_luoghi_zone_svantaggiate
        foreign key (ID_luogo, ID_servizio) references IVS_R_luoghi_zone_svantaggiate
)
go

create table AMS_dispo_comuni
(
    id_domanda int         not null
        constraint FK_AMS_dispo_comuni_Domande
            references Domande,
    id_ente    int         not null
        constraint FK_AMS_dispo_comuni_R_enti
            references R_Enti,
    id_luogo   nvarchar(6) not null
        constraint FK_AMS_dispo_comuni_R_luoghi
            references R_Luoghi (ID_luogo),
    preferito  bit,
    escluso    bit,
    constraint PK_AMS_Comunita
        primary key (id_domanda, id_ente, id_luogo),
    constraint FK_AMS_dispo_comuni_AMS_dispo_comuni
        foreign key (id_domanda, id_ente, id_luogo) references AMS_dispo_comuni
)
go

create table AMS_dispo_comunita
(
    ID_domanda int not null
        constraint FK_AMS_dispo_comunita_Domande
            references Domande,
    ID_ente    int not null
        constraint FK_AMS_dispo_comunita_R_enti
            references R_Enti,
    constraint PK_AMS_dispo_comunita
        primary key (ID_domanda, ID_ente)
)
go

create table AM_Richiedente
(
    ID_domanda                        int            not null
        constraint PK_AM_Richiedente
            primary key
                with (fillfactor = 95)
        constraint FK_AM_Richiedente_Domande
            references Domande,
    tit_es                            smallint       not null,
    tit_es_numero                     nvarchar(50)   not null,
    tit_es_anno                       smallint,
    tit_es_tribunale                  nvarchar(50)   not null,
    tit_es_importo                    decimal(12, 2) not null,
    precetto                          smallint       not null,
    precetto_data                     datetime,
    sent_fall                         smallint       not null,
    sent_fall_numero                  nvarchar(50),
    sent_fall_emessa_da               nvarchar(50),
    residenza                         smallint       not null,
    stato_famiglia                    smallint       not null,
    altro                             smallint       not null,
    altro_desc                        nvarchar(50),
    importo_indennita_accompagnamento decimal(8, 2)  not null,
    deroga                            smallint       not null,
    ID_tp_variazione_situazione_dom   smallint       not null,
    data_revoca                       datetime,
    ID_tp_rapporto                    smallint,
    data_precedente                   datetime,
    data_precedente_motivo            nvarchar(50),
    mese_rivalutazione                smallint
)
go

create table AM_Genitori
(
    ID_domanda             int      not null
        constraint FK_AM_Genitore_AM_Richiedente
            references AM_Richiedente,
    ID_soggetto            int      not null,
    genitore               smallint not null,
    ID_provincia_residenza nvarchar(2),
    ID_comune_residenza    nvarchar(6),
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    cap_residenza          nvarchar(10),
    telefono_residenza     nvarchar(20),
    ID_tp_cittadinanza     nvarchar(4)
        constraint FK_AM_Genitori_tp_cittadinanza
            references tp_cittadinanza,
    irreperibile           smallint,
    constraint PK_AM_Genitore
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95),
    constraint FK_AM_Genitori_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table ANF_Genitori
(
    ID_domanda             int         not null
        constraint PK_ANF_Genitori_1
            primary key
                with (fillfactor = 95)
        constraint FK_ANF_Genitori_Domande
            references Domande,
    cognome                nvarchar(35),
    nome                   nvarchar(35),
    data_nascita           datetime,
    ID_provincia_residenza nvarchar(2) not null,
    ID_comune_residenza    nvarchar(6) not null,
    ID_tp_un_genitore      smallint
        constraint FK_ANF_Genitori_ANF_tp_un_Genitore
            references ANF_tp_un_genitore,
    un_genitore_data       datetime,
    un_genitore_attest     nvarchar(50),
    un_genitore_didata     datetime,
    irreperibile           smallint,
    constraint FK_ANF_Genitori_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table ANF_requisiti_05
(
    ID_domanda int      not null
        constraint PK_ANF_requisiti_05
            primary key
                with (fillfactor = 95)
        constraint FK_ANF_requisiti_05_Domande
            references Domande,
    giu_lav_05 smallint not null,
    giu_res_05 smallint not null,
    lug_lav_05 smallint not null,
    lug_res_05 smallint not null,
    ago_lav_05 smallint not null,
    ago_res_05 smallint not null,
    set_lav_05 smallint not null,
    set_res_05 smallint not null,
    ott_lav_05 smallint not null,
    ott_res_05 smallint not null,
    nov_lav_05 smallint not null,
    nov_res_05 smallint not null
)
go

create table ANF_richiedente
(
    ID_domanda                         int      not null
        constraint PK_ANF_richiedente
            primary key
                with (fillfactor = 95)
        constraint FK_ANF_richiedente_Domande
            references Domande,
    frazione                           nvarchar(50),
    resTAA_5anni                       smallint not null,
    resTAA_1anno                       smallint not null,
    ID_tp_occupazione                  smallint not null
        constraint FK_ANF_richiedente_ANF_tp_occupazione
            references ANF_tp_occupazione,
    un_genitore                        smallint not null,
    ID_tp_un_genitore                  smallint,
    un_genitore_data                   datetime,
    un_genitore_attest                 nvarchar(50),
    un_genitore_didata                 datetime,
    conv_coniuge                       datetime,
    dati_aggiornati_a                  datetime,
    esclusione_ufficio                 int      not null,
    dic_lav_prec                       smallint not null,
    dic_res_prec                       smallint not null,
    gen_lav                            smallint not null,
    gen_res                            smallint not null,
    feb_lav                            smallint not null,
    feb_res                            smallint not null,
    mar_lav                            smallint not null,
    mar_res                            smallint not null,
    apr_lav                            smallint not null,
    apr_res                            smallint not null,
    mag_lav                            smallint,
    mag_res                            smallint not null,
    giu_lav                            smallint not null,
    giu_res                            smallint not null,
    lug_lav                            smallint not null,
    lug_res                            smallint not null,
    ago_lav                            smallint not null,
    ago_res                            smallint not null,
    set_lav                            smallint not null,
    set_res                            smallint not null,
    ott_lav                            smallint not null,
    ott_res                            smallint not null,
    nov_lav                            smallint not null,
    nov_res                            smallint not null,
    dic_lav                            smallint not null,
    dic_res                            smallint not null,
    crc                                int      not null,
    un_genitore_no                     smallint,
    invio_fam                          smallint not null,
    cittadinoUE_dom_ID_luogo           nvarchar(6),
    cittadinoUE_dom_lavoro             nvarchar(50),
    cittadinoUE_dom_ente               nvarchar(50),
    cittadinoUE_dom_ID_tp_cittadinanza nvarchar(4),
    cittadinoUE_dom_anfEstero          decimal,
    ID_tipo_cittadinanza               smallint
        constraint FK_ANF_richiedente_ANF_tp_cittadinanza
            references ANF_tp_cittadinanza,
    ID_tp_residenza                    nchar(10)
)
go

create table ANTICRISI_dati
(
    ID_domanda             int      not null
        constraint PK_ANTICRISI_dati
            primary key
                with (fillfactor = 95)
        constraint FK_ANTICRISI_dati_Domande
            references Domande,
    pensplan_rinuncia      smallint,
    pensplan_rinuncia_data datetime,
    ID_codice_covip        smallint not null
        constraint FK_ANTICRISI_dati_ANTICRISI_tp_codici_covip
            references ANTICRISI_tp_codici_covip
)
go

create table ANTICRISI_periodi
(
    ID_domanda                 int      not null
        constraint FK_ANTICRISI_periodi_Domande
            references Domande,
    periodo                    smallint not null,
    ID_pez1                    char     not null,
    periodo_dal                datetime,
    periodo_al                 datetime,
    ID_tp_periodo              smallint
        constraint FK_ANTICRISI_periodi_ANTICRISI_tp_periodi
            references ANTICRISI_tp_periodi,
    presso                     nvarchar(50),
    sede                       nvarchar(50),
    descrizione_periodo        smallint not null,
    ore_cassa_integrazione     int,
    fondo_pensione_descrizione nvarchar(50),
    agenzia_lavoro             smallint,
    periodo_import             int      not null,
    constraint PK_ANTICRISI_periodi
        primary key (ID_domanda, periodo)
)
go

create table ANTICRISI_periodi_dati
(
    ID_domanda               int      not null,
    periodo                  smallint not null,
    situazione               smallint,
    ID_tp_motivazione        smallint
        constraint FK_ANTICRISI_periodi_dati_ANTICRISI_tp_motivazioni
            references ANTICRISI_tp_motivazioni,
    lav_dip_ultimo           smallint,
    lav_dip_cumulato         smallint,
    lav_prog_collaborazione  smallint,
    lav_prog_monocommittenza smallint,
    lav_prog_RL_limitato     smallint,
    no_domestico_stagionale  smallint,
    no_dimissioni            smallint,
    no_assunzioni            smallint,
    no_iva                   smallint,
    disp_lavoro              smallint,
    residenzaTAA             smallint,
    cassa_integrazione       smallint,
    cassa_integrazione_mesi  int,
    constraint PK_ANTICRISI_periodi_dati
        primary key (ID_domanda, periodo),
    constraint FK_ANTICRISI_periodi_dati_ANTICRISI_periodi
        foreign key (ID_domanda, periodo) references ANTICRISI_periodi
)
go

create table ANTICRISI_sospensioni
(
    ID_domanda         int      not null,
    periodo            smallint not null,
    sospensione        smallint not null,
    prova              nvarchar(50),
    sospensione_dal    datetime,
    sospensione_al     datetime,
    periodo_import     int      not null,
    sospensione_import int      not null,
    constraint PK_ANTICRISI_sospensioni
        primary key (ID_domanda, periodo, sospensione),
    constraint FK_ANTICRISI_sospensioni_ANTICRISI_periodi1
        foreign key (ID_domanda, periodo) references ANTICRISI_periodi
)
go

create table ASSNA_Dati
(
    ID_domanda                          int      not null
        constraint PK_ASSNA_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_ASSNA_Dati_Domande
            references Domande,
    anno                                smallint,
    ID_soggetto_assistito               int
        constraint FK_ASSNA_Dati_Soggetti
            references Soggetti,
    ID_relazione_parentela              int,
    resTAA_5                            smallint,
    resTAA_1                            smallint,
    percettoreSussidioEco               smallint,
    pensDirettaNO                       smallint,
    pensDiretta                         smallint,
    pensDiretta_data                    datetime,
    pensAnzianitaNO                     smallint,
    pensVecchiaiaNO                     smallint,
    pensVecchiaia                       smallint,
    pensVecchiaia_data                  datetime,
    minoreNonIscrittoStruttureEdu       smallint not null,
    iscrittoFormePensionistiche         smallint,
    versamentiPrevidenziali             smallint,
    ID_ente                             int,
    prestazioniEquivalenti              smallint,
    fondoPensioneIban                   nvarchar(50),
    prestazioniEquivalentiDenominazione nvarchar(100),
    fondoPensione                       smallint,
    fondoPensioneVersamenti             smallint,
    noFondoPensione                     smallint,
    versamentiTrimestrali               smallint,
    domandaSuccPrima                    smallint,
    versamentiTrimestraliDomSucc        smallint,
    versamentiPrevidenzialiDomSucc      smallint,
    ID_soggetto_padre                   int
        constraint FK_ASSNA_Dati_Soggetti_padre
            references Soggetti,
    astensione                          smallint,
    astensioneLavDip                    smallint,
    astensioneAuto                      smallint,
    astensioneLibProf                   smallint,
    ID_servizio                         int      not null
        constraint FK_ASSNA_Dati_R_servizi
            references R_Servizi,
    ID_fondo                            int      not null,
    contribuzioneIVS_1                  int,
    contribuzioneIVS_2                  int,
    contribuzioneIVS_3                  int,
    contribuzioneIVS_4                  int,
    contribuzioneIVS_5                  int,
    totaleVersamenti                    float,
    residenzaTrentino                   smallint,
    conviventeMoreUxorio                smallint,
    noTempoPienoAParziale               smallint,
    prevVolObb                          smallint,
    prevComp                            smallint,
    noAttivitaLavorativa                smallint,
    aspettativaNonRetribuita            smallint,
    constraint FK_ASSNA_Dati_ALV_fondi_pensplan
        foreign key (ID_fondo, ID_servizio) references ALV_fondi_pensplan,
    constraint FK_ASSNA_Dati_ASSNA_tp_anno
        foreign key (anno, ID_servizio) references ASSNA_tp_anno
)
go

create table ASSFIGLI_congedo
(
    ID_domanda          int      not null
        constraint FK_ASSFIGLI_congedo_ASSNA_Dati
            references ASSNA_Dati,
    ID_congedo          int      not null,
    ID_tp_genitore      int      not null
        constraint FK_ASSFIGLI_congedo_ASSFIGLI_tp_genitori
            references ASSFIGLI_tp_genitori,
    data_inizio_congedo datetime not null,
    data_fine_congedo   datetime not null,
    constraint PK_ASSFIGLI_congedo
        primary key (ID_domanda, ID_congedo)
)
go

create table ASSFIGLI_figli
(
    ID_domanda           int not null
        constraint FK_ASSFIGLI_figli_ASSNA_Dati
            references ASSNA_Dati,
    ID_soggetto          int not null
        constraint FK_ASSFIGLI_figli_Soggetti
            references Soggetti,
    data_inizio_adozione datetime,
    data_fine_adozione   datetime,
    constraint PK_ASSFIGLI_figli_1
        primary key (ID_domanda, ID_soggetto)
)
go

create table ASSNA_Periodi
(
    ID_domanda                    int      not null
        constraint FK_ASSNA_Periodi_ASSNA_Dati
            references ASSNA_Dati,
    ID_periodo                    smallint not null,
    periodo_dal                   datetime,
    periodo_al                    datetime,
    ID_tp_copertura_previdenziale smallint
        constraint FK_ASSNA_Periodi_ASSNA_tp_coperture_previdenziali
            references ASSNA_tp_coperture_previdenziali,
    ente_previdenziale            nvarchar(50),
    constraint PK_ASSNA_Periodi
        primary key (ID_domanda, ID_periodo)
            with (fillfactor = 95)
)
go

create table ASSNA_Lavori
(
    ID_domanda             int      not null,
    ID_periodo             smallint not null,
    lavoro                 smallint not null,
    datoreLavoro           nvarchar(150),
    categoriaCCNL          nvarchar(150),
    oreSettimanaliParTime  int,
    oreSettimanaliFullTime int,
    constraint PK_ASSNA_Lavori
        primary key (ID_domanda, ID_periodo, lavoro)
            with (fillfactor = 95),
    constraint FK_ASSNA_Lavori_ASSNA_Periodi
        foreign key (ID_domanda, ID_periodo) references ASSNA_Periodi
)
go

create table ASSNA_Versamenti
(
    ID_domanda                        int      not null,
    ID_periodo                        smallint not null,
    versamento                        smallint not null,
    data_versamento                   datetime,
    importo                           float,
    ID_tp_versamento                  smallint
        constraint FK_ASSNA_Versamenti_ASSNA_tp_versamenti
            references ASSNA_tp_versamenti,
    data_versamento_dal               datetime,
    data_versamento_al                datetime,
    ID_tp_tempoCoperturaPrevidenziale smallint
        constraint FK_ASSNA_Versamenti_ASSNA_tp_TempoCoperture_previdenziali
            references ASSNA_tp_tempoCoperture_previdenziali,
    num_periodi                       int,
    constraint PK_ASSNA_Versamenti
        primary key (ID_domanda, ID_periodo, versamento)
            with (fillfactor = 95),
    constraint FK_ASSNA_Versamenti_ASSNA_Periodi
        foreign key (ID_domanda, ID_periodo) references ASSNA_Periodi
)
go

create table AUP_dati
(
    ID_domanda                      int not null
        constraint PK_AUP_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_AUP_dati_Domande
            references Domande,
    ID_servizio                     int not null,
    nucleo_residenza                smallint,
    nucleo_sociale                  smallint,
    nucleo_sociale_spesa            decimal(12, 2),
    nucleo_ospitato                 smallint,
    nucleo_senza_fissa_dimora       smallint,
    nucleo_struttura_parz           smallint,
    nucleo_struttura_parz_presso    nvarchar(200),
    nucleo_struttura_parz_decurt    float,
    nucleo_struttura_tot            smallint,
    soc_riduzione_qtaA              int,
    soc_adesione_progetto           int,
    soc_icef_redditi                int,
    ID_tp_esclusione                int,
    nucleo_non_disponibile_info_lav int,
    domanda_sociale                 int,
    forza_sociale                   int,
    immobili_entrate                int,
    causa_emergenza                 int,
    no_diritto_emergenza            int,
    mensilita_percepite_emergenza   int,
    imm_imp_mese_prec               decimal(12, 2),
    imm_imp_due_mesi_prec           decimal(12, 2),
    indennita                       decimal(12, 2),
    data_patrimonio_attualizzato    datetime,
    no_diritto_titolari_soci_prof   int,
    no_pensione                     int,
    no_importo_beni_immobili        int,
    causa_emergenza_2               int,
    no_diritto_emergenza_2          int,
    mensilita_percepite_emergenza_2 int,
    imm_imp_mese_prec_2             decimal(12, 2),
    imm_imp_due_mesi_prec_2         decimal(12, 2),
    indennita_2                     decimal(12, 2),
    data_patrimonio_attualizzato_2  datetime,
    no_diritto_titolari_soci_prof_2 int,
    no_pensione_2                   int,
    no_importo_beni_immobili_2      int,
    data_att_lav_stag               datetime,
    ISEE                            decimal(12, 2),
    prot_ISEE                       nvarchar(50),
    autodichiarazione_ARERA         smallint,
    isee_12                         smallint,
    isee_20                         smallint,
    residente_TN_2annicont          smallint,
    constraint FK_AUP_dati_AUP_tp_esclusione_domanda
        foreign key (ID_tp_esclusione, ID_servizio) references AUP_tp_esclusione_domanda
)
go

create table Altri_genitori
(
    ID_domanda             int          not null
        constraint PK_altri_genitori
            primary key
                with (fillfactor = 95)
        constraint FK_Altri_genitori_Domande
            references Domande,
    cognome                nvarchar(35) not null,
    nome                   nvarchar(35) not null,
    data_nascita           datetime,
    ID_provincia_residenza nvarchar(2),
    ID_comune_residenza    nvarchar(6),
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    cap_residenza          nvarchar(10),
    irreperibile           smallint
)
go

create table Assegni
(
    id_domanda           int            not null
        constraint PK_Assegni
            primary key
                with (fillfactor = 95)
        constraint FK_Assegni_Domande
            references Domande,
    id_tp_titolo         int            not null
        constraint FK_Assegni_Assegni_tp_titolo
            references Assegni_tp_titolo,
    no_altri_trattamenti smallint       not null,
    is_deduzione         smallint       not null,
    deduzioni            numeric(10, 2),
    deduzione_ente       nvarchar(100),
    is_richiesta         smallint       not null,
    richiesta_ente       nvarchar(100),
    is_precedente        int            not null,
    precedente           numeric(10, 2) not null,
    anno_fam_numerose    int,
    giorno_start         int,
    mese_start           int,
    giorno_end           int,
    mese_end             int
)
go

create table Assegni_madre
(
    id_domanda             int          not null
        constraint PK_Assegni_madre
            primary key
                with (fillfactor = 95)
        constraint FK_Assegni_madre_Assegni
            references Assegni,
    cognome                nvarchar(50) not null,
    nome                   nvarchar(50) not null,
    codice_fiscale         nvarchar(16),
    data_nascita           datetime,
    id_provincia_nascita   nvarchar(2),
    id_luogo_nascita       nvarchar(6),
    id_provincia_residenza nvarchar(2),
    id_luogo_residenza     nvarchar(6),
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    cap                    nvarchar(10),
    id_tp_cittadinanza     nvarchar(4)
        constraint FK_Assegni_madre_tp_cittadinanza
            references tp_cittadinanza,
    telefono               nvarchar(15),
    e_mail                 nvarchar(100),
    constraint FK_Assegni_madre_R_Luoghi
        foreign key (id_luogo_nascita, id_provincia_nascita) references R_Luoghi,
    constraint FK_Assegni_madre_R_Luoghi1
        foreign key (id_luogo_residenza, id_provincia_residenza) references R_Luoghi
)
go

create table Assegni_minori
(
    id_domanda             int          not null
        constraint FK_Assegni_minori_Assegni
            references Assegni,
    id_minore              int          not null,
    cognome                nvarchar(50) not null,
    nome                   nvarchar(50) not null,
    data_nascita           datetime,
    id_tp_evento           int          not null
        constraint FK_Assegni_minori_Assegni_tp_eventi
            references Assegni_tp_eventi,
    data_ingresso_famiglia datetime,
    tp_cittadinanza        varchar(10),
    constraint PK_Assegni_minori
        primary key (id_domanda, id_minore)
            with (fillfactor = 95)
)
go

create table Att_socialmente_utili
(
    ID_domanda                     int      not null
        constraint FK_Att_socialmente_utili_Domande
            references Domande,
    data_maturazione_25_contributi datetime not null,
    importo_pensione               float    not null,
    retribuzione_percepita_anno    float    not null
)
go

create table BAR_Richiedente
(
    ID_domanda                   int      not null
        constraint PK_BAR_Richiedente
            primary key
                with (fillfactor = 95)
        constraint FK_BAR_Richiedente_Domande
            references Domande,
    ID_tp_relazione              smallint not null
        constraint FK_BAR_Richiedente_BAR_tp_relazione
            references BAR_tp_relazione,
    richiedente_beneficiario     smallint not null,
    richiedente_non_beneficiario smallint not null
)
go

create table BAR_immobili
(
    ID_domanda       int not null
        constraint FK_BAR_immobili_Domande
            references Domande,
    ID_dichiarazione int not null,
    immobile         int not null
        constraint FK_BAR_immobili_Soggetti
            references Soggetti,
    contatore        int,
    mq               int,
    constraint PK_BAR_immobili_1
        primary key (ID_domanda, ID_dichiarazione, immobile)
            with (fillfactor = 95)
)
go

create table BIM_studenti
(
    ID_domanda           int not null
        constraint PK_BIM_studenti
            primary key
                with (fillfactor = 95)
        constraint FK_BIM_studenti_Domande
            references Domande,
    ID_bim               int not null
        constraint FK_BIM_studenti_BIM
            references BIM,
    uni_sede             nvarchar(100),
    uni_anno_accademico  nvarchar(9),
    uni_tp_iscrizione    int,
    uni_anno             int,
    uni_corso            nvarchar(100),
    uni                  nvarchar(100),
    uni_matricola        nvarchar(15),
    uni_crediti          int,
    scuola_anno          int,
    scuola_istituto      nvarchar(100),
    scuola_sede          nvarchar(100),
    scuola_anno_respinto nvarchar(9),
    corso_situato_BIM    smallint,
    respinto             smallint,
    isUni                smallint,
    isStud               smallint
)
go

create table CANA_immobili
(
    ID_domanda       int not null
        constraint FK_CANA_immobili_Domande
            references Domande,
    ID_dichiarazione int not null,
    immobile         int not null
        constraint FK_CANA_immobili_Soggetti
            references Soggetti,
    contatore        int,
    mq               int,
    constraint PK_CANA_immobili_1
        primary key (ID_domanda, ID_dichiarazione, immobile)
)
go

create table COMP_POLEQ
(
    ID_domanda      int not null
        constraint PK_Comp_poleq
            primary key
                with (fillfactor = 95)
        constraint FK_Comp_poleq_Domande
            references Domande,
    tetto_max       numeric(12, 2),
    data_decorrenza datetime
)
go

create table COMP_POLEQ_quote
(
    ID_domanda       int not null
        constraint FK_Comp_poleq_quote_Domande
            references Domande,
    ID_intervento    int not null,
    ID_tp_intervento nvarchar(15),
    quota            numeric(12, 2),
    constraint PK_Comp_poleq_quote
        primary key (ID_domanda, ID_intervento)
            with (fillfactor = 95)
)
go

create table COMP_POLEQ_quote_storico
(
    ID_domanda       int          not null
        constraint FK_COMP_POLEQ_quote_storico_Domande
            references Domande,
    anno             smallint     not null,
    mese             smallint     not null,
    ID_tp_intervento nvarchar(15) not null,
    quota            numeric(12, 2),
    constraint PK_COMP_POLEQ_quote_storico
        primary key (ID_domanda, anno, mese, ID_tp_intervento)
            with (fillfactor = 95)
)
go

create table COMP_POLEQ_storico
(
    ID_domanda int      not null
        constraint FK_COMP_POLEQ_storico_Domande
            references Domande,
    anno       smallint not null,
    mese       smallint not null,
    tetto_max  numeric(12, 2),
    constraint PK_COMP_POLEQ_storico
        primary key (ID_domanda, anno, mese)
)
go

create table COMP_dati
(
    ID_domanda     int not null
        constraint PK_COMP_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_COMP_Dati_Domande
            references Domande,
    rettaRSA_nores decimal
)
go

create table DU_Dati
(
    ID_domanda                                  int      not null
        constraint PK_DU_Dati
            primary key
        constraint FK_DU_Dati_DU_Dati
            references Domande,
    richiede_anf                                smallint not null,
    richiede_fnum                               smallint not null,
    richiede_contr_sost_consumi                 smallint not null,
    richiede_contr_generico                     smallint not null,
    enti_contr_generico                         nvarchar(200),
    agevolazioni_contr_generico                 nvarchar(200),
    n_marca_bollo                               nvarchar(14),
    data_marca_bollo                            datetime,
    escludi_ufficio_anf                         smallint not null,
    escludi_ufficio_fnum                        smallint not null,
    escludi_ufficio_contr_sost_consumi          smallint not null,
    forzatura_contr_sost_consumi_garanzia       smallint,
    ID_ente_patronato                           int,
    ID_ufficio_patronato                        int,
    ID_ente_caf                                 int,
    ID_ufficio_caf                              int,
    id_domanda_garanzia_forz_contr_sost_consumi int,
    id_domanda_duplicata                        int,
    richiede_prima_inf                          smallint
)
go

create table Dati_famiglia
(
    ID_domanda                     int not null
        constraint PK_dati_famiglia
            primary key
                with (fillfactor = 95)
        constraint FK_Dati_famiglia_Domande
            references Domande,
    impegno_riconoscimento_assegno smallint,
    no_assegno_in_c5               smallint,
    giudiziali                     nvarchar(max),
    non_giudiziali                 nvarchar(max),
    assegno_extra                  decimal(10, 2),
    ID_tp_obbligo_mantenimento     int not null
)
go

create clustered index _dta_index_Domande_c_8_400108566__K3_K5_K2
    on Domande (ID_servizio, ID_soggetto, ID_ente_erogatore)
    with (fillfactor = 95)
go

create index cognome_Domande
    on Domande (cognome)
    with (fillfactor = 95)
go

create index Domande_Domande
    on Domande (ID_servizio, ID_periodo)
    with (fillfactor = 95)
go

create index idx_domande_ente_erog
    on Domande (ID_ente_erogatore) include (ID_domanda)
    with (fillfactor = 95)
go

create index id_soggetto_Domande
    on Domande (ID_soggetto)
    with (fillfactor = 95)
go

create index idx_codice_fiscale
    on Domande (codice_fiscale)
    with (fillfactor = 95)
go

create index IDX_Domande_erogatore_servizi
    on Domande (ID_ente_erogatore, ID_servizio, ID_periodo) include (ID_domanda)
    with (fillfactor = 95)
go

create table EDILIZIA_itea
(
    ID_domanda         int            not null
        constraint PK_EDILIZIA_itea
            primary key
                with (fillfactor = 95)
        constraint FK_ITEA_canoni_Domande
            references Domande,
    canone_oggettivo   decimal(12, 2) not null,
    codice_inquilino   nvarchar(10),
    reddito_imponibile decimal(12, 2) not null,
    reddito_dipendente decimal(12, 2) not null,
    oneri_deducibili   decimal(12, 2) not null,
    sovvenzioni        decimal(12, 2) not null
)
go

create table EDIL_dati
(
    ID_domanda                 int            not null
        constraint PK_EDIL_dati
            primary key
                with (fillfactor = 95)
        constraint FK_EDIL_dati_Domande
            references Domande,
    prefinanziamento           decimal(16, 2) not null,
    data_inizio_lavori         datetime,
    indennizzi_a_minori_fin    decimal(16, 2) not null,
    indennizzi_a_minori_imm    decimal(16, 2) not null,
    mutui_no_det               decimal(16, 2),
    ID_tp_intervento           int,
    prefinanziamento_anno_prec decimal(16, 2),
    pm                         decimal(16, 2),
    pi                         decimal(16, 2),
    pm_anno_prec               decimal(16, 2),
    pi_anno_prec               decimal(16, 2),
    un_genitore                smallint,
    genitori_lavoratori        smallint
)
go

create table EDIL_familiari
(
    ID_domanda             int not null
        constraint FK_EDIL_familiari_Domande
            references Domande,
    ID_dichiarazione       int not null,
    ID_relazione_parentela int not null
        constraint FK_EDIL_familiari_R_Relazioni_parentela
            references R_Relazioni_parentela,
    familiare              int not null,
    pm                     decimal(16, 2),
    constraint PK_EDIL_familiari_1
        primary key (ID_domanda, ID_dichiarazione)
)
go

create table EDIL_familiari_contr
(
    ID_domanda                int not null
        constraint FK_EDIL_familiari_contr_Domande
            references Domande,
    ID_dichiarazione          int not null,
    ID_relazione_parentela    int not null
        constraint FK_EDIL_familiari_contr_R_Relazioni_parentela
            references R_Relazioni_parentela,
    familiare                 int not null,
    studente                  smallint,
    ID_tp_invalidita          smallint,
    spese_invalidita          decimal(14, 2),
    residenza_storica         smallint,
    residenza_storica_data    datetime,
    residenza_storica_nascita smallint,
    non_invalido              smallint,
    nucleo_dal                datetime,
    nucleo_al                 datetime,
    constraint PK_EDIL_familiari_contr
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table EPU_Dati
(
    ID_domanda                     int not null
        constraint PK_EPU_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_EPU_Dati_Domande
            references Domande,
    codice_utente_itea             int not null,
    invalido_75                    smallint,
    redditi                        decimal,
    detrazioni                     decimal,
    patrimoni_immobiliari          decimal,
    patrimoni_mobiliari            decimal,
    gestione_ITEA                  smallint,
    codice_fiscale_intestario_itea nvarchar(20),
    beneficiario_assegno           nvarchar(50),
    importo_assegno                decimal,
    assistito                      nvarchar(50),
    data_decesso                   datetime,
    indirizzo_alloggio             nvarchar(50),
    n_civ_alloggio                 nvarchar(10),
    frazione_alloggio              nvarchar(50),
    ID_provincia_alloggio          nvarchar(2),
    ID_comune_alloggio             nvarchar(6),
    cap_alloggio                   nvarchar(10),
    data_uscita_nucleo             datetime,
    altro                          nvarchar(50),
    no_sublocazione                smallint,
    id_tp_uscita_nucleo            int,
    id_tp_presenza_nucleo          int,
    id_tp_intestatario             int,
    rettaRSA_nores                 decimal,
    ID_domInvalido                 int,
    presenza_condanna              smallint,
    assenza_condanna               smallint,
    presenza_condanna_definitiva   smallint,
    assenza_condanna_definitiva    smallint,
    constraint FK_EPU_Dati_R_luoghi
        foreign key (ID_comune_alloggio, ID_provincia_alloggio) references R_Luoghi
)
go

create table EPU_familiari
(
    ID_domanda             int      not null
        constraint FK_EPU_familiari_Domande
            references Domande,
    ID_dichiarazione       int      not null,
    ID_relazione_parentela int      not null
        constraint FK_EPU_familiari_R_Relazioni_parentela
            references R_Relazioni_parentela,
    ID_tp_partecipazione   char     not null
        constraint FK_EPU_familiari_tp_partecipazione
            references tp_partecipazione,
    familiare              int      not null,
    nuovo_ingresso         smallint not null,
    constraint PK_EPU_familiari
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table EPU_immobili
(
    ID_domanda              int not null
        constraint FK_EPU_immobili_Domande
            references Domande,
    ID_immobile             int not null,
    ID_soggetto             int not null
        constraint FK_EPU_immobili_Soggetti
            references Soggetti,
    ID_comune_catastale     nvarchar(4)
        constraint FK_EPU_immobili_R_comuni_catastali
            references R_comuni_catastali,
    ID_provincia            nvarchar(2)
        constraint FK_EPU_immobili_R_province
            references R_Province,
    ID_luogo                nvarchar(6),
    p_ed                    nvarchar(10),
    pm                      nvarchar(50),
    cat_catastale           nvarchar(3),
    rendita                 decimal(12, 2),
    quota                   decimal(5, 2),
    ID_tp_servizio_igienico int,
    inagibile               smallint,
    data_inagibile          datetime,
    acquisito               smallint,
    data_acquisito          datetime,
    ID_tp_diritto           char(2)
        constraint FK_EPU_immobili_tp_diritti
            references tp_diritti,
    cognome_titolare        nvarchar(50),
    nome_titolare           nvarchar(50),
    comune_ubicazione       nvarchar(50),
    subalterno              nvarchar(10),
    ceduto                  smallint,
    data_ceduto             datetime,
    constraint PK_EPU_immobili
        primary key (ID_domanda, ID_immobile)
            with (fillfactor = 95),
    constraint FK_EPU_immobili_R_luoghi
        foreign key (ID_luogo, ID_provincia) references R_Luoghi
)
go

create table EPU_inv
(
    ID_domanda             int not null
        constraint PK_EPU_inv
            primary key
                with (fillfactor = 95)
        constraint FK_EPU_inv_Domande
            references Domande,
    indirizzo_alloggio     nvarchar(50),
    n_civ_alloggio         nvarchar(20),
    frazione_alloggio      nvarchar(50),
    ID_provincia_alloggio  nvarchar(2),
    ID_comune_alloggio     nvarchar(6),
    cap_alloggio           nvarchar(5),
    provvidenze_invalidi   numeric(10, 2),
    ID_domandaDaVerificare int,
    constraint FK_EPU_inv_R_Luoghi
        foreign key (ID_comune_alloggio, ID_provincia_alloggio) references R_Luoghi
)
go

create table EPU_inv_familiari
(
    ID_domanda       int not null
        constraint FK_Domande_ID_domanda
            references Domande,
    ID_familiare     int not null,
    perc_inv         int,
    data_inv         datetime,
    ID_soggetto      int
        constraint FK_Soggetti_ID_soggetto
            references Soggetti,
    ID_tp_invalidita smallint
        constraint FK_EPU_inv_familiari_tp_invalidita
            references tp_invalidita,
    constraint PK_EPU_inv_familiari
        primary key (ID_domanda, ID_familiare)
            with (fillfactor = 95)
)
go

create table ER_dati
(
    ID_domanda               int not null
        constraint PK_ER_dati
            primary key
                with (fillfactor = 95)
        constraint FK_ER_dati_Domande
            references Domande,
    ID_soggetto_deceduto     int
        constraint FK_ER_dati_Soggetti
            references Soggetti,
    ID_tp_stato_testamento   int
        constraint FK_ER_dati_ER_tp_stati_testamento
            references ER_tp_stati_testamento,
    importo                  numeric(10, 2),
    agenzia_denuncia         nvarchar(200),
    data_denuncia            datetime,
    numero_denuncia          nvarchar(100),
    volume_denuncia          nvarchar(100),
    ID_tp_imposta            int
        constraint FK_ER_dati_ER_tp_imposte
            references ER_tp_imposte,
    matricolas1p             nvarchar(20),
    id_rapportos1p           nvarchar(20),
    note                     nvarchar(500),
    ID_tp_liquidazione_rateo int
        constraint FK_ER_dati_ER_tp_liquidazioni_rateo
            references ER_tp_liquidazioni_rateo,
    inviabile_gea            bit
)
go

create table ER_eredi
(
    ID_domanda                int not null
        constraint FK_ER_eredi_Domande
            references Domande,
    ID_soggetto               int not null
        constraint FK_ER_eredi_Soggetti
            references Soggetti,
    quota                     numeric(5, 2),
    ID_tp_relazione_parentela int
        constraint FK_ER_eredi_ER_tp_relazioni_parentela
            references ER_tp_relazioni_parentela,
    ID_erede                  int,
    incapace                  bit,
    rinuncia                  bit,
    tribunale_autorizzazione  nvarchar(200),
    data_autorizzazione       datetime,
    numero_autorizzazione     nvarchar(100),
    estremi_rinuncia          nvarchar(100),
    constraint PK_ER_eredi
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table ER_prestazioni
(
    ID_domanda        int not null
        constraint FK_ER_prestazioni_Domande
            references Domande,
    ID_tp_prestazione int not null
        constraint FK_ER_prestazioni_ER_tp_prestazioni
            references ER_tp_prestazioni,
    ID_prestazione    int not null,
    constraint PK_ER_prestazioni_1
        primary key (ID_domanda, ID_prestazione)
            with (fillfactor = 95)
)
go

create table Energia
(
    ID_domanda                  int            not null
        constraint PK_Energia
            primary key
                with (fillfactor = 95)
        constraint FK_Energia_Domande
            references Domande,
    ID_tp_fonte                 int            not null
        constraint FK_Energia_Energia_tp_fonti
            references Energia_tp_fonti,
    ID_tp_potenza               nvarchar(10)   not null,
    ID_tp_distributore          nvarchar(10)   not null
        constraint FK_Energia_Energia_tp_distributori
            references Energia_tp_distributori,
    ID_tp_tariffa               int            not null
        constraint FK_Energia_Energia_tp_tariffe
            references Energia_tp_tariffe,
    codice_pod                  nvarchar(50)   not null,
    residente_trento            smallint       not null,
    alt_sup_431                 smallint       not null,
    dati_al_1_1_2008            smallint       not null,
    codice_isee                 nvarchar(50),
    valore_isee                 decimal(14, 2) not null,
    data_isee                   datetime,
    data_scadenza               datetime,
    solo_contributo_provinciale smallint       not null,
    uso_domestico               smallint,
    ID_tp_potenza_disagiato     smallint
)
go

create table Energia_clienti
(
    ID_domanda             int not null
        constraint FK_Energia_clienti_Energia
            references Energia,
    ID_soggetto_cliente    int not null,
    ID_provincia_residenza nvarchar(2),
    ID_comune_residenza    nvarchar(6),
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(50),
    cap_residenza          nvarchar(10),
    telefono_cliente       nvarchar(20),
    fax_cliente            nvarchar(20),
    email_cliente          nvarchar(50),
    altro_recapito_cliente nvarchar(100),
    cliente                int not null,
    constraint PK_Energia_cliente
        primary key (ID_domanda, ID_soggetto_cliente),
    constraint FK_Energia_clienti_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table Energia_familiari
(
    ID_domanda   int      not null
        constraint FK_Energia_familiari_Energia
            references Energia,
    ID_soggetto  int      not null,
    familiare    int      not null,
    ID_tp_nucleo int      not null
        constraint FK_Energia_familiari_Energia_tp_nucleo
            references Energia_tp_nucleo,
    a_carico     smallint not null,
    constraint PK_Energia_familiari
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table FGIO_Dati
(
    ID_domanda     int not null
        constraint PK_FGIO_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_FGIO_Dati_Domande
            references Domande,
    importo_corso1 float,
    importo_corso2 float,
    importo_corso3 float,
    importo_corso4 float,
    ID_tp_studente int
        constraint FK_FGIO_Dati_FGIO_tp_studenti
            references FGIO_tp_studenti
)
go

create table FN_Dati
(
    ID_domanda                int      not null
        constraint PK_FM_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_FN_Dati_Domande
            references Domande,
    concepiti                 smallint,
    data_parto                datetime,
    affidati                  smallint,
    resTAA                    smallint not null,
    residenza_storica_data    datetime,
    residenza_storica_nascita smallint,
    servizio_sociale          nvarchar(100)
)
go

create table FSE_Studenti
(
    ID_domanda             int         not null
        constraint PK_FSE_Studenti
            primary key
                with (fillfactor = 95)
        constraint FK_FSE_Studenti_Domande
            references Domande,
    ID_corso               int         not null
        constraint FK_FSE_Studenti_FSE_R_Corsi
            references FSE_R_Corsi,
    ID_struttura           int         not null
        constraint FK_FSE_Studenti_FSE_R_Strutture
            references FSE_R_Strutture,
    ID_famiglia            int         not null
        constraint FK_FSE_Studenti_FSE_tp_famiglie
            references FSE_tp_famiglie,
    corsoE1                smallint,
    corsoC2                smallint,
    data                   datetime,
    sede                   nvarchar(50),
    indirizzo_domicilio    nvarchar(30),
    cap_domicilio          nvarchar(6),
    ID_comune_domicilio    nvarchar(6) not null,
    ID_provincia_domicilio nvarchar(2) not null,
    telefono_domicilio     nvarchar(16),
    citt_italiana          smallint,
    citt_stato             nvarchar(50),
    citt_comune            nvarchar(50),
    citt_indirizzo         nvarchar(50),
    importo_borsa_prec     float,
    ID_tp_corso            smallint
        constraint FK_FSE_Studenti_FSE_tp_corsi
            references FSE_tp_corsi,
    constraint FK_FSE_Studenti_R_Luoghi
        foreign key (ID_comune_domicilio, ID_provincia_domicilio) references R_Luoghi
)
go

create table Familiari
(
    ID_domanda                int  not null
        constraint FK_Familiari_Domande
            references Domande,
    ID_dichiarazione          int  not null,
    ID_relazione_parentela    int  not null
        constraint FK_Familiari_R_relazioni_parentela
            references R_Relazioni_parentela,
    ID_tp_partecipazione      char not null
        constraint FK_Familiari_tp_partecipazione
            references tp_partecipazione,
    familiare                 int  not null,
    studente                  smallint,
    ID_tp_invalidita          smallint
        constraint FK_Familiari_tp_invalidita
            references tp_invalidita,
    spese_invalidita          decimal(14, 2),
    residenza_storica         smallint,
    residenza_storica_data    datetime,
    residenza_storica_nascita smallint,
    non_invalido              smallint,
    nucleo_dal                datetime,
    nucleo_al                 datetime,
    nucleo_dal_inv            datetime,
    nucleo_al_inv             datetime,
    non_adottato              smallint,
    adottato_data             datetime,
    non_invalido_figlio       smallint,
    constraint PK_Familiari
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table ANF_familiari
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    nucleo_dal       datetime,
    nucleo_al        datetime,
    a_carico_2005    smallint,
    a_carico_2006    smallint,
    nucleo_dal_inv   datetime,
    nucleo_al_inv    datetime,
    non_invalido     smallint,
    familiare        int not null,
    non_adottato     smallint,
    adottato_data    datetime,
    constraint PK_ANF_familiari
        primary key nonclustered (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95),
    constraint IX_ANF_familiari
        unique clustered (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95),
    constraint FK_ANF_familiari_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table AUP_familiari
(
    ID_domanda                         int not null,
    ID_dichiarazione                   int not null,
    ID_servizio                        int not null,
    non_tenuto_lavoro                  int,
    deroga_minore                      int,
    deroga_pensionabile                int,
    deroga_assistente                  int,
    deroga_assistente_ID_soggetto      int,
    deroga_studente                    int,
    deroga_studente_postUni            int,
    deroga_studente_ente               nvarchar(200),
    deroga_serv_civile                 int,
    deroga_serv_civile_ente            nvarchar(200),
    deroga_gestante                    int,
    deroga_gestante_data_parto         datetime,
    deroga_non_collocabile_lavoro      int,
    deroga_non_collocabile_lavoro_ente nvarchar(200),
    deroga_non_collocabile_lavoro_data datetime,
    deroga_categoria                   int,
    tenuto_lavoro                      int,
    perdita_lav_dim_volontarie         int,
    senza_copertPrev                   int,
    con_coperPrev_parziale             int,
    con_coperPrev_totale               int,
    non_disponibile_lavoro             int,
    soc_esclude_comp_qtaA              int,
    soc_no_decurt_comp_qtaA            int,
    residente_qual_3anni               int,
    residente_qual_3anni_in10          int,
    no_residente_qual                  int,
    residente_qual_2annicont_in10      int,
    no_residente_qual_2annicont_in10   int,
    imprenditore_fallimento            int,
    non_disponibile_lavoro_iniziale    int,
    ID_tp_situazione_lavorativa        int,
    data_stato_lavoro                  datetime,
    did_data                           datetime,
    naspi                              int,
    data_naspi                         datetime,
    data_adozione                      datetime,
    residente_qualTN_2annicont_in10    int,
    contratto_inferiore_3_mesi         int,
    esonerato_centro_impiego           int,
    residente_qual_2annicont_in5       int,
    no_residente_qual_2annicont_in5    int,
    constraint PK_AUP_familiari
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95),
    constraint FK_AUP_familiari_AUP_tp_situazioni_lavorative
        foreign key (ID_tp_situazione_lavorativa, ID_servizio) references AUP_tp_situazioni_lavorative,
    constraint FK_AUP_familiari_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table AUP_familiari_attualizzata
(
    ID_domanda                                 int      not null,
    ID_dichiarazione                           int      not null,
    ID_attualizzata                            int      not null,
    data_attualizzata                          datetime not null,
    riduzione_orario                           int,
    cessaz_lav_dip_det                         int,
    sosp_lav_dip_det                           int,
    cessaz_lav_dip_indeter                     int,
    cessaz_lav_atipico                         int,
    cessaz_lav_aut                             int,
    cessaz_ammort                              int,
    lav_imp_mese_prec                          decimal(12, 2),
    lav_imp_due_mesi_prec                      decimal(12, 2),
    lav_datore                                 nvarchar(150),
    lav_indirizzo                              nvarchar(150),
    lavoratore_stagionale                      int,
    lavoratore_stagionale_no_lavoro            int,
    lavoratore_stagionale_no_occupato_pensione int,
    lavoratore_stagionale_percepiti            decimal(12, 2),
    lavoratore_stagionale_percipiendi          decimal(12, 2),
    lav_imp_mese_prec_lav_stag                 decimal(12, 2),
    lav_imp_due_mesi_prec_lav_stag             decimal(12, 2),
    lav_datore_lav_stag                        nvarchar(150),
    lav_indirizzo_lav_stag                     nvarchar(150),
    constraint PK_AUP_familiari_attualizzata
        primary key (ID_domanda, ID_dichiarazione, ID_attualizzata)
            with (fillfactor = 95),
    constraint FK_AUP_familiari_attualizzata_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table AUP_familiari_esclusioni
(
    ID_domanda                  int not null,
    ID_dichiarazione            int not null,
    ID_esclusione               int not null,
    ID_servizio                 int,
    ID_tp_esclusione_componente int,
    escluso_dal                 datetime,
    escluso_al                  datetime,
    constraint PK_AUP_familiari_esclusioni
        primary key (ID_domanda, ID_dichiarazione, ID_esclusione)
            with (fillfactor = 95),
    constraint FK_AUP_familiari_esclusioni_AUP_tp_esclusione_componente
        foreign key (ID_tp_esclusione_componente, ID_servizio) references AUP_tp_esclusione_componente,
    constraint FK_AUP_familiari_esclusioni_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table AUP_familiari_invalidi_figli
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    ID_servizio      int,
    ID_tp_realazione int,
    ID_soggetto      int,
    constraint PK_AUP_familiari_invalidi_figli
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95),
    constraint FK_AUP_familiari_invalidi_figli_AUP_tp_relazioni
        foreign key (ID_tp_realazione, ID_servizio) references AUP_tp_relazioni,
    constraint FK_AUP_familiari_invalidi_figli_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table AUP_familiari_nucleo
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    ID_nucleo        int not null,
    nucleo_dal       datetime,
    nucleo_al        datetime,
    constraint PK_AUP_familiari_nucleo
        primary key (ID_domanda, ID_dichiarazione, ID_nucleo)
            with (fillfactor = 95),
    constraint FK_AUP_familiari_nucleo_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table DU_attualizzazione
(
    ID_domanda                 int      not null,
    ID_dichiarazione           int      not null,
    ID_tp_reddito_attualizzato int      not null,
    mensilita_antecedente      decimal(12, 2),
    mensilita_2antecedente     decimal(12, 2),
    denominazione              nvarchar(150),
    indirizzo                  nvarchar(150),
    familiare                  smallint not null,
    constraint PK_DU_attualizzazione_1
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95),
    constraint FK_DU_attualizzazione_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create index IDX_Familiari_id_domanda
    on Familiari (ID_domanda)
    with (fillfactor = 95)
go

create index IDX_Familiari_id_dichairazione
    on Familiari (ID_dichiarazione)
    with (fillfactor = 95)
go

create index _dta_index_Familiari_7_1177159339__K1_K2_K3
    on Familiari (ID_domanda, ID_dichiarazione, ID_relazione_parentela)
    with (fillfactor = 95)
go

create table Firmatari
(
    ID_domanda             int          not null
        constraint PK_Firmatari
            primary key
                with (fillfactor = 95)
        constraint FK_Firmatari_Domande
            references Domande,
    ID_tp_impedimento      int          not null,
    ID_tp_qualita          int,
    ID_tp_documento        int,
    cognome                nvarchar(35) not null,
    nome                   nvarchar(35) not null,
    numero                 nvarchar(20),
    data_scadenza          datetime,
    rilasciato_da          nvarchar(50),
    data_rilascio          datetime,
    localita               nvarchar(50),
    ID_ente_rilasciatario  nchar(2),
    ID_provincia_documento nvarchar(2),
    ID_luogo_documento     nvarchar(6)
)
go

create table GR_Dati
(
    ID_domanda                            int      not null
        constraint PK_GR_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_GR_Dati_Domande
            references Domande,
    locazione                             smallint,
    canone_data_stipula                   datetime,
    canone_locatario                      nvarchar(50),
    imp_canone_mensile                    int      not null,
    sociale_no                            smallint not null,
    sociale_attestazione                  nvarchar(50),
    sociale_data                          datetime,
    sociale_si                            smallint not null,
    sociale_inizio_mese                   smallint not null,
    sociale_inizio_anno                   smallint not null,
    sociale_durata                        smallint not null,
    esclusa_ufficio                       smallint not null,
    esclusa_motivazione                   nvarchar(200),
    ID_comune_catastale                   nvarchar(4),
    ID_sportello                          smallint,
    pagamento_cassa                       smallint,
    accredito_cc                          smallint,
    imp_int_mutuo_mensile                 int      not null,
    ID_tp_esclusione                      smallint not null
        constraint FK_GR_Dati_GR_tp_esclusione_domanda
            references GR_tp_esclusione_domanda,
    senza_fissa_dimora                    smallint not null,
    ospitato_gratuitamente                smallint not null,
    ID_tp_scelta_incongrua                smallint not null
        constraint FK_GR_Dati_GR_tp_scelta_incongrua
            references GR_tp_scelta_incongrua,
    ID_tp_scelta_sociale_icef             smallint,
    nome_assitente_sociale                nvarchar(100),
    mesi_revoca                           int      not null,
    incongrua_almeno_una_volta            smallint not null,
    attesa_scelta_sociale_icef            smallint not null,
    ID_user_scelta_sociale_icef           int,
    scelta_sociale_data                   datetime,
    scelta_sociale_check_prima            float,
    scelta_sociale_ID_attestazione_insuss int,
    forzatura_scelta_sociale_dopo_3_mesi  smallint not null,
    nucleo_struttura_coperturaParz        smallint not null,
    nucleo_struttura_coperturaParz_decurt int      not null,
    deroga_incompatibilita_automatismo    smallint not null,
    ospitato_gratuitamente_residenza      smallint,
    rinuncia_SPA                          smallint not null,
    id_domanda_spa                        int,
    data_esclusione                       datetime,
    imp_canone_mensile_contributo         int,
    senza_fissa_dimora_presso             nvarchar(50),
    altra_convivenza_anagrafica           int      not null,
    nucleo_struttura_coperturaParz_presso nvarchar(50),
    erogazione_alternativa                smallint not null
)
go

create table GR_Familiari
(
    ID_domanda                          int      not null
        constraint FK_GR_Familiari_Domande
            references Domande
        constraint FK_GR_Familiari_GR_Dati
            references GR_Dati,
    ID_dichiarazione                    int      not null,
    familiare                           smallint not null,
    subentrato                          smallint not null,
    occupato                            smallint not null,
    dimissioni_non_giusta_causa         smallint not null,
    dimissioni_giusta_causa             smallint not null,
    senzaReddito_24m                    smallint,
    rifiuto_ingiustificato              smallint not null,
    non_collocabile_lavoro              smallint not null,
    non_collocabile_lavoro_certificato  nvarchar(100),
    non_collocabile_lavoro_data         datetime,
    disponibilita_lavoro                smallint not null,
    disponibilita_lavoro_data           datetime,
    disponibilita_lavoro_ente           nvarchar(100),
    cercaPrimo_lavoro                   smallint not null,
    cercaPrimo_lavoro_ente              nvarchar(100),
    cercaPrimo_lavoro_ID_soggetto       int,
    cercaPrimo_lavoro_descrizione       nvarchar(200),
    deroga_assistente                   smallint not null,
    deroga_assistente_descrizione       nvarchar(100),
    deroga_assistente_ID_soggetto       int,
    deroga_studente                     smallint not null,
    deroga_studente_ente                nvarchar(100),
    deroga_serv_civile                  smallint not null,
    deroga_serv_civile_ente             nvarchar(100),
    cercaPrimo_lavoro_ecc               smallint not null,
    esclusa_ufficio                     smallint,
    dimissioni_data                     datetime,
    ID_tp_esclusione                    smallint
        constraint FK_GR_Familiari_GR_tp_esclusione_componente
            references GR_tp_esclusione_componente,
    RSA_spese                           decimal(12, 2),
    genitore_altro_figlio               smallint not null,
    condannato                          smallint not null,
    senza_decurtazione                  smallint not null,
    avviabile_lavoro_colloc_mirato      smallint not null,
    ultimoGiorno_contrObblig_data       datetime,
    deroga_disocc_non_accert            smallint not null,
    deroga_gestante                     smallint not null,
    ospitato_da                         datetime,
    ospitato_a                          datetime,
    deroga_gestante_data_presunta_parto datetime,
    ID_tp_attivita_lavorativa           int
        constraint FK_GR_Familiari_GR_tp_attivita_lavorative
            references GR_tp_attivita_lavorative,
    deroga_disponibilita_lavoro         smallint not null,
    constraint PK_Componenti_ext
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95)
)
go

create table GR_Fuoriusciti
(
    ID_domanda  int      not null
        constraint FK_GR_Fuoriusciti_Domande
            references Domande
        constraint FK_GR_Fuoriusciti_GR_Dati
            references GR_Dati,
    ID_soggetto int      not null,
    fuoriuscito smallint not null,
    constraint PK_GR_Fuoriusciti
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table IC_dati
(
    ID_domanda                           int not null
        constraint PK_IC_dati
            primary key
                with (fillfactor = 95)
        constraint FK_IC_dati_Domande
            references Domande,
    trattamenti_inail                    bit,
    trattamenti_guerra                   bit,
    pensione_presso                      nvarchar(100),
    ID_tp_invalidita                     int
        constraint FK_IC_dati_IC_tp_invalidita
            references IC_tp_invalidita,
    percentuale_invalidita               int,
    data_decorrenza_invalidita           datetime,
    genitore_non_convivente              bit,
    ID_soggetto_gen_no_conv              int
        constraint FK_IC_dati_Soggetti
            references Soggetti,
    sottoscritta_in_presenza_gen_no_conv bit,
    sottoscritta_con_doc_gen_no_conv     bit,
    data_decorrenza_economica            datetime,
    elab_anno_competenza                 int,
    data_scadenza_invalidita             datetime,
    elab_azzera_importi                  bit,
    elab_definitivi_calcolo              bit,
    ID_tp_titolare_reddito               int
        constraint FK_IC_dati_IC_tp_titolare_redditi
            references IC_tp_titolare_redditi,
    ID_tp_ricovero_rsa                   int
        constraint FK_IC_dati_IC_tp_ricoveri_rsa
            references IC_tp_ricoveri_rsa,
    ID_tp_istanza_pensione               int
        constraint FK_IC_dati_IC_tp_istanze_pensione
            references IC_tp_istanze_pensione,
    elab_anno_reddito                    int,
    minorazione_da_terzi                 bit,
    residenza_continua                   bit,
    cittadinanza_continua                bit,
    redd_esente_irpef_continua           bit,
    trattamenti_altro                    bit,
    specifica_trattamenti                nvarchar(200),
    tipo_eta_beneficiario                int,
    matricolas1p                         nvarchar(20),
    id_rapportos1p                       nvarchar(20),
    matricola_inps                       nvarchar(200),
    note                                 nvarchar(200),
    data_trasferimento                   datetime,
    continuazione                        int,
    ID_tp_domanda                        int
        constraint FK_IC_dati_IC_tp_domande
            references IC_tp_domande,
    data_requisito_economico             datetime,
    data_ultimo_pagamento_inps           datetime,
    data_sentenza_tribunale              datetime,
    data_rilascio                        datetime,
    data_scadenza                        datetime,
    ID_tp_documento                      int,
    ID_tp_relazione_ue                   int,
    concessa_provvedimento               int,
    decorrenza_iniziale_s1p_2014         nvarchar(200),
    retifica_matricola_s1p_2014          nvarchar(200),
    decorrenza_iniziale_attuale_inps     nvarchar(200),
    retifica_matricola_attuale_inps      nvarchar(200),
    decorrenza_iniziale_definitiva       nvarchar(200),
    retifica_matricola_definitiva        nvarchar(200)
)
go

create index _dta_index_IC_dati_7_1210643556__K1_K5
    on IC_dati (ID_domanda, ID_tp_invalidita)
    with (fillfactor = 95)
go

create index IX_ic_dati_matricola_inps
    on IC_dati (matricola_inps)
    with (fillfactor = 95)
go

create table IC_permessi_soggiorno
(
    ID_domanda            int not null
        constraint FK_IC_permessi_soggiorno_Domande
            references Domande,
    ID_permesso_soggiorno int not null,
    data_rilascio         datetime,
    data_scadenza         datetime,
    da_istruttoria        bit not null,
    note                  nvarchar(200),
    ID_tp_documento       int,
    ID_tp_relazione_ue    int,
    ID_tp_cittadinanza    nvarchar(4),
    permesso_verificato   bit,
    constraint PK_IC_permessi_soggiorno
        primary key (ID_domanda, ID_permesso_soggiorno)
            with (fillfactor = 95)
)
go

create table IC_redditi
(
    ID_domanda                int not null
        constraint FK_IC_redditi_Domande
            references Domande,
    anno_reddito              int not null,
    definitivi                bit not null,
    redd_dip                  numeric(10, 2),
    mobilita                  numeric(10, 2),
    disoccupazione            numeric(10, 2),
    malattia                  numeric(10, 2),
    produttivita              numeric(10, 2),
    redd_autonomo             numeric(10, 2),
    redd_impresa              numeric(10, 2),
    redd_agricoli             numeric(10, 2),
    redd_dominicali           numeric(10, 2),
    redd_fabbricati           numeric(10, 2),
    borse_studio              numeric(10, 2),
    altre_pens                numeric(10, 2),
    redd_esteri               numeric(10, 2),
    redd_altri                numeric(10, 2),
    borse_lavoro              numeric(10, 2),
    redd_tot                  numeric(12, 2),
    ID_tp_presenza_redd_irpef int
        constraint FK_IC_redditi_IC_tp_presenze_redd_irpef
            references IC_tp_presenze_redd_irpef,
    pensioni                  numeric(10, 2),
    indennita_inail           numeric(10, 2),
    data_inserimento          datetime,
    silenzio_assenso          bit,
    constraint PK_IC_redditi
        primary key (ID_domanda, anno_reddito, definitivi)
            with (fillfactor = 95)
)
go

create table IC_ricoveri_rsa
(
    ID_domanda           int not null
        constraint FK_IC_ricoveri_rsa_Domande
            references Domande,
    ID_tp_retta_rsa      int
        constraint FK_IC_ricoveri_rsa_IC_tp_rette_rsa
            references IC_tp_rette_rsa,
    ricoverato_presso    nvarchar(100),
    ricoverato_dal       datetime,
    ricoverato_al        datetime,
    ID_ricovero_rsa      int not null,
    da_istruttoria       bit not null,
    decorrenza_indennita datetime,
    constraint PK_IC_ricoveri_rsa
        primary key (ID_domanda, ID_ricovero_rsa)
            with (fillfactor = 95)
)
go

create table INTMIL_dati
(
    ID_domanda                int not null
        constraint PK_INTMIL_dati
            primary key
                with (fillfactor = 95)
        constraint FK_INTMIL_dati_Domande
            references Domande,
    data_decorrenza_economica datetime,
    ID_tp_stato_coniuge       int
        constraint FK_INTMIL_dati_INTMIL_tp_stati_coniuge
            references INTMIL_tp_stati_coniuge,
    ID_soggetto_coniuge       int
        constraint FK_INTMIL_dati_Soggetti
            references Soggetti,
    ID_domanda_ic             int,
    continuazione             int,
    data_trasferimento        datetime,
    aggravamento_riduzione    int
)
go

create table INTMIL_redditi
(
    ID_domanda          int not null
        constraint FK_INTMIL_redditi_Domande
            references Domande,
    anno_reddito        int not null,
    definitivi          bit not null,
    coniuge             bit not null,
    redd_dip            numeric(10, 2),
    arretrati_dip       numeric(10, 2),
    redd_autonomo       numeric(10, 2),
    pensioni            numeric(10, 2),
    interessi           numeric(10, 2),
    redd_impresa        numeric(10, 2),
    tfr                 numeric(10, 2),
    arretrati_int       numeric(10, 2),
    redd_fabbricati     numeric(10, 2),
    redd_irpef          numeric(10, 2),
    rendite             numeric(10, 2),
    prestazioni         numeric(10, 2),
    redd_tot            numeric(12, 2),
    ID_tp_presenza_redd int
        constraint FK_INTMIL_redditi_INTMIL_tp_presenze_redd
            references INTMIL_tp_presenze_redd,
    data_inserimento    datetime,
    red_gar             numeric(12, 2),
    redd_no_irpef       numeric(10, 2),
    sia_rei             numeric(10, 2),
    pensione_coniuge    numeric(10, 2),
    quota_B3            numeric(10, 2),
    pensioneIC_coniuge  numeric(10, 2),
    constraint PK_INTMIL_redditi
        primary key (ID_domanda, anno_reddito, definitivi, coniuge)
            with (fillfactor = 95)
)
go

create table IS_Dati
(
    ID_Domanda                        int not null
        constraint PK_IS_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_IS_Dati_Domande
            references Domande,
    spese_forniture_utenze_domestiche decimal(12, 2),
    spese_canone_affitto              decimal(12, 2),
    spese_alimentari                  decimal(12, 2),
    spese_condominiali                decimal(12, 2),
    spese_mediche                     decimal(12, 2),
    spese_trasporto                   decimal(12, 2),
    spese_scolastiche                 decimal(12, 2),
    contributo_erogato                decimal(12, 2),
    spese_altre                       decimal(12, 2),
    allega_spesa_sostenuta            smallint,
    allega_spesa_da_sostenere         smallint,
    canone_mensile_lordo              decimal(12, 2),
    canone_contributo_mensile         decimal(12, 2),
    interessi_mutuo_mensile           decimal(12, 2),
    ID_tp_scelta_sociale_icef         smallint
)
go

create table IS_Familiari
(
    ID_Domanda  int not null
        constraint FK_IS_Familiari_Domande
            references Domande,
    ID_soggetto int not null
        constraint FK_IS_Familiari_Soggetti
            references Soggetti,
    familiare   int not null,
    spese_rsa   decimal(12, 2),
    constraint PK_IS_Familiari
        primary key (ID_Domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table IVSDM_Dati
(
    ID_domanda          int          not null
        constraint PK_IVSDM_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_IVSDM_Dati_Domande
            references Domande,
    nomeImpresa         nvarchar(50) not null,
    ID_provincia        nvarchar(2),
    ID_luogo            nvarchar(6),
    Indirizzo           nvarchar(50) not null,
    n_civico            nvarchar(10) not null,
    partitaIva          nvarchar(50),
    codice_fiscale      nvarchar(16),
    e_mail              nvarchar(50),
    dataInizioAnnofin   datetime,
    dataFineAnnofin     datetime,
    ID_tp_settore       int,
    ID_tp_impresa       int          not null
        constraint FK_IVSDM_Dati_IVSDM_tp_impresa
            references IVSDM_tp_impresa,
    impresa_collegata   int          not null,
    campoApplicazione_A int          not null,
    campoApplicazione_B int          not null,
    campoApplicazione_C int          not null
)
go

create table IVSDM_Collegata
(
    ID_domanda     int not null
        constraint FK_IVSDM_Collegata_IVSDM_Dati
            references IVSDM_Dati,
    ID_domanda_IVS int not null,
    constraint PK_IVSDM_collegata
        primary key (ID_domanda, ID_domanda_IVS)
            with (fillfactor = 95)
)
go

create table IVSDM_Deminimis
(
    ID_domanda        int not null
        constraint FK_IVSDM_Deminimis_IVSDM_Dati
            references IVSDM_Dati,
    ID_deminimis      int not null,
    ID_tp_settore     int not null
        constraint FK_IVSDM_Deminimis_IVSDM_tp_settore
            references IVSDM_tp_settore,
    norma_comunitaria nvarchar(250),
    data_norma        datetime,
    ente_comunitario  nvarchar(250),
    importo_concesso  decimal,
    importo_liquidato decimal,
    n_provvedimento   nvarchar(250),
    ID_tp_reg         int,
    nome_impresa      nvarchar(250),
    constraint PK_IVSDM_Deminimis
        primary key (ID_domanda, ID_deminimis)
            with (fillfactor = 95)
)
go

create table IVSDM_ImportiPrecedenti
(
    ID_domanda           int not null
        constraint FK_IVSDM_ImportiPrecedenti_IVSDM_Dati
            references IVSDM_Dati,
    ID_importoPrecedente int not null,
    norma_comunitaria    nvarchar(50),
    data_norma           datetime,
    ente_comunitario     nvarchar(50),
    importo              decimal,
    n_provvedimento      nvarchar(50),
    constraint PK_IVSDM_importoPrec
        primary key (ID_domanda, ID_importoPrecedente)
)
go

create table IVS_Aziende_Agricole
(
    ID_domanda                      int         not null
        constraint PK_IVS_Aziende_Agricole
            primary key
                with (fillfactor = 95)
        constraint FK_IVS_Aziende_Agricole_Domande
            references Domande,
    ID_luogo                        nvarchar(6) not null,
    ID_zona_svantaggiata            smallint    not null,
    denominazione_azienda           nvarchar(50),
    cessione_azienda_nome           nvarchar(35),
    cessione_azienda_cognome        nvarchar(35),
    cessione_azienda_ID_soggetto    int,
    cessione_azienda_data           datetime,
    cessione_azienda_denominazione  nvarchar(50),
    unita_attive_denominazione      nvarchar(50),
    fascia                          int         not null,
    fascia_precedente_da            int,
    fascia_precedente_a             int,
    fascia_precedente_data          datetime,
    vers_1_trim                     float       not null,
    vers_2_trim                     float       not null,
    vers_3_trim                     float       not null,
    vers_4_trim                     float       not null,
    vers_1_trim_data                datetime,
    vers_2_trim_data                datetime,
    vers_3_trim_data                datetime,
    vers_4_trim_data                datetime,
    importo_IVS                     float       not null,
    importo_IVS_233_90              float       not null,
    importo_IVS_160_75              float       not null,
    importo_IVS_arretrati           float       not null,
    importo_IVS_233_90_arretrati    float       not null,
    importo_IVS_160_75_arretrati    float       not null,
    importato                       smallint    not null,
    valutato_apapi                  smallint    not null,
    contributo_calcolato_apapi      float       not null,
    anni_pregressi                  nvarchar(50),
    note_forzatura_contributo_apapi nvarchar(200),
    crediti                         float,
    titolare                        smallint,
    erede                           smallint,
    ID_altitudine                   int,
    ID_servizio                     int,
    ID_tp_agricoltore               int,
    importo_IVS_esonero             float       not null,
    importo_IVS_esonero_arretrati   float       not null,
    vers_4_trim_prec                float       not null,
    vers_4_trim_prec_data           datetime,
    constraint FK_IVS_Aziende_Agricole_IVS_R_zone_svantaggiate
        foreign key (ID_zona_svantaggiata, ID_servizio) references IVS_R_zone_svantaggiate,
    constraint FK_IVS_Aziende_Agricole_IVS_tp_agricoltore
        foreign key (ID_tp_agricoltore, ID_servizio) references IVS_tp_agricoltore,
    constraint FK_IVS_Aziende_Agricole_IVS_tp_altitudini
        foreign key (ID_altitudine, ID_servizio) references IVS_tp_altitudini
)
go

create table IVS_de_minimis
(
    ID_domanda            int           not null
        constraint FK_IVS_DE_MINIMIS_DOMANDE
            references Domande,
    codice_fiscale        nvarchar(16)  not null,
    denominazione         nvarchar(150) not null,
    anno                  int           not null,
    ID_tp_impresa         int           not null,
    data_inizio_esercizio datetime,
    data_fine_esercizio   datetime,
    constraint IVS_de_minimis_PK
        primary key (ID_domanda, codice_fiscale, anno)
)
go

create table IVS_unita
(
    ID_domanda           int not null
        constraint FK_IVS_unita_IVS_Aziende_Agricole
            references IVS_Aziende_Agricole,
    ID_soggetto          int not null
        constraint FK_IVS_unita_IVS_sogg
            references Soggetti,
    iscritto_dal         datetime,
    riduzione65_dal      datetime,
    cancellato_dal       datetime,
    cancellato_al        datetime,
    unita                smallint,
    importata            smallint,
    esonerato_corrente   int,
    esonderato_arretrato int,
    constraint PK_IVS_unita
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table Integrazione_dati_isee
(
    ID_domanda                            int            not null
        constraint PK_Integrazione_dati_isee
            primary key
                with (fillfactor = 95)
        constraint FK_Integrazione_dati_isee_Domande
            references Domande,
    redditi_imposta_sostitutiva           decimal(10, 2) not null,
    redditi_esenti_imposta                decimal(10, 2) not null,
    redditi_esteri                        decimal(10, 2) not null,
    proventi_agrari                       decimal(10, 2) not null,
    redditi_fondiari                      decimal(10, 2) not null,
    partecipazioni_non_quotate            decimal(10, 2) not null,
    quota_capitale_mutuo_residuo          decimal(10, 2) not null,
    quota_capitale_mutui_altri_fabbricati decimal(10, 2) not null
)
go

create table LAV_dati_richiedente
(
    ID_domanda              int      not null
        constraint PK_LAV_dati_richiedente
            primary key
                with (fillfactor = 95)
        constraint FK_LAV_dati_richiedente_Domande
            references Domande,
    residenza_5_anni        smallint not null,
    residenza_10_anni       smallint not null,
    no_residenza            smallint not null,
    no_legge_68_99          smallint not null,
    legge_68_99             smallint not null,
    legge_68_99_perc_inv    decimal(5, 2),
    disoccupato             smallint not null,
    disoccupato_dal         datetime,
    centro_impiego          nvarchar(250),
    lavAut                  smallint not null,
    autoveicolo             smallint not null,
    motoveicolo             smallint not null,
    altro_mezzo             smallint not null,
    nessun_mezzo            smallint not null,
    titolo_studio           nvarchar(150),
    livello_italiano        int      not null,
    caposquadra             smallint not null,
    inidoneo_scau           smallint,
    ID_tp_motivo_esclusione int
)
go

create table MUOVERSI_Beneficiario
(
    ID_domanda             int not null
        constraint PK_MUOVERSI_Beneficiario
            primary key
                with (fillfactor = 95)
        constraint FK_MUOVERSI_Beneficiario_Domande
            references Domande,
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    telefono_residenza     nvarchar(20),
    ID_provincia_residenza nvarchar(2),
    ID_comune_residenza    nvarchar(6),
    cap_residenza          nvarchar(10),
    ID_tp_cittadinanza     nvarchar(4),
    ID_soggetto            int,
    constraint FK_MUOVERSI_beneficiario_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table MUOVERSI_Richiedente
(
    ID_domanda                   int not null
        constraint PK_MUOVERSI_Richiedente
            primary key
                with (fillfactor = 95)
        constraint FK_MUOVERSI_Richiedente_Domande
            references Domande,
    ID_tp_relazione              smallint
        constraint FK_MUOVERSI_Richiedente_MUOVERSI_tp_relazione
            references MUOVERSI_tp_relazione,
    richiedente_beneficiario     smallint,
    richiedente_non_beneficiario smallint,
    km_richiesti                 float,
    indennita_accomp             smallint,
    indennita_accomp_importo     int,
    tessera                      nvarchar(10),
    n_domanda_ammissione         nvarchar(20),
    data_domanda_ammissione      datetime
)
go

create table Nuclei_familiari
(
    ID_domanda             int not null
        constraint FK_Nuclei_familiari_Domande
            references Domande,
    ID_nucleo_familiare    int not null,
    ID_soggetto            int
        constraint FK_Nuclei_familiari_Soggetti
            references Soggetti,
    ID_relazione_parentela int,
    constraint PK_Nuclei_familiari
        primary key (ID_domanda, ID_nucleo_familiare)
            with (fillfactor = 95)
)
go

create table PENSPLAN_difficolta
(
    id_domanda       int      not null
        constraint FK_PENSPLAN_difficolta_Domande
            references Domande,
    id_difficolta    int      not null,
    id_servizio      int      not null,
    id_tp_difficolta int      not null,
    data_dal         datetime not null,
    data_al          datetime not null,
    n_giornate       int,
    constraint PK_PENSPLAN_difficolta
        primary key (id_domanda, id_difficolta)
            with (fillfactor = 95),
    constraint FK_PENSPLAN_difficolta_PENSPLAN_tp_difficolta
        foreign key (id_tp_difficolta, id_servizio) references PENSPLAN_tp_difficolta
)
go

create table PNS_assistiti
(
    ID_domanda            int      not null
        constraint FK_PNS_assistiti_Domande
            references Domande,
    ID_soggetto           int      not null,
    ID_RelazioneParentela int      not null,
    educazAssistenza_dal  datetime,
    educazAssistenza_al   datetime,
    inv_dal               datetime,
    inv_al                datetime,
    invalido_no           smallint,
    curaAss1Trim          smallint,
    curaAss2Trim          smallint,
    curaAss3Trim          smallint,
    curaAss4Trim          smallint,
    assistito             smallint not null,
    constraint PK_PNS_assistiti
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 90)
)
go

create table PNS_assistiti_dati
(
    ID_domanda           int      not null,
    ID_soggetto          int      not null,
    periodo              smallint not null,
    anno                 smallint,
    cognome_nome         nvarchar(100),
    curaAss1Trim         smallint,
    curaAss2Trim         smallint,
    curaAss3Trim         smallint,
    curaAss4Trim         smallint,
    educazAssistenza_dal datetime,
    educazAssistenza_al  datetime,
    inv_dal              datetime,
    inv_al               datetime,
    invalido_no          smallint,
    prova                nvarchar(50),
    constraint PK_PNS_assistiti_dati
        primary key (ID_domanda, ID_soggetto, periodo)
            with (fillfactor = 95),
    constraint FK_PNS_assistiti_dati_PNS_assistiti
        foreign key (ID_domanda, ID_soggetto) references PNS_assistiti
)
go

create table PNS_familiari
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    nucleo_dal       datetime,
    nucleo_al        datetime,
    nucleo_dal_inv   datetime,
    nucleo_al_inv    datetime,
    invalido         smallint,
    invalido_no      smallint,
    familiare        int not null,
    constraint PK_PNS_familiari
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95),
    constraint FK_PNS_familiari_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table PNS_richiedente
(
    ID_domanda          int not null
        constraint PK_PNS_richiedente_
            primary key
                with (fillfactor = 95)
        constraint FK_PNS_richiedente_Domande
            references Domande,
    frazione            nvarchar(50),
    resTAA_5anni        smallint,
    resTAA_1anno        smallint,
    no_previdenza       smallint,
    pensDir             smallint,
    pensDir_data        datetime,
    fondoPens           nvarchar(50),
    no_assReg           smallint,
    no_dom              smallint,
    entePrev            nvarchar(50),
    entePrev_decorrenza datetime,
    entePrev_data       datetime,
    etaPens             smallint,
    etaPens_no          smallint,
    etaPens_dal         datetime,
    anniContr           smallint,
    anniContr_dal       datetime,
    lavStagionale       smallint,
    lavProgetto         smallint,
    copPrev_dal         datetime,
    ID_tp_occupazione   smallint
        constraint FK_PNS_richiedente_PNS_tp_occupazioni
            references PNS_tp_occupazioni,
    ID_contribuente     smallint
        constraint FK_PNS_richiedente_PNS_tp_contribuente
            references PNS_tp_contribuente,
    ID_tp_categoria     smallint
        constraint FK_PNS_richiedente_PNS_tp_categorie
            references PNS_tp_categorie,
    esclusa_ufficio     smallint,
    lavoro_dal_96       smallint,
    centro_impiego      nvarchar(500),
    centro_impiego_data datetime,
    disoccupato         smallint,
    casalinga           smallint
)
go

create table PNS_periodi
(
    ID_domanda     int      not null
        constraint FK_PNS_periodi_PNS_richiedente
            references PNS_richiedente,
    ID_PNS_periodo smallint not null,
    ID_tp_periodo  smallint not null
        constraint FK_PNS_periodi_PNS_tp_periodi
            references PNS_tp_periodi,
    dal            datetime,
    al             datetime,
    giorni         smallint,
    constraint PK_PNS_periodi
        primary key (ID_domanda, ID_PNS_periodo)
            with (fillfactor = 95)
)
go

create table PNS_versamenti
(
    ID_domanda     int      not null
        constraint FK_PNS_versamenti_PNS_richiedente
            references PNS_richiedente,
    ID_versamento  smallint not null,
    ID_riferimento smallint not null
        constraint FK_PNS_versamenti_PNS_tp_riferimenti
            references PNS_tp_riferimenti,
    anno           int      not null,
    importo        float,
    dataVers       datetime,
    scadenza       datetime,
    settimane      char(10),
    mesi           char(10),
    constraint PK_PNS_versamenti
        primary key (ID_domanda, ID_versamento)
            with (fillfactor = 90)
)
go

create table PSO_disoccupati
(
    ID_domanda  int      not null
        constraint FK_PSO_disoccupati_Domande
            references Domande,
    ID_soggetto int      not null,
    disoccupato smallint not null,
    constraint PK_PSO_disoccupati
        primary key (ID_domanda, ID_soggetto)
)
go

create table PSO_lavoratori
(
    ID_domanda  int            not null
        constraint FK_PSO_lavoratori_Domande
            references Domande,
    ID_soggetto int            not null,
    ultima_paga decimal(12, 2) not null,
    lavoratore  smallint       not null,
    constraint PK_PSP_lavoratori
        primary key (ID_domanda, ID_soggetto)
)
go

create table PSO_richiedente
(
    ID_domanda   int            not null
        constraint PK_PSO_richiedente
            primary key
        constraint FK_PSO_richiedente_Domande
            references Domande,
    sp_sanitarie decimal(12, 2) not null,
    sp_legali    decimal(12, 2) not null,
    sp_funerali  decimal(12, 2) not null,
    sp_alloggio  decimal(12, 2) not null,
    sp_scuola    decimal(12, 2) not null,
    sp_pensione  decimal(12, 2) not null,
    sp_neonati   decimal(12, 2) not null,
    canone       decimal(12, 2) not null,
    int_mutuo    decimal(12, 2) not null,
    gravidanza   smallint       not null
)
go

create table REI_dati
(
    ID_domanda                     int      not null
        constraint PK_REI_dati
            primary key
                with (fillfactor = 95)
        constraint FK_REI_dati_Domande
            references Domande,
    b_residenza_2_anni             smallint not null,
    c_auto_dich_var_nucleo         smallint not null,
    c_comp_inf_18                  smallint not null,
    c_pers_disab_con_genit         smallint not null,
    c_gravidanza                   smallint not null,
    c_gravidanza_data              datetime,
    c_disoccupato                  smallint not null,
    d_no_aspi                      smallint not null,
    d_no_lavoro                    smallint not null,
    d_no_lavoro_primaAnnoRedd      smallint not null,
    d_no_lavoro_dopoAnnoRedd       smallint not null,
    d_no_naspi                     smallint not null,
    d_no_autoveicoli               smallint not null,
    d_no_imbarcazioni              smallint not null,
    e_nucleo_3figli_min_stesso_gen int      not null,
    id_esito_controlli_invio_inps  int      not null,
    IBAN                           nvarchar(27),
    progetto                       int,
    progetto_data                  datetime,
    progetto_note                  nvarchar(250),
    ID_domandaINPS                 int,
    progetto_invioINPS             int,
    rei_invioINPS                  int,
    decadenza                      int,
    decadenza_invioINPS            int,
    ID_servizio                    int      not null,
    ID_progetto                    int      not null,
    note                           nvarchar(250),
    ID_decadenza                   int,
    ID_progetto_sott               int      not null,
    cancellazione_invioINPS        int
)
go

create table REI_verifica_requisiti
(
    ID_domanda              int      not null
        constraint PK_REI_verifica_requisiti
            primary key
        constraint FK_REI_verifica_requisiti_REI_dati
            references REI_dati,
    ctrl_cittadinanza       nchar(2) not null,
    ctrl_cittadinanza_desc  nvarchar(250),
    ctrl_residenza          nchar(2) not null,
    ctrl_residenza_desc     nvarchar(250),
    ctrl_docGravidanza      nchar(2) not null,
    ctrl_docGravidanza_desc nvarchar(250)
)
go

create table ROV_assunzioni
(
    ID_domanda               int not null
        constraint PK_ROV_assunzioni
            primary key
                with (fillfactor = 95)
        constraint FK_ROV_assunzioni_Domande
            references Domande,
    id_tp_penale             int
        constraint FK_ROV_assunzioni_ROV_assunzioni_tp_penali
            references ROV_assunzioni_tp_penali,
    id_tp_condanna           int
        constraint FK_ROV_assunzioni_ROV_assunzioni_tp_condanne
            references ROV_assunzioni_tp_condanne,
    id_tp_titolo             int
        constraint FK_ROV_assunzioni_ROV_assunzioni_tp_titoli
            references ROV_assunzioni_tp_titoli,
    giorni_lavorati          int,
    comune_lista             nvarchar(200),
    condanne                 nvarchar(200),
    penali                   nvarchar(200),
    impieghi_entePubb        smallint,
    scuola_obbligo           smallint,
    qualsiasi_assunzione     smallint,
    assunzione_tempo_pieno   smallint,
    assunzione_tempo_parz_am smallint,
    assunzione_tempo_parz_pm smallint,
    serv_comune_rovereto     smallint
)
go

create table RPAF_Dati
(
    id_domanda                       int not null
        constraint PK_rpaf_dich
            primary key
                with (fillfactor = 95)
        constraint FK_Domande_iddomanda
            references Domande,
    titolo_sanitario                 bit,
    corso_formativo                  bit,
    attivita_lavorativa              bit,
    note                             nvarchar(200),
    domicilio                        bit,
    id_comune_domicilio              nvarchar(6),
    id_provincia_domicilio           nvarchar(2),
    domicilio_cap                    nvarchar(10),
    domicilio_frazione               nvarchar(50),
    domicilio_indirizzo              nvarchar(50),
    domicilio_n_civico               nvarchar(10),
    condanne_penali                  bit,
    titolo_studio                    nvarchar(100),
    titolo_studio_rilasciato         nvarchar(100),
    titolo_studio_data               datetime,
    id_provincia_titolo_studio       nvarchar(2),
    id_nazione_titolo_studio         nvarchar(6),
    conoscenza_italiano              bit,
    attestato_frequenza              nvarchar(100),
    attestato_frequenza_rilascio     nvarchar(100),
    attestato_frequenza_data         datetime,
    id_provincia_attestato_frequenza nvarchar(2)
        constraint FK_RPAF_Dati_R_Luoghi2
            references R_Province,
    versamenti_mant                  bit,
    nonVersamenti_mant               bit,
    consenso_recapito_telefonico     bit,
    consenso_disp_territoriale       bit,
    ID_tp_istituto_titolo            int
        constraint FK_RPAF_Dati_RPAF_tp_istituti1
            references RPAF_tp_istituti,
    ID_tp_lingua_titolo              int
        constraint FK_RPAF_Dati_RPAF_tp_lingue
            references RPAF_tp_lingue,
    ID_tp_istituto_corso             int
        constraint FK_RPAF_Dati_RPAF_tp_istituti
            references RPAF_tp_istituti,
    permesso_soggiorno               bit,
    permesso_soggiorno_numero        nvarchar(50),
    permesso_questura                nvarchar(50),
    permesso_data                    datetime,
    permesso_scadenza                datetime,
    marca_bollo_numero               nvarchar(50),
    marca_bollo_data                 datetime,
    ID_tp_documento_permesso         int
        constraint FK_RPAF_Dati_RPAF_tp_documenti
            references RPAF_tp_documenti,
    non_disponibile                  bit,
    patente                          bit,
    constraint FK_RPAF_Dati_R_Luoghi
        foreign key (id_comune_domicilio, id_provincia_domicilio) references R_Luoghi,
    constraint FK_RPAF_Dati_R_Luoghi1
        foreign key (id_nazione_titolo_studio, id_provincia_titolo_studio) references R_Luoghi
)
go

create table RPAF_Comunita
(
    id_domanda     int not null
        constraint FK_RPAF_Comunita_RPAF_Dati_idDomanda
            references RPAF_Dati,
    id_tp_comunita int not null
        constraint FK_RPAF_Comunita_REnti
            references R_Enti,
    constraint PK_RPAF_Comunita
        primary key (id_domanda, id_tp_comunita)
            with (fillfactor = 95)
)
go

create table RPAF_Orari
(
    ID_domanda   int not null
        constraint FK_RPAF_Orari_RPAF_Dati_idDomanda
            references RPAF_Dati,
    ID_tp_orario int not null
        constraint FK_RPAF_Orari_RPAF_tp_orari
            references RPAF_tp_orari,
    constraint PK_RPAF_Orari
        primary key (ID_domanda, ID_tp_orario)
            with (fillfactor = 95)
)
go

create table RPAF_disponibilita
(
    id_domanda          int not null
        constraint FK_RPAF_disponibilita_RPAF_Dati_idDomanda
            references RPAF_Dati,
    id_tp_disponibilita int not null
        constraint FK_RPAF_disponibilita_RPAF_tp_disponibilita
            references RPAF_tp_disponibilita,
    constraint PK_rpaf_disponibilita
        primary key (id_domanda, id_tp_disponibilita)
            with (fillfactor = 95)
)
go

create table Redditi_agricoli
(
    ID_dichiarazione int            not null
        constraint FK_Redditi_agricoli_Dich_icef
            references Dich_icef,
    reddito_agricolo smallint       not null,
    ID_tp_agricolo   nvarchar(50)   not null,
    ID_zona_agricola int            not null,
    quantita         decimal(8, 2)  not null,
    costo_locazione  decimal(12, 2) not null,
    costo_dipendenti decimal(12, 2) not null,
    denominazione    nvarchar(50),
    quota            decimal(5, 2),
    ID_dich          int            not null,
    constraint PK_Reddito_agricolo
        primary key (ID_dichiarazione, reddito_agricolo)
            with (fillfactor = 95),
    constraint FK_Redditi_agricoli_R_Zone_agricole
        foreign key (ID_dich, ID_zona_agricola) references R_Zone_agricole,
    constraint FK_Redditi_agricoli_tp_agricolo
        foreign key (ID_dich, ID_tp_agricolo) references tp_agricolo
)
go

create table Redditi_altri
(
    ID_dichiarazione int            not null
        constraint FK_Redditi_altri_Dich_icef
            references Dich_icef,
    entrata          smallint       not null,
    ID_tp_entrata    char(3)        not null,
    importo          decimal(12, 2) not null,
    ID_tp_erogazione char(3),
    note             nvarchar(50),
    importo_sentenza decimal(12, 2),
    constraint PK_altre_entrate
        primary key (ID_dichiarazione, entrata)
            with (fillfactor = 95)
)
go

create table Redditi_dipendente
(
    ID_dichiarazione int            not null
        constraint FK_Redditi_dipendente_Dich_icef
            references Dich_icef,
    reddito          smallint       not null,
    ID_tp_reddito    char(3)        not null,
    importo          decimal(12, 2) not null,
    ID_dich          int            not null,
    constraint PK_Redditi
        primary key (ID_dichiarazione, reddito)
            with (fillfactor = 95),
    constraint FK_Redditi_dipendente_tp_redditi
        foreign key (ID_dich, ID_tp_reddito) references tp_redditi
)
go

create table SAN_Dati
(
    ID_domanda                int         not null
        constraint PK_SAN_Dati
            primary key
                with (fillfactor = 95)
        constraint FK_SAN_Dati_Domande
            references Domande,
    ID_luogo_cittadinanza     nvarchar(6) not null,
    ID_provincia_cittadinanza nvarchar(2) not null,
    cittadinanza_italiana     smallint,
    constraint FK_SAN_dati_R_Luoghi
        foreign key (ID_luogo_cittadinanza, ID_provincia_cittadinanza) references R_Luoghi
)
go

create table SAN_Studenti
(
    ID_domanda                 int         not null
        constraint PK_SAN_Studenti
            primary key
                with (fillfactor = 95)
        constraint FK_SAN_Studenti_Domande
            references Domande,
    ID_corso                   int         not null,
    ID_sede_corso              nvarchar(6) not null,
    residenza_tn_dal           datetime,
    anno_prima_imm             int,
    uni_prima_imm              nvarchar(30),
    ID_tp_iscrizione           int
        constraint FK_SAN_Studenti_SAN_tp_iscrizione
            references SAN_tp_iscrizione,
    ID_anno_accademico         int         not null,
    anno_corso                 int         not null,
    matricola                  nvarchar(15),
    importo_borsa_prec         float       not null,
    num_parenti_uni            int         not null,
    ID_provincia_domicilio     nvarchar(2) not null,
    ID_comune_domicilio        nvarchar(6) not null,
    cap_domicilio              nvarchar(6),
    indirizzo_domicilio        nvarchar(30),
    telefono_domicilio         nvarchar(16),
    altra_sede                 nvarchar(50),
    laurea_triennale           smallint,
    laurea_specialistica       smallint,
    affitto_data               datetime,
    affitto_num                nvarchar(10),
    affitto_serie              nvarchar(10),
    affitto_agenzia_entrate    nvarchar(50),
    affitto_ente_convenzionato nvarchar(50),
    fuori_sede                 smallint,
    frequenza_corsi            smallint,
    affitto_dal                datetime,
    affitto_al                 datetime,
    ID_tp_corso                smallint,
    crediti                    int,
    bonus                      int,
    bonus_max                  int,
    erasmus                    smallint,
    erasmus_mesi               int,
    sospensione_studi          smallint,
    citt_italiana              smallint,
    citt_stato                 nvarchar(50),
    citt_comune                nvarchar(50),
    citt_indirizzo             nvarchar(50),
    ID_tp_nucleo               smallint
        constraint FK_SAN_Studenti_SAN_tp_nucleo
            references SAN_tp_nucleo,
    PI_estero                  int,
    RES_estero                 int,
    constraint FK_SAN_Studenti_SAN_R_Corsi
        foreign key (ID_corso, ID_anno_accademico) references SAN_R_Corsi,
    constraint FK_SAN_Studenti_SAN_R_Sedi
        foreign key (ID_anno_accademico, ID_sede_corso) references SAN_R_Sedi,
    constraint FK_SAN_Studenti_SAN_tp_corso
        foreign key (ID_tp_corso, ID_anno_accademico) references SAN_tp_corso
)
go

create table SCUOLE_Studenti
(
    ID_domanda                    int      not null
        constraint PK_SCUOLE_Studenti
            primary key
                with (fillfactor = 95)
        constraint FK_SCUOLE_Studenti_Domande
            references Domande
        constraint FK_SCUOLE_Studenti_SCUOLE_Studenti
            references SCUOLE_Studenti,
    ID_tp_classe                  smallint
        constraint FK_SCUOLE_Studenti_SCUOLE_tp_classi
            references SCUOLE_tp_classi,
    ID_scuola                     int,
    ID_corso                      int,
    sostegno_retta_frequenza      decimal(10, 2),
    ente_sostegno_retta_frequenza nvarchar(100),
    retta                         decimal(10, 2),
    media_voti                    float,
    trasporti                     decimal(10, 2),
    mensa                         decimal(10, 2),
    tassa                         decimal(10, 2),
    libri                         decimal(10, 2),
    convitto_alloggio             decimal(10, 2),
    scuola                        nvarchar(50),
    corso_studio                  nvarchar(50),
    indirizzo_residenza_stud      nvarchar(200),
    assegno_anticipo              smallint not null,
    assegno_unico                 smallint not null,
    constraint FK_SCUOLE_Studenti_SCUOLE_R_Corsi
        foreign key (ID_corso, ID_scuola) references SCUOLE_R_Corsi
)
go

create table SPORT_Studente
(
    ID_domanda              int      not null
        constraint PK_SPORT_richiedente
            primary key
                with (fillfactor = 95)
        constraint FK_SPORT_Studente_Domande
            references Domande,
    ID_soggetto             int      not null,
    conv_naz_assoluta       smallint not null,
    conv_naz_categoria      smallint not null,
    ID_tp_classifica        smallint not null
        constraint FK_SPORT_Studente_SPORT_tp_classifica
            references SPORT_tp_classifica,
    part_assoluta           smallint not null,
    part_categoria          smallint not null,
    classe                  int,
    scuola                  nvarchar(50),
    corso                   nvarchar(50),
    residenzaTAA            smallint,
    merito_scolastico       smallint,
    tesserato               smallint,
    associazione            nvarchar(50),
    associazione_sede       nvarchar(50),
    associazioe_federazione nvarchar(50),
    ID_tp_disciplina        smallint,
    altra_disciplina        nvarchar(100),
    ID_tp_sottoscrittore    smallint,
    ID_tp_rappresentativa   int
        constraint FK_SPORT_Studente_SPORT_tp_rappresentativa
            references SPORT_tp_rappresentativa,
    reddito_richiedente     smallint,
    genitore                smallint
)
go

create table STUD_Familiari
(
    ID_domanda         int      not null,
    ID_dichiarazione   int      not null,
    studente_esonerato smallint not null,
    familiare          int      not null,
    constraint PK_STUD_Familiari
        primary key (ID_domanda, ID_dichiarazione)
            with (fillfactor = 95),
    constraint FK_STUD_Familiari_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table STUD_Studenti
(
    ID_domanda                      int      not null
        constraint FK_STUD_Studenti_Domande
            references Domande,
    ID_soggetto                     int      not null
        constraint FK_STUD_Studenti_Soggetti
            references Soggetti,
    ID_tp_scuola                    smallint not null
        constraint FK_STUD_Studenti_STUD_tp_scuola
            references STUD_tp_scuola,
    nome_scuola                     nvarchar(255),
    studente                        smallint not null,
    ID_scuola                       smallint,
    ID_luogo                        nvarchar(6)
        constraint FK_STUD_Studenti_R_Luoghi
            references R_Luoghi (ID_luogo),
    ID_validazione_esito            int      not null
        constraint FK_STUD_Studenti_STUD_tp_validazione_esito
            references STUD_tp_validazione_esito,
    ID_validazione_esito_aggiornato int,
    constraint PK_STUD_Studenti
        primary key (ID_domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table STUD_Studenti_Variazioni
(
    ID_STUD_Studenti_Variazioni bigint identity
        constraint PK_STUD_Studenti_Variazioni_1
            primary key
                with (fillfactor = 95),
    ID_domanda                  int      not null
        constraint FK_STUD_Studenti_Variazioni_Domande
            references Domande,
    ID_soggetto                 int      not null
        constraint FK_STUD_Studenti_Variazioni_Soggetti
            references Soggetti,
    ID_tp_scuola                smallint not null
        constraint FK_STUD_Studenti_Variazioni_STUD_tp_scuola
            references STUD_tp_scuola,
    nome_scuola                 nvarchar(255),
    studente                    smallint not null,
    ID_scuola                   smallint,
    ID_luogo                    nvarchar(6)
        constraint FK_STUD_Studenti_Variazioni_R_Luoghi
            references R_Luoghi (ID_luogo),
    id_validazione_esito        int      not null,
    infotn_sistemaRicevimento   nvarchar(255),
    infotn_annoScolastico       nvarchar(255),
    infotn_id_studente          nvarchar(255),
    infotn_nome                 nvarchar(255),
    infotn_cognome              nvarchar(255),
    infotn_codiceFiscale        nvarchar(255),
    infotn_dataRiferimento      datetime,
    infotn_idIscrizione         nvarchar(255),
    infotn_idScuola             nvarchar(255),
    infotn_dataInizioIscrizione datetime,
    infotn_dataFineIscrizione   datetime,
    infotn_dataOraVariazione    datetime,
    infotn_tipoVariazione       nvarchar(255),
    infotn_motivoInterruzione   nvarchar(255),
    processato                  int,
    data_inserimento            datetime,
    data_modifica               datetime,
    ID_scuola_NEW               smallint,
    ID_tp_scuola_NEW            smallint
)
go

create table STUD_altri_genitori
(
    ID_domanda             int not null
        constraint PK_STUD_altri_genitori
            primary key
                with (fillfactor = 95)
        constraint FK_STUD_altri_genitori_Domande
            references Domande,
    cognome                nvarchar(35),
    nome                   nvarchar(35),
    data_nascita           datetime,
    ID_provincia_residenza nvarchar(2),
    ID_comune_residenza    nvarchar(6),
    indirizzo_residenza    nvarchar(50),
    n_civ_residenza        nvarchar(10),
    cap_residenza          nvarchar(10),
    irreperibile           smallint,
    constraint FK_STUD_altri_genitori_R_Luoghi
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table Sottoscrittori
(
    ID_dichiarazione            int          not null
        constraint PK_sottoscrittore
            primary key
                with (fillfactor = 95)
        constraint FK_Sottoscrittori_Doc
            references Doc,
    ID_sottoscrittore           int,
    ID_soggetto                 int          not null
        constraint FK_Sottoscrittori_Soggetti
            references Soggetti,
    ID_tp_motivo_sottoscrizione nvarchar(8)  not null
        constraint FK_Sottoscrittori_tp_motivo_sottoscrizione
            references tp_motivo_sottoscrizione
            on update cascade on delete cascade,
    cognome                     nvarchar(35),
    nome                        nvarchar(35),
    codice_fiscale              nvarchar(16),
    ID_tp_sex                   nvarchar
        constraint FK_Sottoscrittori_tp_sex
            references tp_sex,
    ID_provincia_nascita        nvarchar(2),
    ID_luogo_nascita            nvarchar(6),
    data_nascita                datetime,
    ID_provincia_residenza      nvarchar(2)  not null,
    ID_comune_residenza         nvarchar(6)  not null,
    indirizzo_residenza         nvarchar(50) not null,
    n_civ_residenza             nvarchar(10) not null,
    cap_residenza               nvarchar(10) not null,
    telefono_residenza          nvarchar(20),
    constraint FK_Sottoscrittori_R_Luoghi
        foreign key (ID_luogo_nascita, ID_provincia_nascita) references R_Luoghi,
    constraint FK_Sottoscrittori_R_Luoghi1
        foreign key (ID_comune_residenza, ID_provincia_residenza) references R_Luoghi
)
go

create table TN_Studenti
(
    ID_domanda              int            not null
        constraint PK_TN_Studenti
            primary key
                with (fillfactor = 95)
        constraint FK_TN_Studenti_Domande
            references Domande,
    estero_laurea           smallint       not null,
    estero_specializzazione smallint       not null,
    italia_specializzazione smallint       not null,
    laurea                  nvarchar(50),
    universita              nvarchar(50),
    data_laurea             datetime,
    n_esercitazioni         decimal(12, 2) not null,
    n_esami                 decimal(12, 2) not null,
    citt_UE                 smallint       not null,
    residenza_tn_dal        datetime,
    anno_prima_imm          int,
    uni_prima_imm           nvarchar(30),
    ID_tp_iscrizione        smallint
        constraint FK_TN_Studenti_TN_tp_iscrizione
            references TN_tp_iscrizione,
    ID_anno_accademico      int            not null,
    anno_corso              int            not null,
    facolta                 nvarchar(50),
    corso_laurea            nvarchar(50),
    matricola               nvarchar(15),
    durata_laurea           int,
    percent_invalido        int            not null,
    importo_borsa_prec      float          not null,
    ID_provincia_domicilio  nvarchar(2)    not null,
    ID_comune_domicilio     nvarchar(6)    not null,
    cap_domicilio           nvarchar(6),
    indirizzo_domicilio     nvarchar(30),
    telefono_domicilio      nvarchar(16),
    num_parenti_uni         int            not null,
    acconsente              smallint,
    non_acconsente          smallint,
    fabbricati_estero       decimal(10),
    constraint FK_TN_Studenti_R_Luoghi
        foreign key (ID_comune_domicilio, ID_provincia_domicilio) references R_Luoghi
)
go

create table TRASP_Domande
(
    ID_domanda  int            not null
        constraint PK_TRASP_Domande
            primary key
                with (fillfactor = 95)
        constraint FK_TRASP_Domande_Domande
            references Domande,
    isee        decimal(12, 2) not null,
    n_figli     smallint       not null,
    solo_scuola smallint       not null,
    n_handicap  smallint       not null
)
go

create table UNI_Domanda
(
    ID_domanda                   int not null
        constraint PK_UNI_Domanda
            primary key
                with (fillfactor = 95)
        constraint FK_UNI_Domanda_Domande
            references Domande,
    reddito_richiedente          smallint,
    residenza_esterna            smallint,
    orfano                       smallint,
    chiede_borsa                 smallint,
    chiede_alloggio              smallint,
    ID_scuola                    nvarchar(100),
    ID_corso                     nvarchar(100),
    part_time                    smallint,
    anno_prima_imm               int,
    matricola                    nvarchar(100),
    crediti                      int,
    bonus                        int,
    maturita_anno                int,
    maturita_voto                int,
    ID_tp_voto_base_maturita     int
        constraint FK_UNI_Domanda_UNI_tp_voto_base_maturita
            references UNI_tp_voto_base_maturita,
    banca_nome                   nvarchar(100),
    banca_cointestatario         nvarchar(100),
    banca_filiale                nvarchar(100),
    banca_iban                   nvarchar(27),
    ID_tp_documento_identita     nvarchar(2)
        constraint FK_UNI_Domanda_UNI_tp_documento_identita
            references UNI_tp_documento_identita,
    documento_numero             nvarchar(100),
    documento_autorita           nvarchar(100),
    documento_data_rilascio      datetime,
    possesso_requisiti_bando     smallint,
    anno_imm_corso               int,
    anno_imm_su                  int,
    ID_anno_corso                int,
    ID_tp_corso                  nchar(10),
    ID_sede_corso                nchar(10),
    ID_sede                      int,
    ID_servizio                  int,
    residente_data               datetime,
    borsa_anno_prec_CUD          decimal(12, 2),
    ID_politica_opera            int,
    riconferma                   bit,
    protocollo_attestazione_inps nvarchar(27),
    paritario                    bit,
    nr_edizione                  int,
    json_richiesta               nvarchar(max),
    json_risposta                nvarchar(max),
    constraint FK_UNI_Domanda_UNI_R_Scuole
        foreign key (ID_servizio, ID_scuola) references UNI_R_Scuole,
    constraint FK_UNI_Domanda_UNI_anni_corso
        foreign key (ID_servizio, ID_anno_corso) references UNI_anni_corso,
    constraint FK_UNI_Domanda_UNI_sedi
        foreign key (ID_servizio, ID_sede) references UNI_sedi,
    constraint FK_UNI_Domanda_UNI_tp_corsi
        foreign key (ID_tp_corso, ID_servizio) references UNI_tp_corsi,
    constraint FK_UNI_Domanda_UNI_tp_politica_opera
        foreign key (ID_politica_opera, ID_servizio) references UNI_tp_politica_opera,
    constraint FK_UNI_Domanda_UNI_tp_sedi
        foreign key (ID_servizio, ID_sede_corso) references UNI_tp_sedi
)
go

create table UNI_Domanda_confronto
(
    ID_domanda          int not null
        constraint PK_UNI_Domanda_confronto
            primary key
                with (fillfactor = 95)
        constraint FK_UNI_Domanda_confronto_UNI_Domanda
            references UNI_Domanda,
    ID_servizio         int not null,
    json_richiesta      nvarchar(max),
    json_risposta       nvarchar(max),
    json_risposta_nuova nvarchar(max),
    differenze          bit
)
go

create table UNI_Icef_parificato
(
    ID_domanda   int            not null
        constraint PK_UNI_Icef_paritario
            primary key
                with (fillfactor = 95)
        constraint FK_UNI_Icef_paritario_Domande
            references Domande,
    redditi      numeric(12, 2) not null,
    imposte      numeric(12, 2) not null,
    PI           numeric(12, 2) not null,
    PM           numeric(12, 2) not null,
    n_componenti int            not null
)
go

create table UNI_Isee_parificato
(
    ID_domanda         int not null
        constraint PK_UNI_isee_paritario
            primary key
        constraint FK_UNI_Isee_parificato_Domande
            references Domande,
    ISEE               decimal(12, 2),
    ISP                decimal(12, 2),
    VSE                decimal(12, 2),
    data_presentazione datetime,
    data_validita      datetime
)
go

create table UNI_Studenti
(
    ID_domanda                  int         not null
        constraint PK_UNI_Studenti
            primary key
                with (fillfactor = 95)
        constraint FK_UNI_Studenti_Domande
            references Domande,
    ID_universita               nvarchar(6),
    ID_facolta                  nvarchar(6),
    ID_corso_laurea             nvarchar(6),
    matricola                   nvarchar(15),
    ID_tp_iscrizione            int
        constraint FK_UNI_Studenti_UNI_tp_iscrizione
            references UNI_tp_iscrizione,
    ID_anno_accademico          int         not null
        constraint FK_UNI_Studenti_UNI_R_Anno_accademico
            references UNI_R_Anni_accademici,
    anno_corso                  int         not null,
    anno_prima_imm              int,
    uni_prima_imm               nvarchar(30),
    annualita_sostenute         decimal(9, 2),
    crediti                     int,
    bonus                       int,
    bonus_max                   int,
    non_prende_alloggio_in_s    smallint    not null,
    percent_invalido            int         not null,
    indirizzo_domicilio         nvarchar(30),
    cap_domicilio               nvarchar(6),
    ID_comune_domicilio         nvarchar(6) not null,
    ID_provincia_domicilio      nvarchar(2) not null,
    telefono_domicilio          nvarchar(16),
    domanda_borsa               int,
    domanda_esenzione           int,
    domanda_alloggio            int,
    ID_tp_alloggio              int
        constraint FK_UNI_Studenti_UNI_tp_alloggio
            references UNI_tp_alloggio,
    ID_tp_documento             nvarchar(2) not null
        constraint FK_UNI_Studenti_UNI_tp_documenti
            references UNI_tp_documenti,
    numero_documento            nvarchar(30),
    data_rilascio_documento     datetime,
    autorita_rilascio_documento nvarchar(30),
    citt_italiana               smallint    not null,
    citt_stato                  nvarchar(30),
    citt_comune                 nvarchar(30),
    citt_indirizzo              nvarchar(30),
    ID_tp_progetto              int
        constraint FK_UNI_Studenti_UNI_tp_progetti
            references UNI_tp_progetti,
    beneficiato_sospensione     smallint    not null,
    merito_ridotto              smallint    not null,
    trasferito                  smallint,
    trasferito_da_ateneo        nvarchar(30),
    trasferito_data_pre         datetime,
    importo_borsa_prec          float       not null,
    num_parenti_uni             int         not null,
    affitto_data                datetime,
    affitto_num                 nvarchar(10),
    affitto_serie               nvarchar(10),
    affitto_agenzia_entrate     nvarchar(50),
    fuorisede_a_privato         smallint,
    fuorisede_a_opera           smallint,
    escluso_ufficio             smallint,
    merito_minimo               smallint,
    merito_minimo_motivo        nvarchar(50),
    icef_anno_prec              float,
    ID_tp_nucleo                smallint
        constraint FK_UNI_Studenti_UNI_tp_nucleo
            references UNI_tp_nucleo,
    numero_domicilio            nvarchar(30),
    ID_tp_cittadinanza          nvarchar(4)
        constraint FK_UNI_Studenti_tp_cittadinanza
            references tp_cittadinanza,
    forza_borsa_fuori_sede      smallint,
    forza_borsa_in_sede         smallint,
    constraint FK_UNI_Studenti_R_Luoghi
        foreign key (ID_comune_domicilio, ID_provincia_domicilio) references R_Luoghi,
    constraint FK_UNI_Studenti_UNI_R_Corsi_laurea
        foreign key (ID_universita, ID_facolta, ID_corso_laurea, ID_anno_accademico) references UNI_R_Corsi_laurea,
    constraint FK_UNI_Studenti_UNI_R_Facolta
        foreign key (ID_universita, ID_facolta, ID_anno_accademico) references UNI_R_Facolta
)
go

create table VEICOLI_dati
(
    ID_domanda                int not null
        constraint PK_VEICOLI_dati
            primary key
                with (fillfactor = 95)
        constraint FK_VEICOLI_dati_Domande
            references Domande,
    ID_servizio               int
        constraint FK_VEICOLI_dati_R_Servizi
            references R_Servizi,
    ID_tp_tipo                int,
    ID_tp_uso                 int,
    ID_tp_destinatario        int,
    ID_tp_destinatario_agr    int,
    ID_tp_iscrizione          int,
    ID_tp_intervento          int,
    marca                     nvarchar(30),
    targa                     nvarchar(10),
    telaio                    nvarchar(20),
    data_rilascio_carta_circ  datetime,
    rilascio_carta_circ       nvarchar(50),
    doAttivitaAgr             nchar(10),
    n_iscrizione              nvarchar(50),
    data_iscrizione           datetime,
    pa                        nvarchar(50),
    no_contr_stesso_vcl       smallint,
    no_contr_vcl              smallint,
    no_contr_vcl_5anni        smallint,
    aggiornamento_dispositivi smallint,
    aggravamento_fisico       smallint,
    distruzione_veicolo       smallint,
    all_doc_veicolo           smallint,
    all_ordine_veicolo        smallint,
    all_doc_conducente        smallint,
    all_cert_medica           smallint,
    all_dich_consenso         smallint,
    residenza                 smallint,
    data_trasmissione         datetime,
    spesa_ammissibile         decimal(10, 2),
    constraint FK_VEICOLI_dati_VEICOLI_tp_destinatari
        foreign key (ID_servizio, ID_tp_destinatario) references VEICOLI_tp_destinatari,
    constraint FK_VEICOLI_dati_VEICOLI_tp_destinatari1
        foreign key (ID_servizio, ID_tp_destinatario_agr) references VEICOLI_tp_destinatari,
    constraint FK_VEICOLI_dati_VEICOLI_tp_interventi
        foreign key (ID_servizio, ID_tp_intervento) references VEICOLI_tp_interventi,
    constraint FK_VEICOLI_dati_VEICOLI_tp_iscrizione
        foreign key (ID_servizio, ID_tp_iscrizione) references VEICOLI_tp_iscrizioni,
    constraint FK_VEICOLI_dati_VEICOLI_tp_tipi
        foreign key (ID_servizio, ID_tp_tipo) references VEICOLI_tp_tipi,
    constraint FK_VEICOLI_dati_VEICOLI_tp_usi
        foreign key (ID_servizio, ID_tp_uso) references VEICOLI_tp_usi
)
go

create table VLAV_Dati
(
    ID_Domanda                    int not null
        constraint [FK_VLAV_Dati _Domande]
            references Domande,
    ID_soggetto                   int not null
        constraint [FK_VLAV_Dati _Soggetti]
            references Soggetti,
    ID_comune_residenza_incidente nvarchar(6)
        constraint FK_VLAV_Dati_residenza_incidente
            references R_Luoghi (ID_luogo),
    data_morte                    datetime,
    luogo_incidente               nvarchar(200),
    data_incidente                datetime,
    attestazione_incidente        nvarchar(200),
    attiv_lavorativa_volontariato smallint,
    attiv_amministratore_pubb     smallint,
    patrimonio_immobiliare        decimal(12, 2),
    patrimonio_finanziario        decimal(12, 2),
    rendite                       decimal(12, 2),
    altre_erogazioni              decimal(12, 2),
    constraint PK_VLAV_Dati
        primary key (ID_Domanda, ID_soggetto)
            with (fillfactor = 95)
)
go

create table domande_isee159
(
    id_domanda                int            not null
        constraint PK_domande_isee159
            primary key
                with (fillfactor = 95)
        constraint fk_isee_domande_domande
            references Domande,
    protocollo                nvarchar(27),
    id_tp_isee                int            not null,
    id_tp_modalita_calcolo    int            not null,
    isee                      decimal(18, 2) not null,
    ise                       decimal(18, 2) not null,
    isr                       decimal(18, 2) not null,
    isp                       decimal(18, 2) not null,
    scala_equivalenza         decimal(18, 2) not null,
    somma_redditi_componenti  decimal(18, 2) not null,
    reddito_figurativo        decimal(18, 2) not null,
    detrazioni                decimal(18, 2) not null,
    patrimonio_mobiliare      decimal(18, 2) not null,
    detrazione_patrimonio_mob decimal(18, 2) not null,
    patrimonio_immobiliare    decimal(18, 2) not null,
    detrazione_patrimonio_imm decimal(18, 2) not null,
    parametro_nucleo          decimal(18, 2) not null,
    maggiorazioni             decimal(18, 2) not null,
    data_presentazione        datetime,
    data_rilascio             datetime,
    data_validita             datetime,
    constraint fk_isee_domande_tp_modalita_c
        foreign key (id_tp_isee, id_tp_modalita_calcolo) references isee159_tp_modalita_calcolo
)
go

create table domande_permessi_soggiorno
(
    id_domanda                 int not null
        constraint pk_domande_permessi_soggiorno
            primary key
                with (fillfactor = 95)
        constraint FK_domande_permessi_soggiorno_Domande
            references Domande,
    id_tp_permesso             int not null,
    numero_permesso            varchar(50),
    data_rilascio              datetime,
    ente_rilascio              varchar(100),
    data_scadenza              datetime,
    data_richiesta_rinnovo     datetime,
    id_provincia_ente_rilascio nvarchar(2),
    id_luogo_ente_rilascio     nvarchar(6),
    data_rilascio_familiare    datetime,
    ente_rilascio_familiare    nvarchar(100),
    rinnovo                    int
)
go

create table isee159_clesiusdoc
(
    protocollo_dsu nvarchar(27) not null
        constraint FK_isee159_clesiusdoc_isee159_attestazioni
            references isee159_attestazioni,
    id_doc         int          not null
        constraint FK_isee159_clesiusdoc_Doc
            references Doc,
    constraint PK_isee159_clesiusdoc
        primary key (protocollo_dsu, id_doc)
            with (fillfactor = 95)
)
go

create table sia_dati
(
    id_domanda                    int            not null
        constraint pk_sia_dati
            primary key
                with (fillfactor = 95)
        constraint FK_sia_dati_Domande
            references Domande,
    b_residenza_2_anni            smallint       not null,
    c_comp_inf_18                 smallint       not null,
    c_pers_disab_con_genit        smallint       not null,
    c_gravidanza                  smallint       not null,
    c_gravidanza_data             datetime,
    d_tratt_assisten              smallint       not null,
    d_no_autoveicoli              smallint       not null,
    d_no_autoveicoli_max_cilind   smallint       not null,
    e_no_napsi_asdi               smallint       not null,
    id_tp_carico_familiare        int            not null
        constraint FK_sia_dati_sia_tp_carichi_familiari
            references sia_tp_carichi_familiari,
    f_nucl_comp_36mesi            smallint       not null,
    f_nucl_monog_minore           smallint       not null,
    f_nucl_disab_grave            smallint       not null,
    f_nucl_non_autosuff           smallint       not null,
    g_nucl_tutti_disoccup         smallint       not null,
    g_nucl_3figli_min_stesso_gen  smallint       not null,
    isee                          numeric(18, 2) not null,
    isee_si                       smallint       not null,
    id_esito_controlli_invio_INPS smallint       not null
)
go

create table sia_verifica_requisiti
(
    id_domanda             int      not null
        constraint PK_sia_controlli
            primary key
                with (fillfactor = 95)
        constraint FK_sia_verifica_requisiti_sia_dati
            references sia_dati,
    ctrl_cittadinanza      nchar(2) not null,
    ctrl_cittadinanza_desc nvarchar(250),
    ctrl_residenza         nchar(2) not null,
    ctrl_residenza_desc    nvarchar(250),
    ctrl_noAuto            nchar(2) not null,
    ctrl_noAuto_desc       nvarchar(250),
    ctrl_noAutoCC          nchar(2) not null,
    ctrl_noAutoCC_desc     nvarchar(250)
)
go

create table tp_stati_dom
(
    ID_tp_stato_dom int not null
        constraint PK_tp_stati_dom
            primary key
                with (fillfactor = 95),
    tp_stato_dom    nvarchar(50)
)
go

create table tp_stati_firma
(
    id_tp_stato_firma int           not null
        constraint PK_tp_stati_firma
            primary key
                with (fillfactor = 95),
    stato_firma       nvarchar(500) not null,
    stato_firma2      nvarchar(500)
)
go

create table Doc_edizioni
(
    id_doc                                           int      not null,
    edizione_doc                                     int      not null,
    data_edizione                                    datetime not null,
    ID_doc_edizioni_tipologia                        int      not null
        constraint FK_Doc_Edizioni_Doc_edizioni_tipologia
            references Doc_edizioni_tipologia,
    hashcode                                         int,
    archiviato_pat                                   smallint not null,
    id_pat                                           int,
    data_archiviazione                               datetime,
    print_backup_file_name                           nvarchar(50),
    elab_backup_file_name                            nvarchar(50),
    id_user                                          int,
    consegnata_in_copia_a_gdf                        smallint not null,
    id_tp_stato_firma                                int      not null
        constraint FK_Doc_edizioni_tp_stati_firma
            references tp_stati_firma,
    token_id_server_conservazione_firma_grafometrica nvarchar(200),
    token_id_invio_doc_firma_grafometrica            nvarchar(200),
    session_key                                      nvarchar(200),
    task_id                                          nvarchar(20),
    constraint PK_Doc_edizioni
        primary key (id_doc, edizione_doc)
            with (fillfactor = 95)
)
go

create table DU_patrocinio_edizioni
(
    id_doc                         int      not null,
    edizione_doc                   int      not null,
    scaricata_stampa               smallint not null,
    data_scaricamento_stampa       datetime,
    scaricata_elaborazione         smallint not null,
    data_scaricamento_elaborazione datetime,
    patrocinata                    smallint not null,
    data_patrocinio                datetime,
    id_user_patrocinio             int,
    constraint PK_DU_patrocinio_edizioni
        primary key (id_doc, edizione_doc)
            with (fillfactor = 95),
    constraint FK_DU_patrocinio_edizioni_Doc_edizioni
        foreign key (id_doc, edizione_doc) references Doc_edizioni
)
go

create table Doc_edizioni_esiti
(
    id_doc       int          not null,
    edizione_doc int          not null,
    node         nvarchar(50) not null,
    importo      float        not null,
    constraint PK_Doc_edizioni_esiti
        primary key (id_doc, edizione_doc, node)
            with (fillfactor = 95),
    constraint FK_Doc_edizioni_esiti_Doc_edizioni
        foreign key (id_doc, edizione_doc) references Doc_edizioni
)
go

create table GEA_trasmissioni
(
    id_blocco          int      not null,
    id_domanda         int      not null,
    edizione           int      not null,
    data_trasmissione  datetime not null,
    data_presentazione datetime not null,
    data_edizione      datetime not null,
    creata_da_sistema  int      not null,
    id_fascicolo       int      not null,
    id_servizio        int      not null
        constraint FK_GEA_trasmissioni_R_servizi
            references R_Servizi,
    id_ente            int      not null
        constraint FK_GEA_trasmissioni_R_enti
            references R_Enti,
    id_ufficio         int      not null,
    id_protocollo      int,
    n_protocollo       nvarchar(255),
    data_protocollo    datetime,
    id_tp_stato_firma  int      not null
        constraint FK_GEA_trasmissioni_tp_stato_firma
            references tp_stati_firma,
    crc                int      not null,
    token              nvarchar(255),
    id_provincia_altro nvarchar(20),
    id_comune_altro    nvarchar(20),
    indirizzo_altro    nvarchar(255),
    frazione_altro     nvarchar(255),
    n_civ_altro        nvarchar(10),
    cap_altro          nvarchar(10),
    telefono_altro     nvarchar(50),
    presso_altro       nvarchar(255),
    constraint PK_GEA_trasmissioni
        primary key (id_blocco, id_domanda, edizione)
            with (fillfactor = 95),
    constraint FK_GEA_trasmissioni_R_Uffici
        foreign key (id_ente, id_ufficio) references R_Uffici
)
go

create table GEA_benefici
(
    id_blocco       int      not null,
    id_domanda      int      not null,
    id_servizio     int      not null,
    edizione        int      not null,
    id_tp_beneficio int      not null,
    data_da         datetime not null,
    data_a          datetime,
    constraint PK_GEA_benefici_1
        primary key (id_domanda, id_servizio, edizione, id_tp_beneficio, data_da)
            with (fillfactor = 95),
    constraint FK_GEA_benefici_GEA_tp_benefici
        foreign key (id_servizio, id_tp_beneficio) references GEA_tp_benefici,
    constraint FK_GEA_benefici_GEA_trasmissioni
        foreign key (id_blocco, id_domanda, edizione) references GEA_trasmissioni
)
go

create table GEA_esito_blocchi_domande
(
    id_blocco      int not null,
    id_domanda     int not null,
    edizione       int not null,
    id_istanza_GEA int not null,
    id_tp_esito    int not null
        constraint FK_GEA_esito_blocchi_domande_GEA_tp_esiti
            references GEA_tp_esiti,
    nota           nvarchar(2255),
    constraint PK_GEA_esito_blocchi_domande
        primary key (id_blocco, id_domanda, edizione)
            with (fillfactor = 95),
    constraint FK_GEA_esito_blocchi_domande_GEA_trasmissioni
        foreign key (id_blocco, id_domanda, edizione) references GEA_trasmissioni
)
go

create index _dta_index_GEA_esito_blocchi_domande_7_99687603__K5
    on GEA_esito_blocchi_domande (id_tp_esito)
    with (fillfactor = 95)
go

create index IDX_Gea_trasmissioni_id_domanda
    on GEA_trasmissioni (id_domanda)
    with (fillfactor = 95)
go

create table PROTO_Pitre_clesiusDoc
(
    ID_pitre    int not null
        constraint PK_PROTO_Pitre_ClesiusDoc
            primary key
                with (fillfactor = 95),
    ID_Doc      int,
    ID_edizione int,
    constraint FK_PROTO_Pitre_ClesiusDoc_Doc_edizioni
        foreign key (ID_Doc, ID_edizione) references Doc_edizioni
)
go

create index [<Name of Missing Index, sysname,>]
    on PROTO_Pitre_clesiusDoc (ID_Doc, ID_edizione)
    with (fillfactor = 95)
go

create table tp_stato_riferimento
(
    ID_tp_stato_riferimento char not null
        constraint PK_tp_stato_riferimento
            primary key nonclustered
                with (fillfactor = 95),
    tp_stato_riferimento    nvarchar(20)
)
go

create table Dich_isee
(
    ID_dichiarazione          int      not null
        constraint PK_Dichiarazioni_isee
            primary key
                with (fillfactor = 90)
        constraint FK_Dichiarazioni_isee_Doc
            references Doc,
    ID_dich                   int      not null,
    stato_dich                smallint not null,
    chiede_assegno            smallint not null,
    chiede_maternita          smallint not null,
    chiede_nido               smallint not null,
    chiede_mensa              smallint not null,
    chiede_scuola             smallint not null,
    chiede_universita         smallint not null,
    chiede_studio             smallint not null,
    chiede_sanita_domicilio   smallint not null,
    chiede_sanita             smallint not null,
    chiede_agevolazioni       smallint not null,
    chiede_prestazioni        smallint not null,
    chiede_altro1             smallint not null,
    chiede_altro2             smallint not null,
    chiede_servizio1          nvarchar(80),
    chiede_servizio2          nvarchar(80),
    ID_tp_stato_riferimento   char     not null
        constraint FK_Dichiarazioni_isee_tp_stato_riferimento
            references tp_stato_riferimento,
    presenza_invalidi         smallint,
    n_invalidi                int,
    certificazione_rilasciata nvarchar(50),
    un_genitore               smallint
        constraint FK_Dichiarazioni_isee_tp_scelte
            references tp_scelte,
    modelli                   smallint,
    quadro_A                  smallint,
    quadro_B                  smallint,
    quadro_C1                 smallint,
    quadro_C2                 smallint,
    quadro_D                  smallint,
    ID_tp_valuta              char,
    ise_presunto              decimal(12, 2),
    isee_presunto             decimal(12, 2)
)
go

create table tp_studi_settore
(
    ID_tp_studio_settore char(2)       not null
        constraint PK_tp_studi_settore
            primary key
                with (fillfactor = 95),
    tp_studio_settore    nvarchar(100) not null,
    ordine               int
)
go

create table Redditi_impresa
(
    ID_dichiarazione     int            not null
        constraint FK_Redditi_impresa_Dich_icef
            references Dich_icef,
    reddito              smallint       not null,
    ID_tp_impresa        char(3)        not null,
    denominazione        nvarchar(50)   not null,
    partita_iva          nvarchar(11)   not null,
    reddito_normale      decimal(12, 2) not null,
    reddito_dichiarato   decimal(12, 2) not null,
    ID_tp_studio_settore char(2)
        constraint FK_Redditi_impresa_tp_studi_settore
            references tp_studi_settore,
    ID_dich              int            not null,
    valore_ici           decimal(12, 2),
    constraint PK_Redditi_impresa
        primary key (ID_dichiarazione, reddito)
            with (fillfactor = 95),
    constraint FK_Redditi_impresa_tp_imprese
        foreign key (ID_dich, ID_tp_impresa) references tp_imprese
)
go

create table Redditi_partecipazione
(
    ID_dichiarazione     int            not null
        constraint FK_Redditi_partecipazione_Dich_icef
            references Dich_icef,
    reddito              smallint       not null,
    ID_tp_impresa        char(3)        not null,
    denominazione        nvarchar(50)   not null,
    partita_iva          nvarchar(11)   not null,
    utile_fiscale        decimal(16, 2) not null,
    reddito_normale      decimal(16, 2) not null,
    quota                decimal(5, 2)  not null,
    reddito_dichiarato   decimal(12, 2) not null,
    ID_tp_studio_settore char(2)
        constraint FK_Redditi_partecipazione_tp_studi_settore
            references tp_studi_settore,
    ID_dich              int            not null,
    valore_ici           decimal(12, 2),
    quota_capitale       decimal(5, 2),
    constraint PK_Redditi_partecipazione
        primary key (ID_dichiarazione, reddito)
            with (fillfactor = 95),
    constraint FK_Redditi_partecipazione_tp_imprese
        foreign key (ID_dich, ID_tp_impresa) references tp_imprese
)
go

create table tp_usi_immobile
(
    ID_tp_uso_immobile char(5)     not null
        constraint PK_tp_usi_immobile
            primary key
                with (fillfactor = 95),
    tp_uso_immobile    varchar(50) not null,
    ordine             int         not null
)
go

create table ABSOC_immobili_ceduti
(
    ID_domanda                 int            not null
        constraint FK_ABSOC_immobili_ceduti_Domande
            references Domande,
    immobile                   smallint       not null,
    comune_ubicazione          nvarchar(50),
    ID_provincia               nvarchar(2)    not null,
    ID_luogo                   nvarchar(6)    not null,
    quota                      decimal(5, 2)  not null,
    valore_ici                 decimal(16, 2) not null,
    ID_tp_cessione             smallint       not null
        constraint FK_ABSOC_immobili_ceduti_AC_tp_cessioni
            references AC_tp_cessioni,
    mq                         int            not null,
    ID_tp_cat_catastale        nvarchar(4)    not null
        constraint FK_ABSOC_immobili_ceduti_tp_categorie_catastali
            references tp_categorie_catastali,
    ID_tp_diritto              char(2)        not null
        constraint ABSOC_immobili_ceduti_tp_diritti
            references tp_diritti,
    anni_usufrutto             smallint,
    data_nascita_usufruttuario datetime,
    foglio                     nvarchar(10),
    particella                 nvarchar(10),
    subalterno                 nvarchar(10),
    PM                         nvarchar(50),
    ID_tp_immobile             char(2)        not null
        constraint FK_ABSOC_immobili_ceduti_tp_immobili
            references tp_immobili,
    ID_immobile_cat            int,
    ID_titolarita_cat          int,
    ID_comune_catastale        nvarchar(4)    not null,
    residenza_nucleo           smallint       not null,
    ID_tp_uso_immobile         char(5)        not null
        constraint FK_ABSOC_immobili_ceduti_tp_usi_immobile
            references tp_usi_immobile,
    constraint PK_ABSOC_immobili_ceduti
        primary key (ID_domanda, immobile),
    constraint FK_ABSOC_immobili_ceduti_R_Luoghi
        foreign key (ID_luogo, ID_provincia) references R_Luoghi
)
go

create table AC_immobili_ceduti
(
    ID_domanda                 int            not null
        constraint FK_AC_immobili_ceduti_Domande
            references Domande,
    immobile                   smallint       not null,
    comune_ubicazione          nvarchar(50),
    ID_provincia               nvarchar(2)    not null,
    ID_luogo                   nvarchar(6)    not null,
    quota                      decimal(5, 2)  not null,
    valore_ici                 decimal(16, 2) not null,
    ID_tp_cessione             smallint       not null
        constraint FK_AC_immobili_ceduti_AC_tp_cessioni
            references AC_tp_cessioni,
    mq                         int            not null,
    ID_tp_cat_catastale        nvarchar(4)    not null
        constraint FK_AC_immobili_ceduti_tp_categorie_catastali
            references tp_categorie_catastali,
    ID_tp_diritto              char(2)        not null
        constraint AC_immobili_ceduti_tp_diritti
            references tp_diritti,
    anni_usufrutto             smallint,
    data_nascita_usufruttuario datetime,
    foglio                     nvarchar(10),
    particella                 nvarchar(10),
    subalterno                 nvarchar(10),
    PM                         nvarchar(50),
    ID_tp_immobile             char(2)        not null
        constraint FK_AC_immobili_ceduti_tp_immobili
            references tp_immobili,
    ID_immobile_cat            int,
    ID_titolarita_cat          int,
    ID_comune_catastale        nvarchar(4)    not null,
    residenza_nucleo           smallint       not null,
    ID_tp_uso_immobile         char(5)        not null
        constraint FK_AC_immobili_ceduti_tp_usi_immobile
            references tp_usi_immobile,
    constraint PK_AC_immobili_ceduti
        primary key (ID_domanda, immobile)
            with (fillfactor = 95),
    constraint FK_AC_immobili_ceduti_R_Luoghi
        foreign key (ID_luogo, ID_provincia) references R_Luoghi
)
go

create table Patrimoni_immobiliari
(
    ID_dichiarazione           int            not null,
    immobile                   smallint       not null,
    ID_tp_immobile             char(2)        not null
        constraint FK_Patrimoni_immobiliari_tp_immobili
            references tp_immobili,
    comune_ubicazione          nvarchar(50),
    ID_provincia               nvarchar(2),
    ID_luogo                   nvarchar(6)    not null,
    quota                      decimal(5, 2)  not null,
    valore_ici                 decimal(16, 2) not null,
    residenza_nucleo           smallint       not null,
    ID_tp_cat_catastale        nvarchar(4)    not null
        constraint FK_Patrimoni_immobiliari_tp_categorie_catastali
            references tp_categorie_catastali,
    ID_tp_diritto              char(2)        not null
        constraint FK_Patrimoni_immobiliari_tp_diritti
            references tp_diritti
            on update cascade on delete cascade,
    anni_usufrutto             smallint,
    data_nascita_usufruttuario datetime,
    mutuo_residuo              decimal(18, 2),
    partita_catastale          nvarchar(25),
    ID_immobile_cat            int,
    ID_titolarita_cat          int,
    ID_comune_catastale        nvarchar(4)    not null
        constraint FK_Patrimoni_immobiliari_R_comuni_catastali
            references R_comuni_catastali,
    particella                 nvarchar(10),
    subalterno                 nvarchar(10),
    foglio                     nvarchar(10),
    PM                         nvarchar(50),
    aggiornato_com_cat         smallint       not null,
    mq                         int,
    ID_tp_uso_immobile         char(5)
        constraint FK_Patrimoni_immobiliari_tp_usi_immobile
            references tp_usi_immobile,
    constraint PK_Immobili
        primary key (ID_dichiarazione, immobile)
            with (fillfactor = 95),
    constraint FK_Patrimoni_immobiliari_R_Luoghi
        foreign key (ID_luogo, ID_provincia) references R_Luoghi
)
go

create table EDIL_immobile
(
    ID_domanda       int      not null,
    ID_dichiarazione int      not null,
    immobile         smallint not null,
    contatore        int      not null,
    mq               int      not null,
    constraint PK_EDIL_immobile
        primary key (ID_domanda, ID_dichiarazione, immobile)
            with (fillfactor = 95),
    constraint FK_EDIL_immobile_Patrimoni_immobiliari
        foreign key (ID_dichiarazione, immobile) references Patrimoni_immobiliari
)
go

create table EPU_immobili_dubbi
(
    ID_domanda       int      not null
        constraint FK_EPU_immobili_dubbi_Domande
            references Domande,
    immobile_dubbio  int      not null,
    ID_dichiarazione int      not null,
    immobile         smallint not null,
    constraint PK_EPU_immobili_dubbi
        primary key (ID_domanda, immobile_dubbio, ID_dichiarazione, immobile)
            with (fillfactor = 95),
    constraint FK_EPU_immobili_dubbi_Patrimoni_immobiliari
        foreign key (ID_dichiarazione, immobile) references Patrimoni_immobiliari
)
go

create index ID_dichiarazione_Immobili
    on Patrimoni_immobiliari (ID_dichiarazione)
    with (fillfactor = 95)
go

create trigger trigger_Patrimoni_immobiliari_insert_tp_uso_immobile
    on Patrimoni_immobiliari
    for insert
    as
-- missing source code
go

create trigger trigger_Patrimoni_immobiliari_update_tp_uso_immobile
    on Patrimoni_immobiliari
    for update
    as
-- missing source code
go

create table tp_valuta
(
    ID_tp_valuta char not null
        constraint PK_tp_valuta
            primary key nonclustered
                with (fillfactor = 95),
    tp_valuta    char(10)
)
go

create table ultime_domande
(
    id_domanda int
)
go

create table ultime_domande_dati
(
    id_domanda   int,
    RL           float,
    DF           float,
    VF           float,
    PI           float,
    PM           float,
    RES          float,
    N_componenti float,
    under_14     int,
    over_14      int
)
go

create table vcf_tickets_bck
(
    ID_domanda       int         not null,
    ID_dichiarazione int         not null,
    code             numeric(20) not null,
    enabled          smallint,
    data_utilizzo    datetime,
    id_gestore       int         not null,
    stampa           smallint,
    progressivo      int
)
go

create table ws_icef_azioni
(
    id_domanda         int          not null,
    id_domanda_settore nvarchar(50) not null,
    id_azione          smallint     not null,
    data_azione        datetime     not null,
    tipoDomanda        nvarchar(50) not null,
    anno               smallint     not null,
    crc                nvarchar(50) not null,
    data_produzione    datetime     not null,
    data_calcolo_esito datetime     not null,
    descrizione        nvarchar(200)
)
go

create table ws_icef_document_storico_get_documento
(
    id_documento                  int          not null,
    tipologia_documento           nvarchar(10) not null,
    data_richiesta                datetime     not null,
    nome_file_documento_richiesto nvarchar(50) not null,
    codice_fiscale                nvarchar(16) not null
)
go

create table ws_icef_document_storico_get_riferimenti
(
    codice_fiscale nvarchar(16) not null,
    anno_da        int,
    data_richiesta datetime     not null,
    riferimenti    text         not null
)
go

create table ws_icef_domande_settore
(
    id_domanda_settore nvarchar(50) not null,
    id_domanda         int          not null,
    constraint PK_ws_icef_domande_settore
        primary key (id_domanda_settore, id_domanda)
            with (fillfactor = 95)
)
go

create table ws_icef_esiti
(
    id_servizio        int          not null,
    node               nvarchar(50) not null,
    ws_alias_name_node nvarchar(50),
    constraint PK_ws_esiti
        primary key (id_servizio, node)
            with (fillfactor = 90)
)
go

create table ws_icef_tipo_domande
(
    id_servizio              int          not null
        constraint PK_ws_tipo_domande
            primary key
                with (fillfactor = 95),
    tipoDomanda              nvarchar(50) not null,
    anno                     smallint     not null,
    net                      nvarchar(50) not null,
    id_tp_stato_iniziale     smallint     not null,
    id_tp_stato_finale       smallint     not null,
    pubblica                 smallint     not null,
    chiama_tibco             smallint     not null,
    controllo_dati_periodico smallint     not null,
    chiama_apss              smallint     not null,
    chiama_ga                smallint,
    beneficiari_multipli     smallint,
    constraint IX_ws_tipo_domande
        unique (id_servizio, tipoDomanda, anno)
            with (fillfactor = 95)
)
go

create table ws_icef_trasmissioni
(
    id_domanda       int      not null
        constraint PK_ws_icef_trasmissioni_1
            primary key,
    data_inserimento datetime not null,
    domanda          text     not null
)
go

create table ws_icef_trasmissioniinviate
(
    id_domanda        int      not null,
    data_trasmissione datetime not null,
    domanda           text     not null,
    edizione          int      not null
)
go

create index ws_icef_trasmissioniinviate_data_trasmissione
    on ws_icef_trasmissioniinviate (data_trasmissione) include (id_domanda)
    with (fillfactor = 95)
go

create table ws_unitn_esiti
(
    id_servizio int          not null,
    node        nvarchar(50) not null,
    node_alias  nvarchar(50),
    constraint PK_ws_unitn_esiti
        primary key (id_servizio, node)
            with (fillfactor = 95)
)
go

create table ws_unitn_esiti_dati_aggiuntivi
(
    id_servizio                       int           not null,
    nome_dato                         nvarchar(50)  not null,
    tabella                           nvarchar(50)  not null,
    colonna                           nvarchar(200) not null,
    tipologia_dato                    nvarchar(50)  not null,
    obbligatorio                      smallint      not null,
    sql_caricamento_condizionale_dato nvarchar(500),
    constraint PK_ws_unitn_esiti_dati_aggiuntivi
        primary key (id_servizio, nome_dato)
            with (fillfactor = 95)
)
go

create table ws_unitn_esiti_parziali
(
    id_servizio int          not null,
    node        nvarchar(50) not null,
    node_alias  nvarchar(50),
    constraint PK_ws_unitn_esiti_parziali
        primary key (id_servizio, node)
            with (fillfactor = 95)
)
go

create table ws_unitn_esiti_parziali_dati_aggiuntivi
(
    id_servizio                       int           not null,
    nome_dato                         nvarchar(50)  not null,
    tabella                           nvarchar(50)  not null,
    colonna                           nvarchar(200) not null,
    tipologia_dato                    nvarchar(50)  not null,
    obbligatorio                      smallint      not null,
    sql_caricamento_condizionale_dato nvarchar(500),
    constraint PK_ws_unitn_esiti_parziali_dati_aggiuntivi
        primary key (id_servizio, nome_dato)
            with (fillfactor = 95)
)
go

create table ws_unitn_tipo_domande
(
    id_servizio                                  int           not null,
    tipoDomanda                                  nvarchar(50)  not null,
    anno                                         smallint      not null,
    id_tp_stato                                  smallint      not null,
    crea_domanda_da_ws                           smallint      not null,
    nome_tabella_blocco_accesso                  nvarchar(100) not null,
    nome_parametro_blocco_accesso                nvarchar(100) not null,
    sql_caricamento_condizionale_dati_aggiuntivi nvarchar(500),
    constraint PK_ws_unitn_domande
        primary key (id_servizio, tipoDomanda)
            with (fillfactor = 90)
)
go

create unique index IX_ws_unitn_tipo_domande
    on ws_unitn_tipo_domande (tipoDomanda, anno)
go

create table xAUP_familiari_invalidi
(
    ID_domanda        int not null,
    ID_dichiarazione  int not null,
    ID_invalido       int not null,
    codice_invalidita int,
    invalido_dal      datetime,
    invalido_al       datetime,
    constraint PK_AUP_familiari_invalidi
        primary key (ID_domanda, ID_dichiarazione, ID_invalido)
            with (fillfactor = 95)
)
go

create table xAUP_familiari_residenza
(
    ID_domanda       int not null,
    ID_dichiarazione int not null,
    ID_residenza     int not null,
    residenza_dal    datetime,
    residenza_al     datetime,
    constraint PK_AUP_familiari_presenze
        primary key (ID_domanda, ID_dichiarazione, ID_residenza)
            with (fillfactor = 95),
    constraint FK_AUP_familiari_residenza_Familiari
        foreign key (ID_domanda, ID_dichiarazione) references Familiari
)
go

create table xREI_esiti_report_copia
(
    ID_INPS                 int,
    ID_domanda              int,
    CODICE_ENTE             nvarchar(4),
    DATA_MODULO             datetime,
    COGNOME_RICH            nvarchar(35),
    NOME_RICH               nvarchar(35),
    COD_FISCALE_RICH        nvarchar(16),
    STATO_DOMANDA           nvarchar(255),
    NOTE                    nvarchar(255),
    ANNO_MESE_COMP          nvarchar(50) not null,
    DSU_PROTOCOLLO_DOM      nvarchar(255),
    DSU_DATA_SOTT           nvarchar(255),
    ISEE_ESITO              nvarchar(255),
    ISEE_COD_ESITO          nvarchar(255),
    ISEE_DESC               nvarchar(255),
    DSU_PROTOCOLLO_PERIODO  nvarchar(255),
    DSU_DATA_SOTT_PERIODO   datetime,
    DSU_DATA_SCAD_PERIODO   datetime,
    AMMORTIZ_COMPATIBILITA  nvarchar(255),
    COND_LAV                nvarchar(255),
    COND_LAV_DESC           nvarchar(255),
    IMPORTO_CALC_BASE_ANNUA float,
    IMPORTO_DISP            float,
    DATA_POSTE              datetime,
    ESITO_POSTE             nvarchar(255),
    NOTE_ISEE               nvarchar(255),
    xml                     xml
)
go

create table xREI_tp_esclusioni
(
    ID_tp_escludi_ufficio int not null,
    ID_servizio           int not null,
    esclusione            nvarchar(50),
    constraint PK_REI_tp_esclusioni
        primary key (ID_tp_escludi_ufficio, ID_servizio)
            with (fillfactor = 95)
)
go

create table xtemp_REI_assolto
(
    ID_domanda int not null
)
go

create table xtemp_REI_controlli
(
    ID_domanda int,
    controllo  nvarchar(250)
)
go

create table xxPENSPLAN_import_marzo
(
    anno            int,
    cf              varchar(16),
    id_domanda      int,
    n_pratica       int,
    data_inizio     datetime,
    data_fine       datetime,
    giorni          int,
    mesi            int,
    settimane       int,
    tipo_provv      varchar(50),
    tipo_int        varchar(50),
    stato           varchar(50),
    n_aderente      int,
    stato_aderente  varchar(50),
    data_cessazione datetime
)
go

create table xxPENSPLAN_import_old
(
    cf              varchar(16)  not null,
    id              int          not null,
    n_pratica       int          not null,
    datainizio      varchar(8)   not null,
    datafine        varchar(8)   not null,
    giorni          int          not null,
    mese            int          not null,
    settimane       int          not null,
    tipoProv        varchar(50)  not null,
    tipoInt         varchar(100) not null,
    statoPratica    varchar(50)  not null,
    nAderente       int          not null,
    statoAderente   varchar(20)  not null,
    dataCessaz      varchar(8)   not null,
    data_inizio     datetime,
    data_fine       datetime,
    confermato      int,
    data_cessazione datetime,
    note            varchar(250)
)
go

create table xxxCOMP_Elaborazioni_backup
(
    data_elaborazione       datetime     not null,
    cf_utente               nvarchar(16) not null,
    anno                    smallint     not null,
    mese                    smallint     not null,
    ID_tp_intervento        nvarchar(15) not null,
    ID_ente_comp            int          not null,
    ID_Domanda_ICEF         int,
    cf_destinatario_fattura nvarchar(16),
    quantita                numeric(10, 2),
    importo_unitario        numeric(10, 2),
    storno                  numeric(10, 2),
    tetto_max_familiare     numeric(10, 2),
    ICEF                    numeric(10, 6),
    tipo_variazione         nvarchar(2),
    constraint PK_COMP_Elaborazioni_backup
        primary key (data_elaborazione, cf_utente, anno, mese, ID_tp_intervento, ID_ente_comp)
)
go

create table xxxGEA_concessioni
(
    id_domanda       int not null,
    edizione         int,
    data_concessione datetime,
    id               int identity
        constraint PK_GEA_concessioni
            primary key
)
go


