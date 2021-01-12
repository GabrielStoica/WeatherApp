<h1>Aplicație verificare vreme :zap: :cloud: </h1>

## Despre aplicație :question:

Aplicația WeatherApp permite utilizatorilor să vizualizeze statistici meteorologice despre un anumit oraș, aparținând unei țări. Utilizatorul are la dispoziție o interfață grafică intuitivă, prin intermediul căreia trebuie să selecteze țara, iar mai apoi orașul pentru care dorește să vizualizeze date despre: temperatură, descrierea vremii, puterea vântului, precipitații etc.

## Mod de implementare :bulb:

Având la bază API-ul pus la dispoziție de openweathermap, aplicația WeatherApp, realizează apeluri de tip REST, pe baza numelui orașului și codului țării din care acesta face parte, primind datele meteorologice sub forma unui fișier .json. Prin intermediul librărieri json-simple, se realizează prelucrarea datelor din cadrul respectivului fișier, urmând mai apoi a fi afișate corespunzător în cadrul interfeței grafice.

## Etapa de testare 

Pentru etapa de testare au fost realizate 2 teste unitare: 
<ol>
  <li> Primul prin intermediul librariei JUnit in cadrul caruia s-a testat functionalitatea clasei OpenWeatherMapAPI, reponsabila cu receptia datelor de la server, sub forma json. A fost simulat rezultatul ce ar fi fost obtinut pentru un set de date, precum: orasul( + tara) a carui temperatura se doreste a fi afisata. 
De asemenea, pentru verificarea status_code-ului returnat de un apel GET la URL-ul API-ului OpenWeatherMap, a fost simulat un comportament intionat gresit, pentru a evidentia status-code-ul: 404, obtinut din cauza parametrilor neadecvati.</li>
  <li> Cel de-al doilea test, a fost realizat folosind obiecte de tip mock si libraria Mockito. S-a urmarit testarea comportamentului clasei PrimaryController care interactioneaza cu clasele convertFromKelvinToCelsius si OpenWeatherMapAPI. Asadar, au fost realizate teste pentru a verifica daca conversia din grade Kelvin in Celsius se realizeaza corect.</li>
</ol>

