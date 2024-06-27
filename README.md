```markdown
# Simple Advisors Scraper
A simple scraper to extract advisors from one page.

## Setup Instructions

### Clone the Repository

```bash
git clone https://github.com/luispereira230797/advisor-scraper.git
cd advisor-scraper
```

## Deployment Instructions

### Deploying Locally

#### 1. Download Chrome and Chrome driver
Download Chrome and Chrome driver compatible with each other from https://developer.chrome.com/docs/chromedriver/downloads

#### 2. Set the paths on application.properties like below:
```bash
CHROMEDRIVER_PATH=C:\\chromedriver\\chromedriver.exe # Your chromedriver location
CUSTOM_GOOGLE_CHROME_BIN=C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe # Your chrome location
```

#### 3. Set your advisor source configuration:
```bash
advisor.source.base.url=https://www.abc.com.py
advisor.source.search.url=${advisor.source.base.url}/buscador/?query=_search_
```

#### 4. Set your advisor patterns regex to extract data correctly like this:
```bash
ADVISOR_PATTERN=<div\\s+class=\"queryly_item_row\"[^>]*><a\\s[^>]*>(.*?)</a></div> # The pattern of each div that contains each advisor
URL_PATTERN_STRING=<a[^>]+href\\s*=\\s*['\"]([^'\"]+)['\"][^>]*> # The pattern to extract the advisor URL
DATE_PATTERN_STRING=<div\\s+style=\"margin-bottom:10px;color:#212529;font-size:12px;font-family:Lato;\"[^>]*>(.*?)</div> # The pattern to extract the advisor date
TITLE_PATTERN_STRING=<div\\s+class=\"queryly_item_title\"[^>]*>(.*?)</div> # The pattern to extract the advisor title
RESUME_PATTERN_STRING=<div\\s+class=\"queryly_item_description\"[^>]*>(.*?)</div> # The pattern to extract the advisor resume
IMAGE_PATTERN_STRING=<div\\s+class=\"queryly_advanced_item_imagecontainer\"\\s+style=\"[^>]*background-image:\\s*url\\(['\"]([^'\"]+)['\"] # The pattern to extract the advisor image
```

#### 5. Start the Spring Boot application with your favorite IDE:
In my case, I use IntelliJ with Java 17 or higher.

### Deploying with Docker

#### 1. Build and run Docker image:
```bash
docker build -t image/scraper:1 .
docker run -p 8080:8080 image/scraper:1
```