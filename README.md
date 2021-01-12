<h1>Aplicație verificare vreme :zap: :cloud: </h1>

## Despre aplicație :question:

Aplicația WeatherApp permite utilizatorilor să vizualizeze statistici meteorologice despre un anumit oraș, aparținând unei țări. Utilizatorul are la dispoziție o interfață grafică intuitivă, prin intermediul căreia trebuie să selecteze țara, iar mai apoi orașul pentru care dorește să vizualizeze date despre: temperatură, descrierea vremii, puterea vântului, precipitații etc.

## Mod de implementare :bulb:

Având la bază API-ul pus la dispoziție de openweathermap, aplicația WeatherApp, realizează apeluri de tip REST, pe baza numelui orașului și codului țării din care acesta face parte, primind datele meteorologice sub forma unui fișier .json. Prin intermediul librărieri json-simple, se realizează prelucrarea datelor din cadrul respectivului fișier, urmând mai apoi a fi afișate corespunzător în cadrul interfeței grafice.
