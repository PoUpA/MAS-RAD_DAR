import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.rdf4j.common.iteration.Iterations;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.mapdb.DB;

public class CryptotradingOntology {
	
	private static String namespace = "http://www.jcchatelain.ch/cryptotrading#";
	
	//Define IRI for ontology classes and object properties
	private static IRI Exchange, provide, 
		Trader, place, placeLimit, placeMarket, registeredOn,
		Order, basedOn, side,
		LimitOrder,
		MarketOrder,
		OrderSide,
		Pair, base, target,
		FinancialInstrument,
		Coin,
		Token,
		Fiat;
	//Define IRI for data properties
	private static IRI url, username, entryPrice, size, description, sign, ask, bid, abbreviation,fullName;
	
	//Define IRI for individuals
	private static IRI Binance,
	Kraken,
	ETH_BAT,
	BTC_USD,
	WallE,
	Bob,
	LimitOrder_1,
	LimitOrder_2,
	MarketOrder_1,
	MarketOrder_2,
	Long,
	Short,
	Bitcoin,
	Ethereum,
	BAT,
	KNC,
	USD,
	CHF;
	
	static void buildOntology(Repository rep) {
				
		RepositoryConnection connection = rep.getConnection();
		ValueFactory factory = rep.getValueFactory();
		
		//Create IRI for classes and object properties
		Exchange = factory.createIRI(namespace, "Exchange");
		provide = factory.createIRI(namespace, "provide");
		
		Trader = factory.createIRI(namespace, "Trader");
		place = factory.createIRI(namespace, "place");
		registeredOn = factory.createIRI(namespace, "registeredOn");
		placeLimit = factory.createIRI(namespace, "placeLimit");
		placeMarket = factory.createIRI(namespace, "placeMarket");
		
		Order = factory.createIRI(namespace, "Order");
		basedOn = factory.createIRI(namespace, "basedOn");
		side = factory.createIRI(namespace, "side");
		
		LimitOrder = factory.createIRI(namespace, "LimitOrder");
		MarketOrder = factory.createIRI(namespace, "MarketOrder");
		
		OrderSide = factory.createIRI(namespace, "OrderSide");
		
		Pair = factory.createIRI(namespace, "Pair");
		base = factory.createIRI(namespace, "base");
		target = factory.createIRI(namespace, "target");
		
		FinancialInstrument = factory.createIRI(namespace, "FinancialInstrument");
		Coin = factory.createIRI(namespace, "Coin");
		Token = factory.createIRI(namespace, "Token");
		Fiat = factory.createIRI(namespace, "Fiat");
				
		//Create IRI for data properties		
		url = factory.createIRI(namespace, "url");
		description = factory.createIRI(namespace, "description");
		username = factory.createIRI(namespace, "username");
		entryPrice = factory.createIRI(namespace, "entryPrice");
		size = factory.createIRI(namespace, "size");
		sign = factory.createIRI(namespace, "sign");
		ask = factory.createIRI(namespace, "ask");
		bid = factory.createIRI(namespace, "bid");
		abbreviation = factory.createIRI(namespace, "abbreviation");
		fullName = factory.createIRI(namespace, "fullName");
		
		//Create IRI for individuals	
		Binance = factory.createIRI(namespace, "Binance"); 
		Kraken = factory.createIRI(namespace, "Kraken");
		ETH_BAT = factory.createIRI(namespace, "ETH_BAT");
		BTC_USD = factory.createIRI(namespace, "BTC_USD"); 
		WallE = factory.createIRI(namespace, "WallE");
		Bob = factory.createIRI(namespace, "Bob");
		LimitOrder_1 = factory.createIRI(namespace, "LimitOrder_1");
		LimitOrder_2 = factory.createIRI(namespace, "LimitOrder_2");
		MarketOrder_1 = factory.createIRI(namespace, "MarketOrder_1");
		MarketOrder_2 = factory.createIRI(namespace, "MarketOrder_2");
		Long = factory.createIRI(namespace, "Long"); 
		Short = factory.createIRI(namespace, "Short");
		Bitcoin = factory.createIRI(namespace, "Bitcoin");
		Ethereum = factory.createIRI(namespace, "Ethereum");
		BAT = factory.createIRI(namespace, "BAT");
		KNC = factory.createIRI(namespace, "KNC");
		USD = factory.createIRI(namespace, "USD");
		CHF = factory.createIRI(namespace, "CHF");
		
		try {
			connection.add(Exchange, RDF.TYPE, RDFS.CLASS);
			connection.add(provide, RDF.TYPE, RDF.PROPERTY);
			connection.add(provide, RDFS.DOMAIN, Exchange);
			connection.add(provide, RDFS.RANGE, Pair);
			
			connection.add(Trader, RDF.TYPE, RDFS.CLASS);
			connection.add(registeredOn, RDF.TYPE, RDF.PROPERTY);
			connection.add(registeredOn, RDFS.DOMAIN, Trader);
			connection.add(registeredOn, RDFS.RANGE, Exchange);
			
			connection.add(place, RDF.TYPE, RDF.PROPERTY);
			connection.add(place, RDFS.DOMAIN, Trader);
			connection.add(place, RDFS.RANGE, Order);
			connection.add(placeLimit, RDFS.SUBPROPERTYOF, place);
			connection.add(placeLimit, RDFS.DOMAIN, Trader);
			connection.add(placeLimit, RDFS.RANGE, LimitOrder);
			connection.add(placeMarket, RDFS.SUBPROPERTYOF, place);
			connection.add(placeMarket, RDFS.DOMAIN, Trader);
			connection.add(placeMarket, RDFS.RANGE, MarketOrder);
			
			connection.add(Order, RDF.TYPE, RDFS.CLASS);
			connection.add(basedOn, RDF.TYPE, RDF.PROPERTY);
			connection.add(basedOn, RDFS.DOMAIN, Order);
			connection.add(basedOn, RDFS.RANGE, Pair);
			connection.add(side, RDF.TYPE, RDF.PROPERTY);
			connection.add(side, RDFS.DOMAIN, Order);
			connection.add(side, RDFS.RANGE, OrderSide);
			
			connection.add(LimitOrder, RDF.TYPE, RDFS.CLASS);
			connection.add(LimitOrder, RDFS.SUBCLASSOF, Order);
			connection.add(MarketOrder, RDF.TYPE, RDFS.CLASS);
			connection.add(MarketOrder, RDFS.SUBCLASSOF, Order);
			
			connection.add(OrderSide, RDF.TYPE, RDFS.CLASS);
			
			connection.add(Pair, RDF.TYPE, RDFS.CLASS);
			connection.add(base, RDF.TYPE, RDF.PROPERTY);
			connection.add(base, RDFS.DOMAIN, Pair);
			connection.add(base, RDFS.RANGE, FinancialInstrument);
			connection.add(target, RDF.TYPE, RDF.PROPERTY);
			connection.add(target, RDFS.DOMAIN, Pair);
			connection.add(target, RDFS.RANGE, FinancialInstrument);
			
			connection.add(FinancialInstrument, RDF.TYPE, RDFS.CLASS);
			connection.add(Coin, RDF.TYPE, RDFS.CLASS);
			connection.add(Coin, RDFS.SUBCLASSOF, FinancialInstrument);
			connection.add(Token, RDF.TYPE, RDFS.CLASS);
			connection.add(Token, RDFS.SUBCLASSOF, Coin);
			connection.add(Fiat, RDF.TYPE, RDFS.CLASS);
			connection.add(Fiat, RDFS.SUBCLASSOF, FinancialInstrument);			
			
		} finally {
			connection.close();
		}
	}
	
