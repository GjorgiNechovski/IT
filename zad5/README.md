Барања на задачата

По избор дали сакате со Razor Pages или MVC користејќи го истиот принцип на работа како во последните примери од предавања со ASP.Net и C# програмскиот јазик, направете апликација со следните побарувања:

    Претпоставка е дека ги имате реализирани сите или барем некои од задачите 1, 2 и 3 и дека мини сервисите од задачите 1,2,3 ви се конфигурирани како docker/podman контајнери и стартувани секој на соодветна порта: 8081, 8082, 8083 соодветно.   Оваа апликација директно ќе упатува HTTP побарувања до сервисите од задачите 1,2 и 3. Ако ви е сѐ во ред наместено:
        на адресата http://localhost:8081/date.cgi ќе одговори сервисот од задача 1 имплементиран со CGI
        на адресата http://localhost:8082/date ќе одговори сервисот од задача 2 имплементиран со Java Tapestry или Spring
        на адресата http://localhost:8083/date ќе одговори сервисот од задача 3 имплементиран со .Net
        Ако ги немате изработено сите 3, стартувајте ги само тие што ги имате, на соодветно нумерираните порти.

    Ф0: Апликацијата од оваа задача треба да се вика application и да ја конфигурирате така што ќе биде стартувана во свој контејнер на портата 8080.
        Апликацијата ќе ги интегрира дирекните повици до направените веб сервиси од задача 1-3, со директно повикување преку HTTP  на адресите каде тие се поставени (опишано погоре)
        Апликацијата треба да има повеќеслојна структура, со внатрешните сервиси кои во заднина ќе ги извршуваат различните побарани функционалности и HTTP повици до потребните сервиси од претходните задачи.
        Ова се минимум побарување кое мора да се исполнат.
    Ф1
        на главната страна (ако е коректно конфигурирано тоа ќе биде http://localhost:8080/application) треба да има само форма за најава, и ништо друго. Во HTML формата треба да има идентификатор loginForm, со полиња за најава со идентификатори username и password и треба да има копчиња за потврда Submit и поништување на внесеното Cancel
        формата се покажува веднаш на почетната страница доколку никој не е најавен, а ако некој е најавен (што треба да е регистрирано во сесиска променлива и/или колаче) веднаш се отвора неговиот поглед (опишан во Ф2 или Ф3)
        по пополнување на формата за најава треба да се валидира дали е уредно пополнета од страна на контролерот за страната, се проверува дали е успешна најавата и во зависност од тоа кој е најавен се префрла на друга страница со соодветниот поглед (Ф2 или Ф3). 
        за проверка на најавата треба да се користи имплементиран заднински сервис кој треба да прима како параметри само корисничко име и лозинка. заднинскиот сервис треба да провери дали се соодветни името и лозинката. ако е успешна најавата ќе врати име на улога за тоа што може да прави корисникот (улога DATE или GUESS_NUMBER). ако не е успешна најавата треба да врати NULL.
        Листата на корисници е следната (внимавајте мали и големи букви), може да ја имплементирате на било кој начин (во ред е и да е како листа константи во изворниот код)
            user1, lozinka1, DATE
            user2, lozinka2, HANGMAN
            user3, lozinka3, HANGMAN
            user4, lozinka4, DATE
        по проверката на најавата и е утврдено дека е DATE корисник се префрла во страна со поглед за операции со датуми Ф2, а за корисниците од тип HANGMAN им се враќа поглед за погодување број Ф3
        ако е непознат или неуспешен корисник останува на главната страна и под формата се појавува порака LOGIN UNSUCCESSFUL
    Ф2: Поглед за операции со датум
        Горе десно го пишува името на најавениот корисник и улогата
        Се листаат сите реализирани сервиси од претходните задачи поврзани со датуми со линк до нив
        Има форма со ид choiceForm во која од радио се бира типот на реализиран сервис за калкулатор кој ќе се повика (со ид: CGI, Java, DotNet), во формата има и поле за внесување во кое се внесуваат потребните параметри за сервисот и треба да има копчиња за потврда Submit и поништување на внесеното Cancel
        По поднесување на формата, се проследува изборот до заднински сервис од апликацијата кој треба да го повика реализираниот сервис од претходните задачи соодветно на изборот (со ид: CGI, Java, DotNet) и да го врати резултатот на контролерот за да се прикаже веднаш под формата. Внимавајте - заднинскиот сервис од оваа апликација треба да пушти заднински HTTP повик до сервисот од задачата 1-2-3, со HttpClient или некоја друга библиотека и да ги процесира потребните податоци, тоа треба да се случи во позадина на сервер, без корисникот на оваа апликација да знае дека податоците се добиени од друга адреса.

    Ф3: Поглед за игра за погодување име на град - бесилка
        Горе десно го пишува името на најавениот корисник и улогата
        Се листаат сите реализирани сервиси од претходните задачи поврзани со игра за погодување број со линк до нив
        Има форма со ид choiceForm во која од радио се бира типот на реализиран сервис за погодување број кој ќе се повика (со ид: CGI, Java, DotNet), во формата има и поле за внесување во кое се внесуваат потребните параметри за сервисот (буквата што се погодува) и треба да има копчиња - за потврда Submit и поништување на внесеното Cancel
        По поднесување на формата, се проследува изборот до интерен заднински сервис кој е дел од оваа апликација и кој треба директно да го повика преку HTTP повик реализираниот сервис од претходните задачи соодветно на изборот (со ид: CGI, Java, DotNet) и да го врати резултатот на контролерот за да се прикаже веднаш под формата. Внимавајте - заднинскиот сервис за проверка на бројот од оваа апликација треба да пушти заднински HTTP повик до сервисот од задачата 1-2-3, со HttpClient или некоја друга библиотека и да ги процесира потребните податоци, а тоа треба да се случи во позадина на серверот, без корисникот на оваа апликација да знае дека податоците се добиени од некоја друга адреса.

Врз основа на пратеното ќе биде направено компајлирање и стартување во контејнер Docker/Podman на порта 8080 (пример Dockerfile и инструкции слично како на https://github.com/dotnet/dotnet-docker/tree/main/samples/aspnetapp )

Ќе бидат автоматски ставени во контејнери и вашите поднесоци од задача 1-3, на портите 8081-8083, на начин како што е опишано во задачите.
