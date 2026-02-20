const puppeteer = require('puppeteer');
const path = require('path');

(async () => {
    const browser = await puppeteer.launch({ headless: 'new', args: ['--no-sandbox'] });
    const page = await browser.newPage();
    await page.setViewport({ width: 1400, height: 2800, deviceScaleFactor: 1 });

    const filePath = 'file:///' + path.resolve(__dirname, 'screenshots.html').replace(/\\/g, '/');
    console.log('Loading:', filePath);
    await page.goto(filePath, { waitUntil: 'networkidle0', timeout: 30000 });

    // Wait for fonts to load
    await new Promise(r => setTimeout(r, 3000));

    const ids = ['ss1', 'ss2', 'ss3'];
    const names = ['01-context-menu.png', '02-compression-success.png', '03-settings-page.png'];

    for (let i = 0; i < ids.length; i++) {
        const el = await page.$('#' + ids[i]);
        if (el) {
            const outPath = path.join(__dirname, names[i]);
            await el.screenshot({ path: outPath, type: 'png' });
            console.log('Saved:', outPath);
        } else {
            console.log('Element not found:', ids[i]);
        }
    }

    await browser.close();
    console.log('Done!');
})();
