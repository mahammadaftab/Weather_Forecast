import React, { useState, useRef, useEffect } from 'react';
import { locationApi } from '../services/api';

interface SearchResult {
  id: string;
  name: string;
  country: string;
  state?: string;
}

interface SearchBarProps {
  onSearch: (query: string) => void;
  onResultSelect: (result: SearchResult) => void;
}

const SearchBar: React.FC<SearchBarProps> = ({ onSearch, onResultSelect }) => {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState<SearchResult[]>([]);
  const [showResults, setShowResults] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const searchRef = useRef<HTMLDivElement>(null);

  // Handle search input changes
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setQuery(value);
    
    if (value.length > 2) {
      setIsLoading(true);
      setShowResults(true);
      
      // Call API to search for cities
      locationApi.searchCities(value)
        .then((data) => {
          const formattedResults: SearchResult[] = data.map((item: any) => ({
            id: item.id,
            name: item.name,
            country: item.countryCode,
            state: item.stateCode
          }));
          setResults(formattedResults);
          setIsLoading(false);
        })
        .catch((error) => {
          console.error('Search failed:', error);
          setResults([]);
          setIsLoading(false);
        });
    } else {
      setResults([]);
      setShowResults(false);
    }
  };

  // Handle result selection
  const handleResultClick = (result: SearchResult) => {
    setQuery(`${result.name}, ${result.country}`);
    setShowResults(false);
    onResultSelect(result);
  };

  // Handle search submission
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (query.trim()) {
      onSearch(query);
      setShowResults(false);
    }
  };

  // Close dropdown when clicking outside
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (searchRef.current && !searchRef.current.contains(event.target as Node)) {
        setShowResults(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div ref={searchRef} className="mb-8 relative">
      <form onSubmit={handleSubmit}>
        <div className="relative max-w-2xl mx-auto">
          <input 
            type="text" 
            value={query}
            onChange={handleInputChange}
            onFocus={() => query.length > 2 && setShowResults(true)}
            placeholder="Search for a city..." 
            className="w-full p-4 pl-12 rounded-2xl bg-white/20 backdrop-blur-sm text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-white/50"
          />
          <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 absolute left-4 top-4 text-white/70" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
          <button 
            type="submit"
            className="absolute right-2 top-2 bottom-2 px-4 bg-white/20 rounded-xl hover:bg-white/30 transition"
          >
            Search
          </button>
        </div>
      </form>

      {/* Autocomplete dropdown */}
      {showResults && (
        <div className="absolute z-10 w-full max-w-2xl mt-2 bg-white/30 backdrop-blur-sm rounded-2xl shadow-lg max-h-60 overflow-y-auto">
          {isLoading ? (
            <div className="p-4 text-center text-white">
              <div className="animate-spin rounded-full h-6 w-6 border-b-2 border-white mx-auto"></div>
              <p className="mt-2">Searching...</p>
            </div>
          ) : results.length > 0 ? (
            <ul>
              {results.map((result) => (
                <li 
                  key={result.id}
                  onClick={() => handleResultClick(result)}
                  className="p-4 hover:bg-white/20 cursor-pointer text-white border-b border-white/10 last:border-b-0"
                >
                  <div className="font-medium">{result.name}</div>
                  <div className="text-sm text-white/80">
                    {result.state ? `${result.state}, ${result.country}` : result.country}
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <div className="p-4 text-center text-white">
              No results found
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default SearchBar;