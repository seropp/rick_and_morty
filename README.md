<h1>ТЗ для приложения "справочник" по вселенной «Рик и Морти»</h1>


<h2>Верхнеуровневое описание требований к проекту</h2>
<h4>Верхнеуровневое описание будет состоять из трех пунктов:
1. То, что выполненно
2. Что не выполнено
3. То что было выполнено, но не до конца.
  
Подробное описание в соответствующих разделах.</h4>

<h4>1. Были реализованно следующее:</h4>
<p><b>-</b> Кэширование и работа без интернета</p>
<p><b>-</b> Навигация назад</p>
<p><b>-</b> Отображение стрелки на всех экранах кроме гавных</p>
<p><b>-</b> Поддержание фунцкции Pull-to-refresh </p>
<p><b>-</b> Отображение ProgressBar </p>
<p><b>-</b> Реализован SplashScreen</p>
<p><b>-</b> Выполнены критерии по отоброжению информации для каждого из экранов</p>
<p><b>-</b> Реализована библеотека Paging 3</p>
<h4>Дополнения по реалицации</h4>
<p><b>-</b> В приложение был внедрен Dagger2</p>
<p><b>-</b> Использовались Coroutine (Flow, LiveData)</p>
<p><b>-</b> В качестве Adapter был Использован ListAdapter(с Diffutils) для списков не использующих пагинацию</p>
<p><b>-</b> Использование Room с различными запросами</p>
<p><b>-</b> В качестве Архитекктуры использована CleanArcheticture</p>

<h4>Не реализованны следующие вещи:<h4>
<p><b>-</b> Поиск по ключевым словам (запросы есть, не был реализован SearchView)</p>
<p><b>-</b> Тесты</p>

<h4>Реализованно, но не коректно<h4>
<p>За счет использования библеотеки Paging3 возможность реализовать коректную фильтрацию не удалось (Для персоажей)
 Для других вкладок фильтрация есть(по одному параметру). Это связанно с реализацией самой библеотеки Paging3.</p>

<h1>Схематичное представление Presentation и Domain слоев</h1>
<p>C указанием маршрутов загрузки данных</p>
![image](https://user-images.githubusercontent.com/85056996/169247482-41d6529a-4d0b-43d0-bc53-eb93b9f985cb.png)

<h1>Навигация по какой-либо из категорий</h1>
<p>Не указаны переходы с Фрагмента деталей*</p>
![image](https://user-images.githubusercontent.com/85056996/164718143-418e1659-019f-4253-afcf-5fb306d1b85d.png)
 
<h2>Функциональное задание</h2>
<p>Необходимо разработать приложение реализующее <a href="https://rickandmortyapi.com/"><b>Rick and Morty API</b></a>.
Приложение должно иметь главный экран с отображением списка персонажей, локаций либо эпизодов, в зависимости от выбранной вкладки.
Каждая вкладка главного экрана должна реализовывать функции поиска и фильтрации по списку данных, а также поддерживать  Pull-to-Refresh.
Кроме этого, приложение должно содержать в себе несколько  экранов на которых можно будет посмотреть информацию о персонажах, локациях либо эпизодах.
Каждый из этих экранов должен иметь стрелку "Назад"</p>
<p>Приложение должно поддерживать кеширование и иметь возможность работать без интернета. Также весь функционал приложения (поиск, фильтрация)
должен корректно осуществляться без доступа к сети.</p>
<p>Приложение должно иметь Splash Screen</p>
___________________________________________________________________

<h2>Дизайн UI приложения</h2>

<h3>1. Splash Screen</h3>

<p>Для реализации Splash Screen был создан дополнительный xml файл(тема). В нем мы описали сам Splash Screen. Установили его, фон, поместили иконку, а также указали postSplashScreenTheme, который будет отображаться после показа SplashScreen. В Манифесте мы указали XML файл Splash Screen как стиль, который будет показваться при запуске приложения запуска для приложения.</p>
<p>В нашем активити (RootActivity) мы создали view model (SplashScreenViewModel), который содержит в себе лишь одну переменную, (isLoading) которая инкапсулирует булевское значение (по умолчанию true), значение изменяется через 2000мс (установили delay). Нашу переменную мы передаём в метод отвечающий за создание и в дальнейшем отображение Splash Screen. После того как переменная была изменена мы меняем тему на тему указанную в postSplashScreenTheme(в XMl Splash Screen)</p>


<h3>2. "Архитектура экранов"</h3>

<p>Для UI используем <b>Single Activity Architecture</b></p>

<p>В нашем единственном RootActivity будет расположен BottomNavigationView и контейнер для фрагментов. BottomNavigationView будет содержать меню для переключения между основными фрагментами, в качестве таковых выбраны 3 фрагмента: CharactersFragment, LocationsFragment и EpisodesFragment.</p>

<h4>"Основной фрагмент"<h4>

<p>Каждый из основных фрагментов имеет однотипный дизайн, главными компонентами которого являются RecyclerView(с атрибутом layoutManager и spanCount="2"), SearchView а также ImageView (при нажатии на который будет отковаться FilterFragment). В качестве адаптера для RecyclerView выступает PagingDataAdapter (использование Paging3)</p>

<h4>"Filter Fragment"<h4>

<p>Из каждого основного фрагмента есть возможность открыть фрагмент Фильтр, в котором можно будет выбрать фильтры.</p>
<p>Для реализация Фильтров использовали <b>BottomSheetDialogFragment</b>. При нажатии на кнопку "Apply" будем происходить применение фильтров</p>

<h4>"Detail Fragment"<h4>

<p>Нажатие на элемент из RecyclerView основного фильтра будет открывать фрагмент с детальной информацией об элементе. В фрагменте DetailFragmemt будет возможность переключиться на DetailFragmemt другой категории. А также реализована кнопка back</p>

<h3>По предварительным подсчетам приложение будет состоять из 1 Activity и 9 Fragments</h3>
<h3>Навигация между фрагментами будет осуществляться через NavigationComponents</h3>



<p>В качестве адаптера будем использовать ListAdapter c DiffUtils</p>
___________________________________________________________________

<h2>Реализация функций приложения</h2>

<h3>В качестве архитектуры приложения мы будем использовать CleanArchitecture с ViewModels (возможны изменения)</h3>

<h3>Получение данных</h3>
<p>Так как наше приложение должно корректно работать при отсутствии интернета, мы будем использовать ROOM в качестве локального хранилища.</p>
<p>У нас есть в два потенциальных источника данных <b>remote - api</b> и <b>local - room</b> мы будем придерживаться правила <b>"SSCOT" (Single Source of Truth)</b>, при котором наши ViewModels будут получать только из ROOM.</p>
<p>Реализуем подгрузку данных в Room из API в Data слое</p>

<h3>ROOM<\h3>
<p>Так как у нас есть 3 основные категоии объектов "Персонажи", "Локации" и "Эпизоды" мы используем Room с несколькими таблицами <b>Multiple Tables</b></p>

<h3>Поиск</h3>
<p>Для поиска используем соответствующие запросы к BD</p>

<h3>Pagination</h3>
<p>Реализуем пагинацию за счет дополнительных методов</p>

<h3>Pull-to-Refresh<h3>

<h2>Более подробная информация о каждой из функции, а также применение контретной архитектуры будут описываться по мере реализации проекта</h2>