	static void createIndividualsExchange(Repository rep, IRI iri, String url,  String name, IRI[] provideList) {
		
		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, Exchange);
			conn.add(iri, FOAF.NAME, f.createLiteral(name, XMLSchema.STRING));
			conn.add(iri, CryptotradingOntology.url, f.createLiteral(url, XMLSchema.STRING));
			for (IRI pairIRI : provideList) {
				conn.add(iri, CryptotradingOntology.provide,pairIRI);
			}
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsTrader(Repository rep, IRI iri, String username, String email, IRI[] registeredOnList, IRI[] placeLimitList, IRI[] placeMarketList) {
		
		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.Trader);
			conn.add(iri, CryptotradingOntology.username, f.createLiteral(username, XMLSchema.STRING));
			conn.add(iri, FOAF.MBOX, f.createLiteral(email, XMLSchema.STRING));
			for (IRI registeredOnIRI : registeredOnList) {
				conn.add(iri, CryptotradingOntology.registeredOn,registeredOnIRI);
			}
			for (IRI placeLimitIRI : placeLimitList) {
				conn.add(iri, CryptotradingOntology.placeLimit,placeLimitIRI);
			}
			for (IRI placeMarketIRI : placeMarketList) {
				conn.add(iri, CryptotradingOntology.placeMarket,placeMarketIRI);
			}
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsLimitOrder(Repository rep, IRI iri, IRI basedOn, IRI side, Double size, Double entryPrice) {
		
		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.LimitOrder);
			conn.add(iri, CryptotradingOntology.basedOn, basedOn);
			conn.add(iri, CryptotradingOntology.side, side);
			conn.add(iri, CryptotradingOntology.size, f.createLiteral(size.toString(), XMLSchema.DECIMAL));
			conn.add(iri, CryptotradingOntology.entryPrice, f.createLiteral(entryPrice.toString(), XMLSchema.DECIMAL));
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsMarketOrder(Repository rep, IRI iri, IRI side, IRI basedOn, Double size) {
		
		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.MarketOrder);
			conn.add(iri, CryptotradingOntology.basedOn, basedOn);
			conn.add(iri, CryptotradingOntology.side, side);
			conn.add(iri, CryptotradingOntology.size, f.createLiteral(size.toString(), XMLSchema.DECIMAL));
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsOrderSide(Repository rep, IRI iri, String description, Boolean sign) {
		
		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.OrderSide);
			conn.add(iri, CryptotradingOntology.description, f.createLiteral(description, XMLSchema.STRING));
			conn.add(iri, CryptotradingOntology.sign, f.createLiteral(sign.toString(), XMLSchema.BOOLEAN));
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsPair(Repository rep, IRI iri, IRI base, IRI target, Double ask, Double bid) {
		
		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.Pair);
			conn.add(iri, CryptotradingOntology.base, base);
			conn.add(iri, CryptotradingOntology.target, target);
			conn.add(iri, CryptotradingOntology.ask, f.createLiteral(ask.toString(), XMLSchema.DECIMAL));
			conn.add(iri, CryptotradingOntology.bid, f.createLiteral(bid.toString(), XMLSchema.DECIMAL));
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsCoin(Repository rep, IRI iri, String fullName, String abbreviation) {

		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.Coin);
			conn.add(iri, CryptotradingOntology.fullName, f.createLiteral(fullName, XMLSchema.STRING));
			conn.add(iri, CryptotradingOntology.abbreviation, f.createLiteral(abbreviation, XMLSchema.STRING));
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsToken(Repository rep, IRI iri, String fullName, String abbreviation) {

		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.Token);
			conn.add(iri, CryptotradingOntology.fullName, f.createLiteral(fullName, XMLSchema.STRING));
			conn.add(iri, CryptotradingOntology.abbreviation, f.createLiteral(abbreviation, XMLSchema.STRING));
		} finally {
			conn.close();
		}
	}
	
	static void createIndividualsFiat(Repository rep, IRI iri, String fullName, String abbreviation) {

		RepositoryConnection conn = rep.getConnection();
		ValueFactory f = rep.getValueFactory();
		try {
			conn.add(iri, RDF.TYPE, CryptotradingOntology.Fiat);
			conn.add(iri, CryptotradingOntology.fullName, f.createLiteral(fullName, XMLSchema.STRING));
			conn.add(iri, CryptotradingOntology.abbreviation, f.createLiteral(abbreviation, XMLSchema.STRING));
		} finally {
			conn.close();
		}
	}
	
	
	static void createIndividuals(Repository rep) {
		
		createIndividualsCoin(rep, Bitcoin, "Bitcoin","BTC");
		createIndividualsCoin(rep, Ethereum, "Ethereum", "ETH");
		
		createIndividualsToken(rep, BAT, "Basic Attention Token", "BAT");
		createIndividualsToken(rep, KNC, "Kyber Network", "KNC");
		
		createIndividualsFiat(rep, USD, "U.S. Dollar", "USD");
		createIndividualsFiat(rep, CHF, "Swiss Franc", "CHF");
		
		createIndividualsPair(rep, BTC_USD, Bitcoin, USD, 9589.0,9589.5);
		createIndividualsPair(rep, ETH_BAT, Ethereum, BAT, 0.0009637, 0.0009705);
		
		createIndividualsOrderSide(rep, Long, "Long", true);
		createIndividualsOrderSide(rep, Short, "Short", false);
		

		createIndividualsLimitOrder(rep, LimitOrder_1, BTC_USD, Long, 500.0, 9406.5);
		createIndividualsLimitOrder(rep, LimitOrder_2, ETH_BAT, Long, 100.0, 0.000951);
		createIndividualsMarketOrder(rep, MarketOrder_1, Short, BTC_USD, 37.0);
		createIndividualsMarketOrder(rep, MarketOrder_2, Long, ETH_BAT, 1300.0);
		
		createIndividualsExchange(rep, Binance, "https://www.binance.com",  "Binance",new IRI[] {ETH_BAT});
		createIndividualsExchange(rep, Kraken, "https://www.kraken.com",  "Kraken", new IRI[] {BTC_USD});
		
		createIndividualsTrader(rep, Bob, "BobbyBob", "bob@bob.com", new IRI[] {Kraken, Binance}, new IRI[] {}, new IRI[] { MarketOrder_1, MarketOrder_2 });
		createIndividualsTrader(rep, WallE, "WallyRobot", "bot@bot.com", new IRI[] {Kraken, Binance}, new IRI[] {LimitOrder_1, LimitOrder_2}, new IRI[] {});
		
	}

	
	static void turtleOutput(Repository rep) {
	
		RepositoryConnection conn = rep.getConnection();
		
		RepositoryResult<Statement> statements = conn.getStatements (null, null, null, true);
		Model model = Iterations.addAll(statements, new LinkedHashModel());	
		
		model.setNamespace("rdf", RDF.NAMESPACE);
		model.setNamespace("rdfs", RDFS.NAMESPACE);
		model.setNamespace("xsd", XMLSchema.NAMESPACE);
		model.setNamespace("foaf", FOAF.NAMESPACE);
		model.setNamespace("ns", namespace);
		try {
			FileWriter output = new FileWriter("turtle.n3");
			Rio.write(model, output, RDFFormat.TURTLE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Get all the traders usernames
	static void execQueryGetTraderUsernames(Repository rep) {
		
		RepositoryConnection connection = rep.getConnection();
		try {
			String queryString = "PREFIX db: <http://dbpedia.org/resource/>" + 
								 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
								 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + 
								 "PREFIX : <" + namespace + ">" +
								 "select distinct ?username where { "+
								 		"?x rdf:type :Trader . " +
								 		"?x :username ?username . " +
								 "}";
			
			TupleQuery query = connection.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				while (result.hasNext()) {
					BindingSet currentStatement = result.next();
					String line = "username : " + currentStatement.getValue("username");
					System.out.println(line);
				}
			}
		} finally {
			connection.close();
		}
	}
	
	//Get all the exchanges URLs
	static void execQueryGetExchangeUrls(Repository rep) {
		
		RepositoryConnection connection = rep.getConnection();
		try {
			String queryString = "PREFIX db: <http://dbpedia.org/resource/>" + 
								 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
								 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + 
								 "PREFIX : <" + namespace + ">" +
								 "select distinct ?url where { "+
								 		"?x rdf:type :Exchange . " +
								 		"?x :url ?url . " +
								 "}";
			
			TupleQuery query = connection.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				while (result.hasNext()) {
					BindingSet currentStatement = result.next();
					String line = "username : " + currentStatement.getValue("url");
					System.out.println(line);
				}
			}
		} finally {
			connection.close();
		}
	}
	
	//Get all the long orders above 100 to identify bullish whales
	static void execQueryGetLongOrderValueAbove100(Repository rep) {
		
		RepositoryConnection connection = rep.getConnection();
		try {
			String queryString = "PREFIX db: <http://dbpedia.org/resource/>" + 
								 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
								 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + 
								 "PREFIX : <" + namespace + ">" +
								 "select distinct ?size where { "+
								 		"?x :side :Long . " +
								 		"?x :size ?size . " +
								 		"FILTER($size > 100) . " +
								 "}";
			
			TupleQuery query = connection.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				while (result.hasNext()) {
					BindingSet currentStatement = result.next();
					String line = "size : " + currentStatement.getValue("size");
					System.out.println(line);
				}
			}
		} finally {
			connection.close();
		}
	}
	
	//Show all the sizes of orders and their entry price if they have one
	static void execQueryGetAllOrdersWithEntryPrice(Repository rep) {
		
		RepositoryConnection connection = rep.getConnection();
		try {
			String queryString = "PREFIX db: <http://dbpedia.org/resource/>" + 
								 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
								 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + 
								 "PREFIX : <" + namespace + ">" +
								 "select distinct ?size ?entryPrice where { "+
								 		"?x :size ?size . " +
								 		"OPTIONAL {?x :entryPrice ?entryPrice }" +
								 "}";
			
			TupleQuery query = connection.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				while (result.hasNext()) {
					BindingSet currentStatement = result.next();
					
					String line = "Size : " + currentStatement.getValue("size") + "EntryPrice" +currentStatement.getValue("entryPrice");
					System.out.println(line);
				}
			}
		} finally {
			connection.close();
		}
	}
	
	//Get number of Bob's orders because bob is an important person
	static void execQueryGetBobOrdersCount(Repository rep) {
		
		RepositoryConnection connection = rep.getConnection();
		try {
			String queryString = "PREFIX db: <http://dbpedia.org/resource/>" + 
								 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
								 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + 
								 "PREFIX : <" + namespace + ">" +
								 "select (count(?x) as ?orderCount) where { "+
								 		"{:Bob :placeMarket ?x .}" +
								 	"union {" +
								 		"{:Bob :placeLimit ?x .}" +
								 	"}" +
								 "}";
								 ;
			
			TupleQuery query = connection.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				while (result.hasNext()) {
					BindingSet currentStatement = result.next();
					
					String line = "Bob's number of orders : " + currentStatement.getValue("orderCount	");
					System.out.println(line);
				}
			}
		} finally {
			connection.close();
		}
	}
	
	//Get all the coins sorted by fullname in descending order
	static void execQueryGetCoinsDesc(Repository rep) {
		
		RepositoryConnection connection = rep.getConnection();
		try {
			String queryString = "PREFIX db: <http://dbpedia.org/resource/>" + 
								 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + 
								 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + 
								 "PREFIX : <" + namespace + ">" +
								 "select distinct ?fullName where { "+
								 		"?x rdf:type :Coin . " +
								 		"?x :fullName ?fullName . " +
								 "} ORDER BY DESC(?fullName)";
								 
			
			TupleQuery query = connection.prepareTupleQuery(queryString);

			try (TupleQueryResult result = query.evaluate()) {
				while (result.hasNext()) {
					BindingSet currentStatement = result.next();
					String line = "fullName : " + currentStatement.getValue("fullName");
					System.out.println(line);
				}
			}
		} finally {
			connection.close();
		}
	}
	
	
	public static void main(String[] args) {
		
		Repository rep = new SailRepository(new MemoryStore());
		
		try {
			buildOntology(rep);
			createIndividuals(rep);
			//turtleOutput(rep);
			System.out.println("---Traders usernames ----");
			execQueryGetTraderUsernames(rep);
			System.out.println("---Exchanges urls ----");
			execQueryGetExchangeUrls(rep);
			System.out.println("--- Long Orders with size above 100 ----");
			execQueryGetLongOrderValueAbove100(rep);
			System.out.println("--- All orders with optional entryPrice ----");
			execQueryGetAllOrdersWithEntryPrice(rep);
			System.out.println("--- Bob's orders ----");
			execQueryGetBobOrdersCount(rep);
			System.out.println("--- Coins by descending order ----");
			execQueryGetCoinsDesc(rep);
		} finally {
			
		}	

	}

}
