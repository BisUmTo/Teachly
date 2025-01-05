function initializeCodeArea(options) {
    let defaultOptions = {
        readOnly: false,
        includeGameEvents: false,
        gameEventsJson: '/json/game_events.json',
        includeGameActions: false,
        gameActionsJson: '/json/game_actions.json',
        includeCustomBlocks: true,
        customBlocksJson: '',
        toolboxJson: '/json/toolbox.json',
        blocklyAreaId: 'blocklyArea',
        blocklyDivId: 'blocklyDiv',
        blocklyCardId: 'blocklyCard',
        theme: Blockly.Themes.modern
    }
    options = Object.assign(defaultOptions, options);

    // Maximize blockly area
    const blocklyArea = document.getElementById('blocklyArea');
    const windowHeight = window.innerHeight;
    const footerHeight = document.querySelector('footer.main-footer').offsetHeight + 16;
    const topCoordinate = blocklyArea.getBoundingClientRect().y;
    const areaHeight = windowHeight - topCoordinate - footerHeight;
    blocklyArea.style.height = areaHeight + 'px';

    return (async () => {
        const blocklyDiv = document.getElementById(options.blocklyDivId);
        const state = JSON.parse(blocklyDiv.innerHTML || '{}');
        blocklyDiv.innerHTML = '';

        // Register blocks, validators and generators
        const toolboxJson = await fetchJson(options.toolboxJson);
        const toolbox = toolboxJson.contents;
        if(options.includeGameEvents) {
            await gameEventsLoader(options.gameEventsJson);
            Object.assign(Blockly.JavaScript.forBlock, gameEventsGenerators());
            if(!toolbox.find(e => e.name === 'Game')) toolbox.push({kind:"category",name:"Game",expanded:"true",contents:[]});
            toolbox.find(e => e.name === 'Game').contents.push(gameEventsToolbox());
            for (const [name, validator] of Object.entries(gameEventsValidators())) {
                Blockly.Extensions.register(name, validator);
            }
        }
        if(options.includeGameActions) {
            await gameActionsLoader(options.gameActionsJson);
            Object.assign(Blockly.JavaScript.forBlock, gameActionsGenerators());
            if(!toolbox.find(e => e.name === 'Game')) toolbox.push({kind:"category",name:"Game",expanded:"true",contents:[]});
            toolbox.find(e => e.name === 'Game').contents.push(gameActionsToolbox());
            for (const [name, validator] of Object.entries(gameActionsValidators())) {
                Blockly.Extensions.register(name, validator);
            }
        }
        if(options.includeCustomBlocks) {
            await customBlocksLoader(options.customBlocksJson);
            Object.assign(Blockly.JavaScript.forBlock, customBlocksGenerators());
            toolbox.push(customBlocksToolbox());
            for (const [name, validator] of Object.entries(customBlocksValidators())) {
                Blockly.Extensions.register(name, validator);
            }
        }

        const workspace = Blockly.inject(blocklyDiv, {
            toolbox: (options.readOnly ? null : toolboxJson),
            readOnly: options.readOnly,
            scrollbars: true,
            theme: options.theme,
            plugins: {
                // ...window.pluginInfo // TODO: fix this
            },
        });
        Blockly.serialization.workspaces.load(state, workspace);
        workspace.addChangeListener(Blockly.Events.disableOrphans);

        // Plugins
        if (!options.readOnly) new Backpack(workspace).init();

        new CrossTabCopyPaste().init({
            contextMenu: true,
            shortcut: true,
        });

        // Handle resize
        const onresize = function (e) {
            let element = blocklyArea;
            let x = element.offsetLeft;
            let y = element.offsetTop;
            blocklyDiv.style.left = x + 'px';
            blocklyDiv.style.top = y + 'px';
            blocklyDiv.style.width = blocklyArea.offsetWidth + 'px';
            blocklyDiv.style.height = blocklyArea.offsetHeight + 'px';
            Blockly.svgResize(workspace);

            const dropdownDiv = Blockly.DropDownDiv;
            if (dropdownDiv.getContentDiv() && dropdownDiv.isVisible()) {
                dropdownDiv.hideWithoutAnimation();
            }
        };
        window.addEventListener('resize', onresize, false);
        onresize();
        workspace.scrollCenter();

        const blocklyCard = $('#' + options.blocklyCardId);
        if (blocklyCard.length) {
            const resizeObserver = new ResizeObserver(entries => {
                entries.forEach(entry => onresize());
            });
            resizeObserver.observe(blocklyCard[0]);
        }

        return workspace;
    })();
}

// UTILS //
function indentCode(code) {
    if (!code) return '';
    return code.split('\n').map(line => `  ${line}`.trimEnd()).join('\n');
}

async function loadBlocksFromJson(json) {
    const blocks = Blockly.common.createBlockDefinitionsFromJsonArray(json);
    Blockly.common.defineBlocks(blocks);
}

// INTERFACES //
function customBlocksLoader(json_path) {
    return loadBlocksFromJson(json_path);
}

function customBlocksGenerators() {
    return {};
}

function customBlocksToolbox() {
    return {};
}
