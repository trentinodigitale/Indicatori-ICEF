package c_elab.pat.edilGC;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;

/** idoneità dichiarazioni 08
 * @author s_largher
 */
public class Icef08Tables extends ElainNode {//QImmobiliare {//extends ElainNode {		//

	private static Log log = LogFactory.getLog( Icef08Tables.class );
	
	private Table c_1;
    private Table c_2;
    private Table c_3;
	private Table c_4;
    private Table c_5;
    private Table detrazioni;
    private Table particolarita;
    private Table patr_mob;
    private Table patr_imm;
    private Table componenti;
    private Table dati;
	private Table edilDati;
    
    private static final String tableFamiliari = "Edil_Familiari_contr";
    private static final String tableDati = "Edil_Dati";
    
    private static final String tb_componenti =  Icef08Tables.class.getName()+"_Componenti";
    private static final String tb_c1 =  Icef08Tables.class.getName()+"_C1";
    private static final String tb_c2 =  Icef08Tables.class.getName()+"_C2";
    private static final String tb_c3 =  Icef08Tables.class.getName()+"_C3";
	private static final String tb_c4 =  Icef08Tables.class.getName()+"_C4";
	private static final String tb_c5 =  Icef08Tables.class.getName()+"_C5";
	private static final String tb_detrazioni =  Icef08Tables.class.getName()+"_Detrazioni";
	private static final String tb_particolarita =  Icef08Tables.class.getName()+"_Particolarita";
	private static final String tb_pm =  Icef08Tables.class.getName()+"_Pm";
	private static final String tb_pi =  Icef08Tables.class.getName()+"_Pi";
	private static final String tb_dati =  Icef08Tables.class.getName()+"_Dati";
	private static final String tb_edil_dati =  Icef08Tables.class.getName()+"_EdilDati";
    
    
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		
		ElabStaticContext.getInstance().resetSession( Icef08Tables.class.getName(), IDdomanda, "records" );
		try  {
			ElabStaticContext.getInstance().resetSession( tb_componenti, IDdomanda, "records" );
			ElabStaticContext.getInstance().resetSession( tb_c1, IDdomanda, "records" );
			ElabStaticContext.getInstance().resetSession( tb_c2, IDdomanda, "records" );
			ElabStaticContext.getInstance().resetSession( tb_c3, IDdomanda, "records" );
			ElabStaticContext.getInstance().resetSession( tb_c4, IDdomanda, "records" );
			ElabStaticContext.getInstance().resetSession( tb_c5, IDdomanda, "records" );
			ElabStaticContext.getInstance().resetSession( tb_detrazioni, IDdomanda,  "records" );
			ElabStaticContext.getInstance().resetSession( tb_particolarita, IDdomanda, "records"  );
			//ElabStaticContext.getInstance().resetSession( patr_mob.getClass().getName(), IDdomanda, tb_pm  );
			ElabStaticContext.getInstance().resetSession( tb_pi, IDdomanda, "records"  );
			ElabStaticContext.getInstance().resetSession(tb_dati, IDdomanda, "records" );
			ElabStaticContext.getInstance().resetSession( tb_edil_dati, IDdomanda, "records" );
		}catch (Exception e) {
			e.printStackTrace();
		}

		//to do
		///di tutti
		
