document.addEventListener("DOMContentLoaded", () => {
    initializeTags();

    // BLOCKLY
    (async () => {
        const blocklyDiv = document.getElementById('blocklyDiv');
        const blocklyArea = document.getElementById('blocklyArea');
        const toolboxJson = await fetchToolbox();
        const workspace = Blockly.inject(blocklyDiv, {
            toolbox: toolboxJson,
            trashcan: true
        });
        const backpack = new Backpack(workspace);
        backpack.init();

        const onresize = function(e) {
            let element = blocklyArea;
            let x = element.offsetLeft;
            let y = element.offsetTop;
            blocklyDiv.style.left = x + 'px';
            blocklyDiv.style.top = y + 'px';
            blocklyDiv.style.width = blocklyArea.offsetWidth + 'px';
            blocklyDiv.style.height = blocklyArea.offsetHeight + 'px';
            Blockly.svgResize(workspace);
        };
        window.addEventListener('resize', onresize, false);
        onresize();

        const blocklyCard = $('#blocklyCard');
        if (blocklyCard.length) {
            const resizeObserver = new ResizeObserver(entries => {
                entries.forEach(entry => onresize());
            });
            resizeObserver.observe(blocklyCard[0]);
        }
    })();

    console.info('Creation page loaded');
});

async function fetchToolbox() {
    try {
        const response = await fetch('/json/toolbox.json');
        if (!response.ok) {
            throw new Error("Error while getting toolbox");
        }
        return await response.json();
    } catch (error) {
        console.error("Error while loading toolbox:", error);
        return "";
    }
}

API_TAG_FETCH_URL = '/api/v1/triggers/tags';
