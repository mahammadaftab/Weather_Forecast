package com.weatherforecast.data;

import com.weatherforecast.model.City;
import com.weatherforecast.model.Country;
import com.weatherforecast.model.State;
import com.weatherforecast.repository.CityRepository;
import com.weatherforecast.repository.CountryRepository;
import com.weatherforecast.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Service to initialize the database with real worldwide location data
 */
@Component
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (countryRepository.count() == 0) {
            initializeCountries();
            initializeStates();
            initializeCities();
        }
    }

    /**
     * Initialize countries with real worldwide data
     */
    private void initializeCountries() {
        List<Country> countries = Arrays.asList(
            // North America
            createCountry("US", "USA", 840, "United States", "North America", "Northern America", 331002651, 9833517.0, Arrays.asList("USD"), Arrays.asList("en"), "Washington"),
            createCountry("CA", "CAN", 124, "Canada", "North America", "Northern America", 37742154, 9984670.0, Arrays.asList("CAD"), Arrays.asList("en", "fr"), "Ottawa"),
            createCountry("MX", "MEX", 484, "Mexico", "North America", "Central America", 128932753, 1964375.0, Arrays.asList("MXN"), Arrays.asList("es"), "Mexico City"),
            
            // Europe
            createCountry("GB", "GBR", 826, "United Kingdom", "Europe", "Northern Europe", 67886011, 242495.0, Arrays.asList("GBP"), Arrays.asList("en"), "London"),
            createCountry("DE", "DEU", 276, "Germany", "Europe", "Western Europe", 83783942, 357114.0, Arrays.asList("EUR"), Arrays.asList("de"), "Berlin"),
            createCountry("FR", "FRA", 250, "France", "Europe", "Western Europe", 65273511, 643801.0, Arrays.asList("EUR"), Arrays.asList("fr"), "Paris"),
            createCountry("IT", "ITA", 380, "Italy", "Europe", "Southern Europe", 60461826, 301336.0, Arrays.asList("EUR"), Arrays.asList("it"), "Rome"),
            createCountry("ES", "ESP", 724, "Spain", "Europe", "Southern Europe", 46754778, 505992.0, Arrays.asList("EUR"), Arrays.asList("es"), "Madrid"),
            createCountry("RU", "RUS", 643, "Russia", "Europe", "Eastern Europe", 145934462, 17098242.0, Arrays.asList("RUB"), Arrays.asList("ru"), "Moscow"),
            createCountry("SE", "SWE", 752, "Sweden", "Europe", "Northern Europe", 10099265, 450295.0, Arrays.asList("SEK"), Arrays.asList("sv"), "Stockholm"),
            createCountry("NO", "NOR", 578, "Norway", "Europe", "Northern Europe", 5421241, 323802.0, Arrays.asList("NOK"), Arrays.asList("no"), "Oslo"),
            createCountry("FI", "FIN", 246, "Finland", "Europe", "Northern Europe", 5540745, 338424.0, Arrays.asList("EUR"), Arrays.asList("fi", "sv"), "Helsinki"),
            createCountry("DK", "DNK", 208, "Denmark", "Europe", "Northern Europe", 5792202, 43094.0, Arrays.asList("DKK"), Arrays.asList("da"), "Copenhagen"),
            createCountry("NL", "NLD", 528, "Netherlands", "Europe", "Western Europe", 17134872, 41543.0, Arrays.asList("EUR"), Arrays.asList("nl"), "Amsterdam"),
            createCountry("BE", "BEL", 56, "Belgium", "Europe", "Western Europe", 11589623, 30528.0, Arrays.asList("EUR"), Arrays.asList("nl", "fr", "de"), "Brussels"),
            createCountry("CH", "CHE", 756, "Switzerland", "Europe", "Western Europe", 8654622, 41284.0, Arrays.asList("CHF"), Arrays.asList("de", "fr", "it"), "Bern"),
            createCountry("AT", "AUT", 40, "Austria", "Europe", "Western Europe", 9006398, 83871.0, Arrays.asList("EUR"), Arrays.asList("de"), "Vienna"),
            createCountry("PL", "POL", 616, "Poland", "Europe", "Eastern Europe", 37846611, 312679.0, Arrays.asList("PLN"), Arrays.asList("pl"), "Warsaw"),
            createCountry("CZ", "CZE", 203, "Czech Republic", "Europe", "Eastern Europe", 10708981, 78865.0, Arrays.asList("CZK"), Arrays.asList("cs"), "Prague"),
            createCountry("HU", "HUN", 348, "Hungary", "Europe", "Eastern Europe", 9660351, 93028.0, Arrays.asList("HUF"), Arrays.asList("hu"), "Budapest"),
            
            // Asia
            createCountry("JP", "JPN", 392, "Japan", "Asia", "Eastern Asia", 126476461, 377930.0, Arrays.asList("JPY"), Arrays.asList("ja"), "Tokyo"),
            createCountry("CN", "CHN", 156, "China", "Asia", "Eastern Asia", 1439323776, 9596960.0, Arrays.asList("CNY"), Arrays.asList("zh"), "Beijing"),
            createCountry("IN", "IND", 356, "India", "Asia", "Southern Asia", 1380004385, 3287263.0, Arrays.asList("INR"), Arrays.asList("hi", "en"), "New Delhi"),
            createCountry("KR", "KOR", 410, "South Korea", "Asia", "Eastern Asia", 51269185, 100210.0, Arrays.asList("KRW"), Arrays.asList("ko"), "Seoul"),
            createCountry("TH", "THA", 764, "Thailand", "Asia", "South-Eastern Asia", 69799978, 513120.0, Arrays.asList("THB"), Arrays.asList("th"), "Bangkok"),
            createCountry("VN", "VNM", 704, "Vietnam", "Asia", "South-Eastern Asia", 97338579, 331212.0, Arrays.asList("VND"), Arrays.asList("vi"), "Hanoi"),
            createCountry("MY", "MYS", 458, "Malaysia", "Asia", "South-Eastern Asia", 32365999, 330803.0, Arrays.asList("MYR"), Arrays.asList("ms", "en"), "Kuala Lumpur"),
            createCountry("SG", "SGP", 702, "Singapore", "Asia", "South-Eastern Asia", 5850342, 710.0, Arrays.asList("SGD"), Arrays.asList("en", "ms", "ta", "zh"), "Singapore"),
            createCountry("ID", "IDN", 360, "Indonesia", "Asia", "South-Eastern Asia", 273523615, 1904569.0, Arrays.asList("IDR"), Arrays.asList("id"), "Jakarta"),
            createCountry("PH", "PHL", 608, "Philippines", "Asia", "South-Eastern Asia", 109581078, 300000.0, Arrays.asList("PHP"), Arrays.asList("en", "tl"), "Manila"),
            createCountry("TR", "TUR", 792, "Turkey", "Asia", "Western Asia", 84339067, 783562.0, Arrays.asList("TRY"), Arrays.asList("tr"), "Ankara"),
            createCountry("IL", "ISR", 376, "Israel", "Asia", "Western Asia", 8655535, 20770.0, Arrays.asList("ILS"), Arrays.asList("he", "ar"), "Jerusalem"),
            createCountry("SA", "SAU", 682, "Saudi Arabia", "Asia", "Western Asia", 34813871, 2149690.0, Arrays.asList("SAR"), Arrays.asList("ar"), "Riyadh"),
            createCountry("AE", "ARE", 784, "United Arab Emirates", "Asia", "Western Asia", 9890402, 83600.0, Arrays.asList("AED"), Arrays.asList("ar"), "Abu Dhabi"),
            
            // Oceania
            createCountry("AU", "AUS", 36, "Australia", "Oceania", "Australia and New Zealand", 25499884, 7692024.0, Arrays.asList("AUD"), Arrays.asList("en"), "Canberra"),
            createCountry("NZ", "NZL", 554, "New Zealand", "Oceania", "Australia and New Zealand", 4822233, 270467.0, Arrays.asList("NZD"), Arrays.asList("en", "mi"), "Wellington"),
            
            // South America
            createCountry("BR", "BRA", 76, "Brazil", "South America", "South America", 212559417, 8515767.0, Arrays.asList("BRL"), Arrays.asList("pt"), "Brasília"),
            createCountry("AR", "ARG", 32, "Argentina", "South America", "South America", 45195774, 2780400.0, Arrays.asList("ARS"), Arrays.asList("es"), "Buenos Aires"),
            createCountry("CL", "CHL", 152, "Chile", "South America", "South America", 19116201, 756102.0, Arrays.asList("CLP"), Arrays.asList("es"), "Santiago"),
            createCountry("PE", "PER", 604, "Peru", "South America", "South America", 32971854, 1285216.0, Arrays.asList("PEN"), Arrays.asList("es"), "Lima"),
            createCountry("CO", "COL", 170, "Colombia", "South America", "South America", 50882891, 1141748.0, Arrays.asList("COP"), Arrays.asList("es"), "Bogotá"),
            createCountry("VE", "VEN", 862, "Venezuela", "South America", "South America", 28435940, 916445.0, Arrays.asList("VES"), Arrays.asList("es"), "Caracas"),
            
            // Africa
            createCountry("ZA", "ZAF", 710, "South Africa", "Africa", "Southern Africa", 59308690, 1221037.0, Arrays.asList("ZAR"), Arrays.asList("zu", "xh", "af", "en", "tn", "st", "ts", "ss", "ve", "nr"), "Pretoria"),
            createCountry("EG", "EGY", 818, "Egypt", "Africa", "Northern Africa", 102334404, 1002450.0, Arrays.asList("EGP"), Arrays.asList("ar"), "Cairo"),
            createCountry("NG", "NGA", 566, "Nigeria", "Africa", "Western Africa", 206139589, 923768.0, Arrays.asList("NGN"), Arrays.asList("en"), "Abuja"),
            createCountry("KE", "KEN", 404, "Kenya", "Africa", "Eastern Africa", 53771296, 580367.0, Arrays.asList("KES"), Arrays.asList("sw", "en"), "Nairobi"),
            createCountry("MA", "MAR", 504, "Morocco", "Africa", "Northern Africa", 36910560, 446550.0, Arrays.asList("MAD"), Arrays.asList("ar", "fr"), "Rabat")
        );

        countryRepository.saveAll(countries);
        System.out.println("Initialized " + countries.size() + " countries");
    }
    
    /**
     * Helper method to create a country with comprehensive data
     */
    private Country createCountry(String code2, String code3, int numericCode, String name, String continent, String subregion, long population, double area, List<String> currencies, List<String> languages, String capital) {
        Country country = new Country();
        country.setCode(code2);
        country.setCode3(code3);
        country.setNumericCode(numericCode);
        country.setName(name);
        country.setContinent(continent);
        country.setSubregion(subregion);
        country.setPopulation(population);
        country.setArea(area);
        country.setCurrencies(currencies);
        country.setLanguages(languages);
        country.setCapital(capital);
        // Set flag emoji based on country code
        country.setFlagEmoji(getFlagEmoji(code2));
        return country;
    }
    
    /**
     * Helper method to get flag emoji from country code
     */
    private String getFlagEmoji(String countryCode) {
        if (countryCode == null || countryCode.length() != 2) {
            return "🌐";
        }
        int firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

    /**
     * Initialize states/provinces with real data
     */
    private void initializeStates() {
        List<State> states = Arrays.asList(
            // United States - More comprehensive list
            createState(getCountryIdByCode("US"), "AL", "Alabama", "State", "Montgomery", 4903185, 135765.0, 32.377761, -86.300781, "America/Chicago"),
            createState(getCountryIdByCode("US"), "AK", "Alaska", "State", "Juneau", 731545, 1723337.0, 58.301944, -134.419722, "America/Anchorage"),
            createState(getCountryIdByCode("US"), "AZ", "Arizona", "State", "Phoenix", 7278717, 295234.0, 33.448376, -112.074036, "America/Phoenix"),
            createState(getCountryIdByCode("US"), "AR", "Arkansas", "State", "Little Rock", 3017804, 137732.0, 34.746481, -92.289595, "America/Chicago"),
            createState(getCountryIdByCode("US"), "CA", "California", "State", "Sacramento", 39512223, 423970.0, 38.581572, -121.494400, "America/Los_Angeles"),
            createState(getCountryIdByCode("US"), "CO", "Colorado", "State", "Denver", 5758736, 269601.0, 39.739235, -104.990250, "America/Denver"),
            createState(getCountryIdByCode("US"), "CT", "Connecticut", "State", "Hartford", 3565287, 14357.0, 41.763711, -72.685093, "America/New_York"),
            createState(getCountryIdByCode("US"), "DE", "Delaware", "State", "Dover", 973764, 6446.0, 39.158168, -75.524368, "America/New_York"),
            createState(getCountryIdByCode("US"), "FL", "Florida", "State", "Tallahassee", 21477737, 170312.0, 30.438256, -84.280731, "America/New_York"),
            createState(getCountryIdByCode("US"), "GA", "Georgia", "State", "Atlanta", 10617423, 153910.0, 33.748995, -84.387982, "America/New_York"),
            createState(getCountryIdByCode("US"), "HI", "Hawaii", "State", "Honolulu", 1415872, 28313.0, 21.306944, -157.858333, "Pacific/Honolulu"),
            createState(getCountryIdByCode("US"), "ID", "Idaho", "State", "Boise", 1787065, 216443.0, 43.615020, -116.202316, "America/Boise"),
            createState(getCountryIdByCode("US"), "IL", "Illinois", "State", "Springfield", 12671821, 149995.0, 39.798366, -89.654961, "America/Chicago"),
            createState(getCountryIdByCode("US"), "IN", "Indiana", "State", "Indianapolis", 6732219, 94326.0, 39.768402, -86.158066, "America/Indiana/Indianapolis"),
            createState(getCountryIdByCode("US"), "IA", "Iowa", "State", "Des Moines", 3155070, 145746.0, 41.586836, -93.624954, "America/Chicago"),
            createState(getCountryIdByCode("US"), "KS", "Kansas", "State", "Topeka", 2913314, 213112.0, 39.055824, -95.689018, "America/Chicago"),
            createState(getCountryIdByCode("US"), "KY", "Kentucky", "State", "Frankfort", 4467673, 104749.0, 38.200905, -84.873283, "America/Kentucky/Louisville"),
            createState(getCountryIdByCode("US"), "LA", "Louisiana", "State", "Baton Rouge", 4648794, 135382.0, 30.451468, -91.187149, "America/Chicago"),
            createState(getCountryIdByCode("US"), "ME", "Maine", "State", "Augusta", 1344212, 91633.0, 44.310624, -69.779495, "America/New_York"),
            createState(getCountryIdByCode("US"), "MD", "Maryland", "State", "Annapolis", 6045680, 32133.0, 38.978445, -76.492183, "America/New_York"),
            createState(getCountryIdByCode("US"), "MA", "Massachusetts", "State", "Boston", 6892503, 27336.0, 42.358433, -71.059773, "America/New_York"),
            createState(getCountryIdByCode("US"), "MI", "Michigan", "State", "Lansing", 9986857, 250487.0, 42.732535, -84.555535, "America/Detroit"),
            createState(getCountryIdByCode("US"), "MN", "Minnesota", "State", "Saint Paul", 5639632, 225163.0, 44.953703, -93.089958, "America/Chicago"),
            createState(getCountryIdByCode("US"), "MS", "Mississippi", "State", "Jackson", 2976149, 125438.0, 32.298757, -90.184810, "America/Chicago"),
            createState(getCountryIdByCode("US"), "MO", "Missouri", "State", "Jefferson City", 6137428, 180533.0, 38.576702, -92.173516, "America/Chicago"),
            createState(getCountryIdByCode("US"), "MT", "Montana", "State", "Helena", 1068778, 380831.0, 46.588371, -112.024505, "America/Denver"),
            createState(getCountryIdByCode("US"), "NE", "Nebraska", "State", "Lincoln", 1934408, 200330.0, 40.813616, -96.702596, "America/Chicago"),
            createState(getCountryIdByCode("US"), "NV", "Nevada", "State", "Carson City", 3080156, 286380.0, 39.163798, -119.767403, "America/Los_Angeles"),
            createState(getCountryIdByCode("US"), "NH", "New Hampshire", "State", "Concord", 1359711, 24214.0, 43.208167, -71.537572, "America/New_York"),
            createState(getCountryIdByCode("US"), "NJ", "New Jersey", "State", "Trenton", 8882190, 22591.0, 40.220587, -74.759717, "America/New_York"),
            createState(getCountryIdByCode("US"), "NM", "New Mexico", "State", "Santa Fe", 2096829, 314917.0, 35.682211, -105.939724, "America/Denver"),
            createState(getCountryIdByCode("US"), "NY", "New York", "State", "Albany", 19453561, 141297.0, 42.652579, -73.756232, "America/New_York"),
            createState(getCountryIdByCode("US"), "NC", "North Carolina", "State", "Raleigh", 10488084, 139391.0, 35.779590, -78.638176, "America/New_York"),
            createState(getCountryIdByCode("US"), "ND", "North Dakota", "State", "Bismarck", 779094, 183272.0, 46.808327, -100.783739, "America/North_Dakota/Center"),
            createState(getCountryIdByCode("US"), "OH", "Ohio", "State", "Columbus", 11689100, 116098.0, 39.961176, -82.998794, "America/New_York"),
            createState(getCountryIdByCode("US"), "OK", "Oklahoma", "State", "Oklahoma City", 3956971, 181195.0, 35.467560, -97.516428, "America/Chicago"),
            createState(getCountryIdByCode("US"), "OR", "Oregon", "State", "Salem", 4217737, 254799.0, 44.942898, -123.035096, "America/Los_Angeles"),
            createState(getCountryIdByCode("US"), "PA", "Pennsylvania", "State", "Harrisburg", 12801989, 119283.0, 40.273191, -76.886701, "America/New_York"),
            createState(getCountryIdByCode("US"), "RI", "Rhode Island", "State", "Providence", 1059361, 4001.0, 41.823989, -71.412834, "America/New_York"),
            createState(getCountryIdByCode("US"), "SC", "South Carolina", "State", "Columbia", 5148714, 82932.0, 34.000710, -81.034813, "America/New_York"),
            createState(getCountryIdByCode("US"), "SD", "South Dakota", "State", "Pierre", 884659, 199729.0, 44.366843, -100.353759, "America/Chicago"),
            createState(getCountryIdByCode("US"), "TN", "Tennessee", "State", "Nashville", 6829174, 109247.0, 36.162664, -86.781602, "America/Chicago"),
            createState(getCountryIdByCode("US"), "TX", "Texas", "State", "Austin", 28995881, 695662.0, 30.267153, -97.743061, "America/Chicago"),
            createState(getCountryIdByCode("US"), "UT", "Utah", "State", "Salt Lake City", 3205958, 219882.0, 40.760779, -111.891047, "America/Denver"),
            createState(getCountryIdByCode("US"), "VT", "Vermont", "State", "Montpelier", 623989, 24906.0, 44.260059, -72.575387, "America/New_York"),
            createState(getCountryIdByCode("US"), "VA", "Virginia", "State", "Richmond", 8535519, 110787.0, 37.540725, -77.436048, "America/New_York"),
            createState(getCountryIdByCode("US"), "WA", "Washington", "State", "Olympia", 7614893, 184661.0, 47.037874, -122.900695, "America/Los_Angeles"),
            createState(getCountryIdByCode("US"), "WV", "West Virginia", "State", "Charleston", 1792147, 62755.0, 38.349820, -81.632623, "America/New_York"),
            createState(getCountryIdByCode("US"), "WI", "Wisconsin", "State", "Madison", 5822434, 169635.0, 43.073052, -89.401230, "America/Chicago"),
            createState(getCountryIdByCode("US"), "WY", "Wyoming", "State", "Cheyenne", 578759, 253335.0, 41.140253, -104.820246, "America/Denver"),

            // Canada
            createState(getCountryIdByCode("CA"), "ON", "Ontario", "Province", "Toronto", 14733119, 1076395.0, 43.653226, -79.383184, "America/Toronto"),
            createState(getCountryIdByCode("CA"), "BC", "British Columbia", "Province", "Victoria", 5145851, 944735.0, 48.428421, -123.365644, "America/Vancouver"),
            createState(getCountryIdByCode("CA"), "QC", "Quebec", "Province", "Quebec City", 8575779, 1542056.0, 46.813878, -71.207981, "America/Montreal"),
            createState(getCountryIdByCode("CA"), "AB", "Alberta", "Province", "Edmonton", 4421876, 661848.0, 53.544389, -113.490927, "America/Edmonton"),
            createState(getCountryIdByCode("CA"), "MB", "Manitoba", "Province", "Winnipeg", 1379268, 647797.0, 49.895136, -97.138374, "America/Winnipeg"),
            createState(getCountryIdByCode("CA"), "SK", "Saskatchewan", "Province", "Regina", 1177884, 651036.0, 50.445211, -104.618487, "America/Regina"),
            createState(getCountryIdByCode("CA"), "NS", "Nova Scotia", "Province", "Halifax", 979115, 55284.0, 44.648763, -63.575239, "America/Halifax"),
            createState(getCountryIdByCode("CA"), "NB", "New Brunswick", "Province", "Fredericton", 781315, 72908.0, 45.963589, -66.643112, "America/Moncton"),
            createState(getCountryIdByCode("CA"), "NL", "Newfoundland and Labrador", "Province", "St. John's", 521542, 405212.0, 47.560541, -52.712832, "America/St_Johns"),
            createState(getCountryIdByCode("CA"), "PE", "Prince Edward Island", "Province", "Charlottetown", 159625, 5660.0, 46.238240, -63.131070, "America/Halifax"),

            // Germany
            createState(getCountryIdByCode("DE"), "BW", "Baden-Württemberg", "State", "Stuttgart", 11100394, 35751.0, 48.775846, 9.182932, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "BY", "Bavaria", "State", "Munich", 13124737, 70549.0, 48.135125, 11.581981, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "BE", "Berlin", "State", "Berlin", 3669491, 891.0, 52.520008, 13.404954, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "BB", "Brandenburg", "State", "Potsdam", 2521893, 29478.0, 52.390569, 13.064473, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "HB", "Bremen", "State", "Bremen", 681202, 419.0, 53.079296, 8.801694, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "HH", "Hamburg", "State", "Hamburg", 1945232, 755.0, 53.551086, 9.993682, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "HE", "Hesse", "State", "Wiesbaden", 6288080, 21100.0, 50.078218, 8.239761, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "MV", "Mecklenburg-Vorpommern", "State", "Schwerin", 1609815, 23173.0, 53.635502, 11.401250, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "NI", "Lower Saxony", "State", "Hanover", 7993608, 47624.0, 52.375896, 9.732010, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "NW", "North Rhine-Westphalia", "State", "Düsseldorf", 17947221, 34084.0, 51.227144, 6.776164, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "RP", "Rhineland-Palatinate", "State", "Mainz", 4089938, 19854.0, 50.001959, 8.271855, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "SL", "Saarland", "State", "Saarbrücken", 986887, 2571.0, 49.240157, 6.996933, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "SN", "Saxony", "State", "Dresden", 4071971, 18415.0, 51.050411, 13.737262, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "ST", "Saxony-Anhalt", "State", "Magdeburg", 2194782, 20445.0, 52.120533, 11.627624, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "SH", "Schleswig-Holstein", "State", "Kiel", 2903060, 15799.0, 54.323293, 10.122765, "Europe/Berlin"),
            createState(getCountryIdByCode("DE"), "TH", "Thuringia", "State", "Erfurt", 2133378, 16171.0, 50.979492, 11.026480, "Europe/Berlin"),

            // United Kingdom
            createState(getCountryIdByCode("GB"), "ENG", "England", "Country", "London", 56286961, 130279.0, 51.507351, -0.127758, "Europe/London"),
            createState(getCountryIdByCode("GB"), "SCT", "Scotland", "Country", "Edinburgh", 5463300, 78387.0, 55.953251, -3.188361, "Europe/London"),
            createState(getCountryIdByCode("GB"), "WLS", "Wales", "Country", "Cardiff", 3152879, 20779.0, 51.481583, -3.179090, "Europe/London"),
            createState(getCountryIdByCode("GB"), "NIR", "Northern Ireland", "Province", "Belfast", 1893667, 13843.0, 54.597280, -5.930120, "Europe/London"),

            // France
            createState(getCountryIdByCode("FR"), "ARA", "Auvergne-Rhône-Alpes", "Region", "Lyon", 8052300, 69711.0, 45.764043, 4.835659, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "BFC", "Bourgogne-Franche-Comté", "Region", "Dijon", 2792200, 47784.0, 47.322047, 5.041480, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "BRE", "Brittany", "Region", "Rennes", 3340700, 27209.0, 48.111980, -1.674290, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "CVL", "Centre-Val de Loire", "Region", "Orléans", 2559100, 39151.0, 47.902964, 1.909251, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "COR", "Corsica", "Region", "Ajaccio", 344600, 8680.0, 41.919229, 8.738635, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "GES", "Grand Est", "Region", "Strasbourg", 5518500, 57441.0, 48.573405, 7.752111, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "GF", "French Guiana", "Region", "Cayenne", 298700, 83534.0, 4.922420, -52.313453, "America/Cayenne"),
            createState(getCountryIdByCode("FR"), "GP", "Guadeloupe", "Region", "Basse-Terre", 395700, 1628.0, 16.025000, -61.710000, "America/Guadeloupe"),
            createState(getCountryIdByCode("FR"), "HDF", "Hauts-de-France", "Region", "Lille", 5962800, 31813.0, 50.629250, 3.057256, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "IDF", "Île-de-France", "Region", "Paris", 12278200, 12011.0, 48.856614, 2.352222, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "MQ", "Martinique", "Region", "Fort-de-France", 375300, 1128.0, 14.608919, -61.078768, "America/Martinique"),
            createState(getCountryIdByCode("FR"), "YT", "Mayotte", "Region", "Mamoudzou", 279500, 374.0, -12.782381, 45.228776, "Indian/Mayotte"),
            createState(getCountryIdByCode("FR"), "NOR", "Normandy", "Region", "Rouen", 3302100, 30100.0, 49.443232, 1.099971, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "NAQ", "Nouvelle-Aquitaine", "Region", "Bordeaux", 6000300, 84036.0, 44.837789, -0.579180, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "OCC", "Occitanie", "Region", "Toulouse", 5926100, 72724.0, 43.604652, 1.444209, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "PDL", "Pays de la Loire", "Region", "Nantes", 3801200, 32082.0, 47.218371, -1.553621, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "PAC", "Provence-Alpes-Côte d'Azur", "Region", "Marseille", 5055700, 31400.0, 43.296482, 5.369780, "Europe/Paris"),
            createState(getCountryIdByCode("FR"), "RE", "Réunion", "Region", "Saint-Denis", 860000, 2511.0, -20.882310, 55.450676, "Indian/Reunion"),

            // Japan
            createState(getCountryIdByCode("JP"), "01", "Hokkaido", "Prefecture", "Sapporo", 5188441, 83423.0, 43.062096, 141.354370, "Asia/Tokyo"),
            createState(getCountryIdByCode("JP"), "07", "Fukushima", "Prefecture", "Fukushima", 1835915, 13782.0, 37.760860, 140.474854, "Asia/Tokyo"),
            createState(getCountryIdByCode("JP"), "13", "Tokyo", "Prefecture", "Tokyo", 14047579, 2187.0, 35.689487, 139.691706, "Asia/Tokyo"),
            createState(getCountryIdByCode("JP"), "14", "Kanagawa", "Prefecture", "Yokohama", 9198242, 2415.0, 35.447754, 139.642172, "Asia/Tokyo"),
            createState(getCountryIdByCode("JP"), "27", "Osaka", "Prefecture", "Osaka", 8823355, 1905.0, 34.686391, 135.519669, "Asia/Tokyo"),
            createState(getCountryIdByCode("JP"), "40", "Fukuoka", "Prefecture", "Fukuoka", 5120263, 4977.0, 33.590355, 130.401718, "Asia/Tokyo"),

            // China
            createState(getCountryIdByCode("CN"), "BJ", "Beijing", "Municipality", "Beijing", 21542000, 16410.0, 39.904211, 116.407395, "Asia/Shanghai"),
            createState(getCountryIdByCode("CN"), "SH", "Shanghai", "Municipality", "Shanghai", 24281000, 6340.0, 31.230416, 121.473701, "Asia/Shanghai"),
            createState(getCountryIdByCode("CN"), "GD", "Guangdong", "Province", "Guangzhou", 126010000, 179800.0, 23.129110, 113.264385, "Asia/Shanghai"),
            createState(getCountryIdByCode("CN"), "JS", "Jiangsu", "Province", "Nanjing", 84748016, 102600.0, 32.060255, 118.796877, "Asia/Shanghai"),
            createState(getCountryIdByCode("CN"), "SD", "Shandong", "Province", "Jinan", 101527000, 157100.0, 36.670282, 117.019074, "Asia/Shanghai"),

            // India
            createState(getCountryIdByCode("IN"), "MH", "Maharashtra", "State", "Mumbai", 122113000, 122113.0, 19.076090, 72.877426, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "UP", "Uttar Pradesh", "State", "Lucknow", 237882000, 243290.0, 26.846694, 80.946166, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "TN", "Tamil Nadu", "State", "Chennai", 77841000, 130058.0, 13.082680, 80.270718, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "KA", "Karnataka", "State", "Bangalore", 65798000, 191791.0, 12.971599, 77.594563, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "DL", "Delhi", "Union Territory", "New Delhi", 32941000, 1484.0, 28.613939, 77.209021, "Asia/Kolkata"),

            // Australia
            createState(getCountryIdByCode("AU"), "NSW", "New South Wales", "State", "Sydney", 8166000, 809444.0, -33.868820, 151.209295, "Australia/Sydney"),
            createState(getCountryIdByCode("AU"), "VIC", "Victoria", "State", "Melbourne", 6629000, 237658.0, -37.813628, 144.963058, "Australia/Melbourne"),
            createState(getCountryIdByCode("AU"), "QLD", "Queensland", "State", "Brisbane", 5184000, 1852642.0, -27.469771, 153.025124, "Australia/Brisbane"),
            createState(getCountryIdByCode("AU"), "WA", "Western Australia", "State", "Perth", 2667000, 2645615.0, -31.950527, 115.860458, "Australia/Perth"),
            createState(getCountryIdByCode("AU"), "SA", "South Australia", "State", "Adelaide", 1770000, 1043514.0, -34.928499, 138.600746, "Australia/Adelaide"),
            createState(getCountryIdByCode("AU"), "TAS", "Tasmania", "State", "Hobart", 541000, 90758.0, -42.882138, 147.327195, "Australia/Hobart"),
            createState(getCountryIdByCode("AU"), "ACT", "Australian Capital Territory", "Territory", "Canberra", 431000, 2358.0, -35.280937, 149.130005, "Australia/Canberra"),
            createState(getCountryIdByCode("AU"), "NT", "Northern Territory", "Territory", "Darwin", 246000, 1420968.0, -12.463739, 130.844446, "Australia/Darwin"),

            // Brazil
            createState(getCountryIdByCode("BR"), "SP", "São Paulo", "State", "São Paulo", 46289000, 248209.0, -23.550520, -46.633308, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "RJ", "Rio de Janeiro", "State", "Rio de Janeiro", 17366000, 43696.0, -22.906847, -43.172896, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "MG", "Minas Gerais", "State", "Belo Horizonte", 21292000, 586528.0, -19.916681, -43.934493, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "RS", "Rio Grande do Sul", "State", "Porto Alegre", 11423000, 281748.0, -30.034647, -51.217658, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "BA", "Bahia", "State", "Salvador", 14930000, 564273.0, -12.579738, -38.509723, "America/Bahia"),
            createState(getCountryIdByCode("BR"), "PR", "Paraná", "State", "Curitiba", 11517000, 199315.0, -25.427755, -49.273108, "America/Sao_Paulo")
        );

        stateRepository.saveAll(states);
        System.out.println("Initialized " + states.size() + " states");
    }
    
    /**
     * Helper method to create a state with comprehensive data
     */
    private State createState(String countryId, String code, String name, String type, String capital, long population, double area, double lat, double lon, String timezone) {
        State state = new State();        
        state.setCountryId(countryId);
        state.setCode(code);
        state.setName(name);
        state.setType(type);
        state.setCapital(capital);
        state.setPopulation(population);
        state.setArea(area);
        state.setLatitude(lat);
        state.setLongitude(lon);
        state.setTimezone(timezone);
        return state;
    }

    /**
     * Initialize cities with real data
     */
    private void initializeCities() {
        List<City> cities = Arrays.asList(
            // Major world capitals and cities - More comprehensive list
            
            // United States - Major cities
            createCityExtended("New York", "US", "NY", 40.7128, -74.0060, 8336817, 10, "P"),
            createCityExtended("Los Angeles", "US", "CA", 34.0522, -118.2437, 3979576, 71, "P"),
            createCityExtended("Chicago", "US", "IL", 41.8781, -87.6298, 2693976, 181, "P"),
            createCityExtended("Houston", "US", "TX", 29.7604, -95.3698, 2320268, 10, "P"),
            createCityExtended("Phoenix", "US", "AZ", 33.4484, -112.0740, 1680992, 331, "P"),
            createCityExtended("Philadelphia", "US", "PA", 39.9526, -75.1652, 1584064, 12, "P"),
            createCityExtended("San Antonio", "US", "TX", 29.4241, -98.4936, 1547253, 198, "P"),
            createCityExtended("San Diego", "US", "CA", 32.7157, -117.1611, 1423851, 19, "P"),
            createCityExtended("Dallas", "US", "TX", 32.7767, -96.7970, 1343573, 131, "P"),
            createCityExtended("San Jose", "US", "CA", 37.3382, -121.8863, 1021795, 13, "P"),
            createCityExtended("Austin", "US", "TX", 30.2672, -97.7431, 978908, 188, "P"),
            createCityExtended("Jacksonville", "US", "FL", 30.3322, -81.6557, 903889, 10, "P"),
            createCityExtended("Fort Worth", "US", "TX", 32.7555, -97.3308, 895003, 182, "P"),
            createCityExtended("Columbus", "US", "OH", 39.9612, -82.9988, 889079, 271, "P"),
            createCityExtended("Charlotte", "US", "NC", 35.2271, -80.8431, 874579, 212, "P"),
            createCityExtended("San Francisco", "US", "CA", 37.7749, -122.4194, 873965, 16, "P"),
            createCityExtended("Indianapolis", "US", "IN", 39.7684, -86.1581, 871449, 218, "P"),
            createCityExtended("Seattle", "US", "WA", 47.6062, -122.3321, 753675, 56, "P"),
            createCityExtended("Denver", "US", "CO", 39.7392, -104.9903, 715522, 1638, "P"),
            createCityExtended("Washington", "US", "DC", 38.9072, -77.0369, 702455, 13, "P"),
            
            // Canada
            createCityExtended("Toronto", "CA", "ON", 43.6532, -79.3832, 2731571, 76, "P"),
            createCityExtended("Montreal", "CA", "QC", 45.5017, -73.5673, 1704694, 2, "P"),
            createCityExtended("Vancouver", "CA", "BC", 49.2827, -123.1207, 675218, 70, "P"),
            createCityExtended("Calgary", "CA", "AB", 51.0447, -114.0619, 1336000, 1045, "P"),
            createCityExtended("Edmonton", "CA", "AB", 53.5461, -113.4938, 972223, 610, "P"),
            createCityExtended("Ottawa", "CA", "ON", 45.4215, -75.6972, 934243, 71, "P"),
            
            // United Kingdom
            createCityExtended("London", "GB", "ENG", 51.5074, -0.1278, 8982000, 35, "P"),
            createCityExtended("Birmingham", "GB", "ENG", 52.4862, -1.8904, 1141816, 140, "P"),
            createCityExtended("Manchester", "GB", "ENG", 53.4808, -2.2426, 547627, 38, "P"),
            createCityExtended("Glasgow", "GB", "SCT", 55.8642, -4.2518, 635640, 9, "P"),
            createCityExtended("Leeds", "GB", "ENG", 53.8008, -1.5491, 789194, 47, "P"),
            
            // Germany
            createCityExtended("Berlin", "DE", "BE", 52.5200, 13.4050, 3669491, 34, "P"),
            createCityExtended("Hamburg", "DE", "HH", 53.5511, 9.9937, 1945232, 5, "P"),
            createCityExtended("Munich", "DE", "BY", 48.1351, 11.5820, 1488202, 519, "P"),
            createCityExtended("Cologne", "DE", "NRW", 50.9375, 6.9603, 1073096, 38, "P"),
            createCityExtended("Frankfurt", "DE", "HE", 50.1109, 8.6821, 753056, 112, "P"),
            
            // France
            createCityExtended("Paris", "FR", "IDF", 48.8566, 2.3522, 2161000, 35, "P"),
            createCityExtended("Marseille", "FR", "PAC", 43.2965, 5.3698, 873076, 0, "P"),
            createCityExtended("Lyon", "FR", "ARA", 45.7640, 4.8357, 522969, 165, "P"),
            createCityExtended("Toulouse", "FR", "OCC", 43.6047, 1.4442, 479553, 150, "P"),
            createCityExtended("Nice", "FR", "PAC", 43.7102, 7.2620, 342637, 0, "P"),
            
            // Japan
            createCityExtended("Tokyo", "JP", "13", 35.6762, 139.6503, 13929286, 40, "P"),
            createCityExtended("Osaka", "JP", "27", 34.6937, 135.5023, 2691185, 24, "P"),
            createCityExtended("Yokohama", "JP", "14", 35.4437, 139.6380, 3726167, 28, "P"),
            createCityExtended("Nagoya", "JP", "23", 35.1815, 136.9066, 2295638, 37, "P"),
            createCityExtended("Sapporo", "JP", "01", 43.0621, 141.3544, 1969538, 26, "P"),
            
            // China
            createCityExtended("Beijing", "CN", "BJ", 39.9042, 116.4074, 21542000, 43, "P"),
            createCityExtended("Shanghai", "CN", "SH", 31.2304, 121.4737, 24281000, 4, "P"),
            createCityExtended("Guangzhou", "CN", "GD", 23.1291, 113.2644, 12601000, 6, "P"),
            createCityExtended("Shenzhen", "CN", "GD", 22.3193, 114.1694, 12528300, 7, "P"),
            createCityExtended("Chengdu", "CN", "SC", 30.5728, 104.0668, 11309000, 499, "P"),
            
            // India
            createCityExtended("Mumbai", "IN", "MH", 19.0760, 72.8777, 12478447, 14, "P"),
            createCityExtended("Delhi", "IN", "DL", 28.7041, 77.1025, 16787941, 226, "P"),
            createCityExtended("Bangalore", "IN", "KA", 12.9716, 77.5946, 8443675, 920, "P"),
            createCityExtended("Hyderabad", "IN", "TG", 17.3850, 78.4867, 6809970, 545, "P"),
            createCityExtended("Chennai", "IN", "TN", 13.0827, 80.2707, 4646732, 6, "P"),
            createCityExtended("Kolkata", "IN", "WB", 22.5726, 88.3639, 4486679, 9, "P"),
            
            // Australia
            createCityExtended("Sydney", "AU", "NSW", -33.8688, 151.2093, 5312163, 40, "P"),
            createCityExtended("Melbourne", "AU", "VIC", -37.8136, 144.9631, 5078193, 31, "P"),
            createCityExtended("Brisbane", "AU", "QLD", -27.4698, 153.0251, 2514184, 27, "P"),
            createCityExtended("Perth", "AU", "WA", -31.9505, 115.8605, 2059484, 14, "P"),
            createCityExtended("Adelaide", "AU", "SA", -34.9285, 138.6007, 1345777, 50, "P"),
            
            // Brazil
            createCityExtended("São Paulo", "BR", "SP", -23.5505, -46.6333, 12325232, 805, "P"),
            createCityExtended("Rio de Janeiro", "BR", "RJ", -22.9068, -43.1729, 6718903, 2, "P"),
            createCityExtended("Brasília", "BR", "DF", -15.7801, -47.9292, 3055149, 1136, "P"),
            createCityExtended("Salvador", "BR", "BA", -12.9714, -38.5014, 2886698, 8, "P"),
            createCityExtended("Fortaleza", "BR", "CE", -3.7319, -38.5267, 2686612, 20, "P")
        );

        cityRepository.saveAll(cities);
        System.out.println("Initialized " + cities.size() + " cities");
    }
    
    /**
     * Helper method to create a city with extended data
     */
    private City createCityExtended(String name, String countryCode, String stateCode, double lat, double lon, long population, long elevation, String featureCode) {
        City city = new City();
        city.setName(name);
        city.setLatitude(lat);
        city.setLongitude(lon);
        city.setPopulation(population);
        city.setElevation(elevation);
        city.setFeatureCode(featureCode);
        city.setImportance(10); // Default importance
        
        // Set country ID
        countryRepository.findByCode(countryCode).ifPresent(country -> {
            city.setCountryId(country.getId());
            city.setCountryCode(countryCode);
        });
        
        // Set state ID if state code is provided
        if (stateCode != null && !stateCode.isEmpty()) {
            stateRepository.findByCode(stateCode).ifPresent(state -> {
                city.setStateId(state.getId());
                city.setStateCode(stateCode);
            });
        }
        
        return city;
    }

    /**
     * Helper method to create a city with proper associations
     */
    private City createCity(String name, String countryCode, String stateCode, double lat, double lon) {
        return createCityExtended(name, countryCode, stateCode, lat, lon, 0, 0, "P");
    }

    /**
     * Helper method to get country ID by code
     */
    private String getCountryIdByCode(String code) {
        return countryRepository.findByCode(code)
            .map(Country::getId)
            .orElse(null);
    }
}