		//arrayString ciclo reset
	}
   
    protected String getDefaultQuery() {
		StringBuffer sql = new StringBuffer();
		//      											1                           		2                   				3                                        4                                     5                                     6                                          7                          8                                9                                   10						11									12								 13									14					15
		sql.append("SELECT Patrimoni_immobiliari.residenza_nucleo, R_Relazioni_parentela.peso_patrimonio, Patrimoni_immobiliari.valore_ici, Patrimoni_immobiliari.ID_tp_cat_catastale, Patrimoni_immobiliari.ID_tp_diritto, Patrimoni_immobiliari.anni_usufrutto, Patrimoni_immobiliari.data_nascita_usufruttuario, Doc.data_presentazione, Dich_icef.anno_produzione_patrimoni, Dich_icef.ID_dichiarazione, Dich_icef.anno_produzione_redditi,  Patrimoni_immobiliari.ID_tp_immobile, Patrimoni_immobiliari.quota, Dich_icef.anno_attualizzato, Dich_icef.mese  ");
		sql.append("FROM "+tableFamiliari+" ");
		sql.append("INNER JOIN Patrimoni_immobiliari ON "+tableFamiliari+".ID_dichiarazione = Patrimoni_immobiliari.ID_dichiarazione ");
		sql.append("INNER JOIN Dich_icef ON "+tableFamiliari+".ID_dichiarazione = Dich_icef.ID_dichiarazione ");
		sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
		sql.append("INNER JOIN Doc ON "+tableFamiliari+".ID_domanda = Doc.ID ");
		sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
		sql.append("WHERE Domande.ID_domanda = ");
		sql.append(IDdomanda);
		
		return sql.toString();
	}
    
    
	public void init(RunawayData dataTransfer) {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_pi, IDdomanda, "records" );
		super.init(dataTransfer);
		
		patr_imm = new Table();
		//patr_imm = records;
		session.setAttribute(tb_pi, patr_imm);
		
		setEdilDati();
		
		setComponenti();
			
		setC_1();
		setC_2();
		setC_3();
		setC_4();
		setC_5();
		setDetrazioni();
		setParticolarita();
		setDati();
		//setPatrMob();
    }
	
	
	
	public void setComponenti() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_componenti, IDdomanda, "records" );
    	StringBuffer sqlComponenti = new StringBuffer();
    	componenti =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			sqlComponenti.append("SELECT "+tableFamiliari+".ID_dichiarazione, R_Relazioni_parentela.peso_componente ");//R_Relazioni_parentela.peso_reddito,  ");
			sqlComponenti.append("FROM "+tableFamiliari+" ");
			sqlComponenti.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sqlComponenti.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sqlComponenti.append("WHERE Domande.ID_domanda = ");
			sqlComponenti.append(IDdomanda);
			
			records = new Table();
			doQuery(sqlComponenti.toString());

			session.setInitialized( true );
			componenti = records;
			
			session.setAttribute(tb_componenti, componenti);
			
		} else {
			//records = theRecords;
			componenti = (Table)session.getAttribute(tb_componenti);
		}
    }
	
    
    public void setC_1() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_c1, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	c_1 =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
	    	sql.append("SELECT Redditi_dipendente.ID_tp_reddito, R_Relazioni_parentela.peso_reddito, Redditi_dipendente.importo, Redditi_dipendente.ID_dichiarazione ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_dipendente ON "+tableFamiliari+".ID_dichiarazione = Redditi_dipendente.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			
			records = new Table();
			doQuery(sql.toString());

			session.setInitialized( true );
			c_1 = records;
			
			session.setAttribute(tb_c1, c_1);
			
		} else {
			//records = theRecords;
			c_1 = (Table)session.getAttribute(tb_c1);
		}
    }
    
    public void setC_2() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_c2, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	c_2 =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_agricoli.quantita, R_Importi_agricoli.importo, Redditi_agricoli.costo_locazione, Redditi_agricoli.costo_dipendenti, Redditi_agricoli.quota ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN Redditi_agricoli ON "+tableFamiliari+".ID_dichiarazione = Redditi_agricoli.ID_dichiarazione ");
			sql.append("INNER JOIN R_Importi_agricoli ON (Redditi_agricoli.ID_tp_agricolo = R_Importi_agricoli.ID_tp_agricolo) ");
			sql.append("AND (Redditi_agricoli.ID_zona_agricola = R_Importi_agricoli.ID_zona_agricola) AND (Redditi_agricoli.ID_dich = R_Importi_agricoli.ID_dich) ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			records = new Table();
			doQuery(sql.toString());

			session.setInitialized( true );
			c_2 = records;
			
			session.setAttribute(tb_c2, c_2);
			
		} else {
			//records = theRecords;
			c_2 = (Table)session.getAttribute(tb_c2);
		}
    }
    
    public void setC_3() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_c3, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	c_3 =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			sql.append(
			"SELECT R_Relazioni_parentela.peso_reddito, Redditi_impresa.reddito_dichiarato ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_impresa ON "+tableFamiliari+".ID_dichiarazione = Redditi_impresa.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			records = new Table();
			doQuery(sql.toString());

			session.setInitialized( true );
			c_3 = records;
			
			session.setAttribute(tb_c3, c_3);
			
		} else {
			//records = theRecords;
			c_3 = (Table)session.getAttribute(tb_c3);
		}
    }
    
    public void setC_4() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_c4, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	c_4 =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_partecipazione.reddito_dichiarato, Redditi_partecipazione.utile_fiscale, Redditi_partecipazione.quota ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_partecipazione ON "+tableFamiliari+".ID_dichiarazione = Redditi_partecipazione.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			
			records = new Table();
			doQuery(sql.toString());

			session.setInitialized( true );
			c_4 = records;
			
			session.setAttribute(tb_c4, c_4);
			
		} else {
			//records = theRecords;
			c_4 = (Table)session.getAttribute(tb_c4);
		}
    }
    
    public void setC_5() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_c5, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	c_5 =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_altri.importo ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Redditi_altri ON "+tableFamiliari+".ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			records = new Table();
			doQuery(sql.toString());

			session.setInitialized( true );
			c_5 = records;
			
			session.setAttribute(tb_c5, c_5);
			
		} else {
			//records = theRecords;
			c_5 = (Table)session.getAttribute(tb_c5);
		}
    }
    
    public void setDetrazioni() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_detrazioni, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	detrazioni =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			sql.append("SELECT Detrazioni.ID_tp_detrazione, R_Relazioni_parentela.peso_reddito, Detrazioni.importo, Detrazioni.ID_dichiarazione ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Detrazioni ON "+tableFamiliari+".ID_dichiarazione = Detrazioni.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
		
			records = new Table();
			doQuery(sql.toString());

			session.setInitialized( true );
			detrazioni = records;
			
			session.setAttribute(tb_detrazioni, detrazioni);
			
		} else {
			//records = theRecords;
			detrazioni = (Table)session.getAttribute(tb_detrazioni);
		}
    }
    
    public void setParticolarita() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_particolarita, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	particolarita =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			sql.append("SELECT     tp_invalidita.coeff_invalidita, "+tableFamiliari+".spese_invalidita, Soggetti.data_nascita, "+tableFamiliari+".studente, Dich_icef.anno_produzione_redditi, "); 
            //         6                               7                                         8
			sql.append("Doc.data_presentazione, "+tableFamiliari+".ID_relazione_parentela, R_Relazioni_parentela.peso_componente ");
			sql.append("FROM Soggetti INNER JOIN ");
			sql.append(""+tableFamiliari+" INNER JOIN ");
			sql.append("tp_invalidita ON "+tableFamiliari+".ID_tp_invalidita = tp_invalidita.ID_tp_invalidita INNER JOIN ");
			sql.append("Dich_icef ON "+tableFamiliari+".ID_dichiarazione = Dich_icef.ID_dichiarazione ON Soggetti.ID_soggetto = Dich_icef.ID_soggetto INNER JOIN ");
			sql.append("Doc ON "+tableFamiliari+".ID_domanda = Doc.ID INNER JOIN ");
			sql.append("R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE     "+tableFamiliari+".ID_domanda =  ");
            sql.append(IDdomanda);
            
            records = new Table();
			doQuery(sql.toString());

			session.setInitialized( true );
			particolarita = records;
			
			session.setAttribute(tb_particolarita, particolarita);
			
		} else {
			//records = theRecords;
			particolarita = (Table)session.getAttribute(tb_particolarita);
		}
    }
    
    public void setPatrMob() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_pm, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	patr_mob =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			sql.append("SELECT Patrimoni_finanziari.ID_tp_investimento, R_Relazioni_parentela.peso_patrimonio, Patrimoni_finanziari.consistenza, Patrimoni_finanziari.interessi, Familiari.ID_dichiarazione, Patrimoni_finanziari.consistenza_31_03, Patrimoni_finanziari.consistenza_30_06, Patrimoni_finanziari.consistenza_30_09 ");
			sql.append("FROM "+tableFamiliari+" ");
			sql.append("INNER JOIN Patrimoni_finanziari ON "+tableFamiliari+".ID_dichiarazione = Patrimoni_finanziari.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON "+tableFamiliari+".ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON "+tableFamiliari+".ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);

			records = new Table();
			doQuery( sql.toString() );
			
			session.setInitialized( true );
			patr_mob = records;
			
			session.setAttribute(tb_pm, patr_mob);
			
		} else {
			//records = theRecords;
			patr_mob = (Table)session.getAttribute(tb_pm);
		}
    }
    
    public void setDati() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_dati, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	dati =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			sql.append("SELECT "+tableDati+".un_genitore, "+tableDati+".genitori_lavoratori ");
			sql.append("FROM "+tableDati+" ");
			sql.append("WHERE "+tableDati+".ID_domanda = ");
			sql.append(IDdomanda);

			records = new Table();
			doQuery( sql.toString() );
			
			session.setInitialized( true );
			dati = records;
			
			session.setAttribute(tb_dati, dati);
			
		} else {
			//records = theRecords;
			dati = (Table)session.getAttribute(tb_dati);
		}
    }
    
    public void setEdilDati() {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( tb_edil_dati, IDdomanda, "records" );
    	StringBuffer sql = new StringBuffer();
    	edilDati =  new Table();
		if (!session.isInitialized()) {
			super.init(dataTransfer);
			
			sql.append("SELECT pi_anno_prec, pm_anno_prec, prefinanziamento_anno_prec ");
			sql.append("FROM Edil_dati ");
			sql.append("WHERE Edil_dati.ID_domanda = ");
			sql.append(IDdomanda);

			records = new Table();
			doQuery( sql.toString() );
			
			session.setInitialized( true );
			edilDati = records;
			
			session.setAttribute(tb_edil_dati, edilDati);
			
		} else {
			//records = theRecords;
			edilDati = (Table)session.getAttribute(tb_edil_dati);
		}
    }
    
    

    
    public Table getDati() {
		return dati;
	}

	public Table getPatr_imm() {
		return patr_imm;
	}

	public Table getComponenti() {
		return componenti;
	}

	public Table getC_4() {
		return c_4;
	}

	public Table getC_5() {
		return c_5;
	}

    public Table getC_1() {
		return c_1;
	}

	public Table getC_2() {
		return c_2;
	}

	public Table getC_3() {
		return c_3;
	}

	public Table getDetrazioni() {
		return detrazioni;
	}

	public Table getParticolarita() {
		return particolarita;
	}
	
	public Table getPatr_mob() {
		return patr_mob;
	}
	
    public Table getEdilDati() {
		return edilDati;
	}
	
}