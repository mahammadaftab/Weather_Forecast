package com.weatherforecast.data;

import com.weatherforecast.model.City;
import com.weatherforecast.model.Country;
import com.weatherforecast.model.State;
import com.weatherforecast.model.User;
import com.weatherforecast.repository.CityRepository;
import com.weatherforecast.repository.CountryRepository;
import com.weatherforecast.repository.StateRepository;
import com.weatherforecast.repository.UserRepository;
import com.weatherforecast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (countryRepository.count() == 0) {
            initializeCountries();
            initializeStates();
            initializeCities();
        }
        
        // Create default user if not exists
        if (userRepository.count() == 0) {
            initializeDefaultUser();
        }
        
        // Ensure we have at least one city for testing
        if (cityRepository.count() == 0) {
            initializeTestCity();
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
            
            // South America
            createCountry("BR", "BRA", 76, "Brazil", "South America", "South America", 212559417, 8515767.0, Arrays.asList("BRL"), Arrays.asList("pt"), "Brasília"),
            createCountry("AR", "ARG", 32, "Argentina", "South America", "South America", 45195774, 2780400.0, Arrays.asList("ARS"), Arrays.asList("es"), "Buenos Aires"),
            createCountry("CL", "CHL", 152, "Chile", "South America", "South America", 19116201, 756102.0, Arrays.asList("CLP"), Arrays.asList("es"), "Santiago"),
            createCountry("PE", "PER", 604, "Peru", "South America", "South America", 32971854, 1285216.0, Arrays.asList("PEN"), Arrays.asList("es"), "Lima"),
            createCountry("CO", "COL", 170, "Colombia", "South America", "South America", 50882891, 1141748.0, Arrays.asList("COP"), Arrays.asList("es"), "Bogotá"),
            createCountry("VE", "VEN", 862, "Venezuela", "South America", "South America", 28435940, 916445.0, Arrays.asList("VES"), Arrays.asList("es"), "Caracas"),
            
            // Europe
            createCountry("GB", "GBR", 826, "United Kingdom", "Europe", "Northern Europe", 67886011, 242495.0, Arrays.asList("GBP"), Arrays.asList("en"), "London"),
            createCountry("DE", "DEU", 276, "Germany", "Europe", "Western Europe", 83783942, 357114.0, Arrays.asList("EUR"), Arrays.asList("de"), "Berlin"),
            createCountry("FR", "FRA", 250, "France", "Europe", "Western Europe", 65273511, 643801.0, Arrays.asList("EUR"), Arrays.asList("fr"), "Paris"),
            createCountry("IT", "ITA", 380, "Italy", "Europe", "Southern Europe", 60461826, 301340.0, Arrays.asList("EUR"), Arrays.asList("it"), "Rome"),
            createCountry("ES", "ESP", 724, "Spain", "Europe", "Southern Europe", 46754778, 505992.0, Arrays.asList("EUR"), Arrays.asList("es"), "Madrid"),
            createCountry("NL", "NLD", 528, "Netherlands", "Europe", "Western Europe", 17134872, 41543.0, Arrays.asList("EUR"), Arrays.asList("nl"), "Amsterdam"),
            createCountry("PL", "POL", 616, "Poland", "Europe", "Eastern Europe", 37846611, 312696.0, Arrays.asList("PLN"), Arrays.asList("pl"), "Warsaw"),
            createCountry("SE", "SWE", 752, "Sweden", "Europe", "Northern Europe", 10099265, 450295.0, Arrays.asList("SEK"), Arrays.asList("sv"), "Stockholm"),
            createCountry("NO", "NOR", 578, "Norway", "Europe", "Northern Europe", 5421241, 323802.0, Arrays.asList("NOK"), Arrays.asList("no"), "Oslo"),
            createCountry("CH", "CHE", 756, "Switzerland", "Europe", "Western Europe", 8654622, 41284.0, Arrays.asList("CHF"), Arrays.asList("de", "fr", "it"), "Bern"),
            createCountry("RU", "RUS", 643, "Russia", "Europe", "Eastern Europe", 145934462, 17098242.0, Arrays.asList("RUB"), Arrays.asList("ru"), "Moscow"),
            
            // Asia
            createCountry("CN", "CHN", 156, "China", "Asia", "Eastern Asia", 1439323776, 9596961.0, Arrays.asList("CNY"), Arrays.asList("zh"), "Beijing"),
            createCountry("JP", "JPN", 392, "Japan", "Asia", "Eastern Asia", 126476461, 377975.0, Arrays.asList("JPY"), Arrays.asList("ja"), "Tokyo"),
            createCountry("IN", "IND", 356, "India", "Asia", "Southern Asia", 1380004385, 3287263.0, Arrays.asList("INR"), Arrays.asList("hi", "en"), "New Delhi"),
            createCountry("KR", "KOR", 410, "South Korea", "Asia", "Eastern Asia", 51269185, 100210.0, Arrays.asList("KRW"), Arrays.asList("ko"), "Seoul"),
            createCountry("TR", "TUR", 792, "Turkey", "Asia", "Western Asia", 84339067, 783562.0, Arrays.asList("TRY"), Arrays.asList("tr"), "Ankara"),
            createCountry("SA", "SAU", 682, "Saudi Arabia", "Asia", "Western Asia", 34813871, 2149690.0, Arrays.asList("SAR"), Arrays.asList("ar"), "Riyadh"),
            createCountry("ID", "IDN", 360, "Indonesia", "Asia", "South-Eastern Asia", 273523615, 1904569.0, Arrays.asList("IDR"), Arrays.asList("id"), "Jakarta"),
            createCountry("TH", "THA", 764, "Thailand", "Asia", "South-Eastern Asia", 69799978, 513120.0, Arrays.asList("THB"), Arrays.asList("th"), "Bangkok"),
            createCountry("VN", "VNM", 704, "Vietnam", "Asia", "South-Eastern Asia", 97338579, 331212.0, Arrays.asList("VND"), Arrays.asList("vi"), "Hanoi"),
            
            // Africa
            createCountry("ZA", "ZAF", 710, "South Africa", "Africa", "Southern Africa", 59308690, 1221037.0, Arrays.asList("ZAR"), Arrays.asList("en", "af", "zu"), "Pretoria"),
            createCountry("NG", "NGA", 566, "Nigeria", "Africa", "Western Africa", 206139589, 923768.0, Arrays.asList("NGN"), Arrays.asList("en"), "Abuja"),
            createCountry("EG", "EGY", 818, "Egypt", "Africa", "Northern Africa", 102334404, 1002450.0, Arrays.asList("EGP"), Arrays.asList("ar"), "Cairo"),
            createCountry("KE", "KEN", 404, "Kenya", "Africa", "Eastern Africa", 53771296, 580367.0, Arrays.asList("KES"), Arrays.asList("en", "sw"), "Nairobi"),
            createCountry("MA", "MAR", 504, "Morocco", "Africa", "Northern Africa", 36910560, 446550.0, Arrays.asList("MAD"), Arrays.asList("ar", "fr"), "Rabat"),
            
            // Oceania
            createCountry("AU", "AUS", 36, "Australia", "Oceania", "Australia and New Zealand", 25499884, 7692024.0, Arrays.asList("AUD"), Arrays.asList("en"), "Canberra"),
            createCountry("NZ", "NZL", 554, "New Zealand", "Oceania", "Australia and New Zealand", 4822233, 270467.0, Arrays.asList("NZD"), Arrays.asList("en"), "Wellington")
        );
        
        countryRepository.saveAll(countries);
        System.out.println("✅ Initialized " + countries.size() + " countries");
    }
    
    /**
     * Initialize states/provinces with real data
     */
    private void initializeStates() {
        // We'll add some major states/provinces for key countries
        State[] states = {
            // United States - Major states
            createState(getCountryIdByCode("US"), "CA", "California", "State", "Sacramento", 39538223, 423970.0, 36.7783, -119.4179, "America/Los_Angeles"),
            createState(getCountryIdByCode("US"), "TX", "Texas", "State", "Austin", 29145505, 695662.0, 31.9686, -99.9018, "America/Chicago"),
            createState(getCountryIdByCode("US"), "FL", "Florida", "State", "Tallahassee", 21538187, 170312.0, 27.6648, -81.5158, "America/New_York"),
            createState(getCountryIdByCode("US"), "NY", "New York", "State", "Albany", 20201249, 141297.0, 43.2994, -74.2179, "America/New_York"),
            createState(getCountryIdByCode("US"), "PA", "Pennsylvania", "State", "Harrisburg", 13002700, 119283.0, 41.2033, -77.1945, "America/New_York"),
            
            // Canada - Provinces
            createState(getCountryIdByCode("CA"), "ON", "Ontario", "Province", "Toronto", 14733119, 1076395.0, 51.2538, -85.3232, "America/Toronto"),
            createState(getCountryIdByCode("CA"), "QC", "Quebec", "Province", "Quebec City", 8575779, 1542056.0, 46.8139, -71.2080, "America/Montreal"),
            createState(getCountryIdByCode("CA"), "BC", "British Columbia", "Province", "Victoria", 5145851, 944735.0, 53.7267, -127.6476, "America/Vancouver"),
            createState(getCountryIdByCode("CA"), "AB", "Alberta", "Province", "Edmonton", 4428112, 661848.0, 53.9333, -116.5765, "America/Edmonton"),
            createState(getCountryIdByCode("CA"), "MB", "Manitoba", "Province", "Winnipeg", 1379584, 647797.0, 53.7609, -98.8139, "America/Winnipeg"),
            
            // Australia - States
            createState(getCountryIdByCode("AU"), "NSW", "New South Wales", "State", "Sydney", 8166365, 809444.0, -33.8650, 151.2099, "Australia/Sydney"),
            createState(getCountryIdByCode("AU"), "VIC", "Victoria", "State", "Melbourne", 6680642, 237658.0, -37.4713, 144.7852, "Australia/Melbourne"),
            createState(getCountryIdByCode("AU"), "QLD", "Queensland", "State", "Brisbane", 5184817, 1852642.0, -20.9176, 142.7028, "Australia/Brisbane"),
            createState(getCountryIdByCode("AU"), "WA", "Western Australia", "State", "Perth", 2667179, 2529875.0, -27.6728, 121.6283, "Australia/Perth"),
            createState(getCountryIdByCode("AU"), "SA", "South Australia", "State", "Adelaide", 1770792, 983482.0, -30.0002, 136.2093, "Australia/Adelaide"),
            
            // Brazil - States
            createState(getCountryIdByCode("BR"), "SP", "São Paulo", "State", "São Paulo", 46289333, 248209.0, -23.5505, -46.6333, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "RJ", "Rio de Janeiro", "State", "Rio de Janeiro", 17366164, 43696.0, -22.9068, -43.1729, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "MG", "Minas Gerais", "State", "Belo Horizonte", 21292796, 586528.0, -19.9102, -43.9050, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "RS", "Rio Grande do Sul", "State", "Porto Alegre", 11422479, 281748.0, -30.0346, -51.2177, "America/Sao_Paulo"),
            createState(getCountryIdByCode("BR"), "BA", "Bahia", "State", "Salvador", 14930634, 564273.0, -12.5797, -41.7007, "America/Sao_Paulo"),
            
            // India - States
            createState(getCountryIdByCode("IN"), "MH", "Maharashtra", "State", "Mumbai", 120777787, 307713.0, 19.7515, 75.7139, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "UP", "Uttar Pradesh", "State", "Lucknow", 231507652, 243290.0, 26.8467, 80.9462, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "DL", "Delhi", "Union Territory", "New Delhi", 32941086, 1484.0, 28.7041, 77.1025, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "KA", "Karnataka", "State", "Bengaluru", 65798864, 191791.0, 15.3173, 75.7139, "Asia/Kolkata"),
            createState(getCountryIdByCode("IN"), "TN", "Tamil Nadu", "State", "Chennai", 75695374, 130058.0, 11.1271, 78.6569, "Asia/Kolkata")
        };
        
        stateRepository.saveAll(Arrays.asList(states));
        System.out.println("✅ Initialized " + states.length + " states/provinces");
    }
    
    /**
     * Initialize cities with real data
     */
    private void initializeCities() {
        City[] cities = {
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
            
            // Russia
            createCityExtended("Moscow", "RU", "MOW", 55.7558, 37.6173, 12506468, 156, "P"),
            createCityExtended("Saint Petersburg", "RU", "SPE", 59.9343, 30.3351, 5351935, 5, "P"),
            createCityExtended("Novosibirsk", "RU", "NVS", 55.0084, 82.9357, 1612844, 164, "P"),
            createCityExtended("Yekaterinburg", "RU", "SVE", 56.8389, 60.6057, 1484474, 247, "P"),
            createCityExtended("Kazan", "RU", "TAT", 55.8304, 49.0661, 1243500, 61, "P"),
            
            // South Africa
            createCityExtended("Johannesburg", "ZA", "GP", -26.2041, 28.0473, 5783771, 1753, "P"),
            createCityExtended("Cape Town", "ZA", "WC", -33.9249, 18.4241, 433688, 25, "P"),
            createCityExtended("Durban", "ZA", "KZN", -29.8587, 31.0218, 595066, 8, "P"),
            createCityExtended("Pretoria", "ZA", "GT", -25.7479, 28.2293, 741651, 1332, "P"),
            
            // Nigeria
            createCityExtended("Lagos", "NG", "LA", 6.5244, 3.3792, 14862115, 11, "P"),
            createCityExtended("Kano", "NG", "KN", 12.0001, 8.5167, 4103125, 455, "P"),
            createCityExtended("Ibadan", "NG", "OD", 7.3776, 3.9059, 3565671, 228, "P"),
            createCityExtended("Abuja", "NG", "FC", 9.0765, 7.3986, 3507000, 840, "P"),
            
            // Egypt
            createCityExtended("Cairo", "EG", "C", 30.0444, 31.2357, 9503954, 23, "P"),
            createCityExtended("Alexandria", "EG", "ALX", 31.2001, 29.9187, 5200000, 1, "P"),
            createCityExtended("Giza", "EG", "GZ", 30.0081, 31.2118, 5598402, 20, "P"),
            createCityExtended("Port Said", "EG", "PTS", 31.2653, 32.2881, 650000, 1, "P"),
            
            // Saudi Arabia
            createCityExtended("Riyadh", "SA", "01", 24.7136, 46.6753, 7665000, 612, "P"),
            createCityExtended("Jeddah", "SA", "02", 21.4858, 39.1925, 4600000, 12, "P"),
            createCityExtended("Mecca", "SA", "14", 21.3891, 39.5692, 2300000, 289, "P"),
            createCityExtended("Medina", "SA", "03", 24.5247, 39.5692, 1700000, 635, "P"),
            
            // Turkey
            createCityExtended("Istanbul", "TR", "34", 41.0082, 28.9784, 15462452, 39, "P"),
            createCityExtended("Ankara", "TR", "06", 39.9334, 32.8597, 5503985, 870, "P"),
            createCityExtended("Izmir", "TR", "35", 38.4237, 27.1428, 4320519, 2, "P"),
            createCityExtended("Bursa", "TR", "16", 40.1826, 29.0668, 2936804, 100, "P"),
            
            // Indonesia
            createCityExtended("Jakarta", "ID", "JK", -6.2088, 106.8456, 10770482, 8, "P"),
            createCityExtended("Surabaya", "ID", "JI", -7.2575, 112.7521, 2805950, 5, "P"),
            createCityExtended("Bandung", "ID", "JB", -6.9175, 107.6191, 2575478, 768, "P"),
            createCityExtended("Medan", "ID", "SU", 3.5952, 98.6722, 2233837, 2.5, "P"),
            
            // Thailand
            createCityExtended("Bangkok", "TH", "10", 13.7563, 100.5018, 10539000, 1, "P"),
            createCityExtended("Chiang Mai", "TH", "50", 18.7877, 98.9931, 1318915, 313, "P"),
            createCityExtended("Phuket", "TH", "83", 7.8804, 98.3923, 85725, 1, "P"),
            createCityExtended("Pattaya", "TH", "20", 12.9236, 100.8825, 223165, 7, "P"),
            
            // Vietnam
            createCityExtended("Ho Chi Minh City", "VN", "SG", 10.8231, 106.6297, 8993000, 19, "P"),
            createCityExtended("Hanoi", "VN", "HN", 21.0285, 105.8542, 7781631, 19, "P"),
            createCityExtended("Da Nang", "VN", "DN", 16.0544, 108.2022, 1229653, 11, "P"),
            createCityExtended("Hai Phong", "VN", "HP", 20.8449, 106.6881, 2019171, 5, "P")
        };
        
        cityRepository.saveAll(Arrays.asList(cities));
        System.out.println("✅ Initialized " + cities.length + " cities");
    }
    
    /**
     * Initialize a simple test city if no cities exist
     */
    private void initializeTestCity() {
        City testCity = new City();
        testCity.setName("Test City");
        testCity.setLatitude(40.7128);
        testCity.setLongitude(-74.0060);
        testCity.setPopulation(1000000);
        testCity.setElevation(10L);
        testCity.setFeatureCode("P");
        testCity.setImportance(5);
        
        cityRepository.save(testCity);
        System.out.println("✅ Created test city for development");
    }
    
    /**
     * Initialize default user for testing
     */
    private void initializeDefaultUser() {
        User defaultUser = new User("admin", "admin@example.com");
        defaultUser.setFirstName("Admin");
        defaultUser.setLastName("User");
        defaultUser.setPasswordHash(passwordEncoder.encode("admin123"));
        userRepository.save(defaultUser);
        System.out.println("✅ Created default user: admin/admin123");
    }
    
    /**
     * Helper method to create a country
     */
    private Country createCountry(String code, String code3, int numericCode, String name, String continent, 
                                 String subregion, long population, double area, List<String> currencies, 
                                 List<String> languages, String capital) {
        Country country = new Country();
        country.setCode(code);
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
        return country;
    }
    
    /**
     * Helper method to create a state/province
     */
    private State createState(String countryId, String code, String name, String type, String capital, 
                             long population, double area, double latitude, double longitude, String timezone) {
        State state = new State();
        state.setCountryId(countryId);
        state.setCode(code);
        state.setName(name);
        state.setType(type);
        state.setCapital(capital);
        state.setPopulation(population);
        state.setArea(area);
        state.setLatitude(latitude);
        state.setLongitude(longitude);
        state.setTimezone(timezone);
        return state;
    }
    
    /**
     * Helper method to create a city with extended information
     */
    private City createCityExtended(String name, String countryCode, String stateCode, 
                                   double lat, double lon, long population, double elevation, String featureCode) {
        City city = new City();
        city.setName(name);
        city.setLatitude(lat);
        city.setLongitude(lon);
        city.setPopulation(population);
        city.setElevation((long)elevation);
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