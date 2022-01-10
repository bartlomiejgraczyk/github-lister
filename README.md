#### Autor: Bartłomiej Graczyk

---

#### Stos technologiczny

Java, Maven, Spring Boot, Spring Cloud, Docker, Docker Compose, Prometheus, Node Exporter, cAdvisor, Grafana, Micrometer, Lombok, Swagger, Kohsuke GitHub API for Java

---

#### Podstawowe funkcjonalności

Przedstawiony projekt pozwala na:

* listowanie repozytoriów (nazwa i liczba gwiazdek)
* zwracanie sumy gwiazdek we wszystkich repozytoriach
* listowanie najpopularniejszych języków programowania (nazwa, liczba bajtów kodu)

dla dowolnego użytkownika GitHub.

Dane zwracane są za pomocą protokołu HTTP.

---

#### Infrastruktura

Od strony infrastrukturalnej projekt podzielony jest na 3 mikroserwisy:

**Serwer konfiguracyjny** - Konfiguracja w projekcie została za jego sprawą przeniesiona do zewnętrznego repozytorium Git (link: https://github.com/bartlomiejgraczyk/github-lister-config-repo). Pozwala to na wygodną podmianę konfiguracji znajdującej się w zdalnym repozytorium bez potrzeby restartowania danego serwisu. Możliwa jest również jednoczesna podmiana parametrów konfiguracyjnych dla wszystkich serwisów. Oparty na Spring Cloud Config Server.

**Service discovery** - Serwis umożliwiający rejestrowanie się nowych serwisów. Kontroluje oraz przechowuje informacje o aktualnym stanie (zarejestrowany, dostępny, niedostępny) zarejestrowanych serwisów, a także podstawowe informacje takie jak: zużycie pamięci, dostępna pamięć, nazwa środowiska, na którym jest uruchomiona, ilość dostępnych procesorów oraz czas pracy od ich uruchomienia. Oparty na Spring Cloud Netflix (Eureka Server).

**Core** - właściwy serwis realizujący pobranie danych z API GitHub, ich przetworzenie oraz zwrócenie do użytkownika końcowego za pomocą protokołu HTTP. Oparty o Spring Boot. Do pobierania danych z API GitHub wykorzystywana jest biblioteka GitHubApi for Java autorstwa Kohsuke Kawaguchi (link: https://github-api.kohsuke.org/).

Ponadto podczas ich działania zbierane są metryki o danych z ich działania. Wykorzystane do tego zostały narzędzia monitorujące takie jak: Prometheus, Node Exporter oraz cAdvisor. Dane te przedstawiane są w graficzny sposób z wykorzystaniem platformy Grafana.

Całość uruchamiana jest w 7 kontenerach klastra Docker budowanych z wykorzystaniem Docker Compose oraz poszczególnych plików Dockerfile.

---

#### Instrukcja uruchomienia

Z poziomu głównego katalogu repozytorium zbudować klaster, wywołując polecenie:

`docker-compose up`

Uwaga! Konieczne do uruchomienia jest posiadanie w swoim komputerze zainstalowanego narzędzia docker-compose.
Pierwsze uruchomienie może potrwać dłuższą chwilę ze względu na proces pobierania zależności ze zdalnego repozytorium Maven oraz wymaganych obrazów z Docker Hub.

---

#### Podstawowe użytkowanie

**Pobieranie publicznych repozytoriów (nazwa i liczba gwiazdek) użytkownika GitHub na podstawie jego nazwy użytkownika.**

`http://{host}:8080/users/{nazwa_użytkownika}/repositories`

**Pobieranie sumarycznej liczby gwiazdek dla wszystkich repozytoriów użytkownika GitHub na podstawie jego nazwy użytkownika.**

`http://{host}:8080/users/{nazwa_użytkownika}/stars`

**Pobieranie określonej liczby (domyślnie 10) najpopularniejszych języków (na podstawie ich sumarycznej liczby bajtów) we wszystkich repozytoriach użytkownika określonego przez nazwę użytkownika.**

`http://{host}:8080/users/{nazwa_użytkownika}/languages?count={liczba_języków}`

**Interfejs graficzny Swagger.**

`http://{host}:8080/swagger-ui/index.html`
`http://{host}:8080/docs`

**Wyświetlenie dashboard'u service discovery.**

`http://{host}:8761`

**Wyświetlenie podstawowych informacji o aplikacji.**

`http://{host}:8080/actuator/info`

**Pobranie odświeżonej konfiguracji z serwera konfiguracyjnego.**

`http://{host}:8080/actuator/refresh`
`http://{host}:8888/actuator/refresh`
`http://{host}:8761/actuator/refresh`

**Wyświetlenie dashboard'u platformy Grafana.**

`http://{host}:3000`

---

#### Możliwe rozszerzenia, dalsze kierunki rozwoju

W ramach uproszczenia aplikacja nie została zabezpieczona żadną formą uwierzytelnienia ani autoryzacji. W szczególności podgląd konfiguracji, metryk, dostęp do Swaggera oraz / możliwy jest przez jej dowolnego użytkownika. Aby tego uniknąć, należałoby wprowadzić np. kontrolę dostępu opartą o model RBAC. Ciekawą funkcjonalnością byłoby także umożliwienie autoryzacji z wykorzytaniem standardu OAuth2 z GitHub'em jako serwerem zasobów. Umożliwiłoby to użycie uwierzytelnienia zalogowanego użytkownika podczas kontaktu z API GitHub w ramach pobierania z niego danych o repozytoriach użytkowników, a także umożliwiłoby to pobieranie danych o prywatnych repozytoriach zalogowanego użytkownika.

W celu zabezpieczenia transmisji danych pożądane byłoby także dostarczenie dla aplikacji certyfikatu SSL oraz wykorzystanie protokołu HTTPS zamiast HTTP.

Implementacja HATEOAS dla API aplikacji pozwoliłoby poinformować jego klientów o możliwej dalszej nawigacji zgodnie z trzecim poziomem dojrzałości Richardsona.

Wartym rozważenia byłoby także zaimplementowanie cache dla pobieranych z GitHub'a danych. Umożliwiłoby to nieprzerwane oferowanie prezentowanych funkcjonalności także w przypadku niedostępności danych bezpośrednio z API GitHub.

Kosmetyczną, acz zwiększającą wygodę korzystania byłoby zintegrowanie Swaggera ze Spring Actuator tak, aby jego endpoint'y były dostępne z poziomu Swaggera.

W zakresie infrastruktury powstałego systemu pożądane byłoby przesłonienie powstałego klastra poprzez proxy, np. nginx.

Końcowego efektu dopełniłoby utworzenie aplikacji widokowej do prezentacji zwracanych przez API danych.
